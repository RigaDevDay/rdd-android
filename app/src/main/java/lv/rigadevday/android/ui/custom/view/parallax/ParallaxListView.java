package lv.rigadevday.android.ui.custom.view.parallax;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.google.common.base.Function;

import java.util.List;
import java.util.Stack;

import lv.rigadevday.android.R;
import lv.rigadevday.android.common.ViewHolder;

/**
 * Custom ViewGroup that behaves similar to a ListView, except it has parallax effect on it's contents.
 */
public final class ParallaxListView extends ScrollView {


    private static final String STATE_SUPER = "savedState";
    private static final String STATE_SCROLL_POSITION = "scrollPos";

    private static final float OVERLAY_MAX_TRANSPARENCY = 150f;
    // Determine which part of visible area a fully expanded view should take.
    private static final float FULL_VIEW_SCREEN_PART = 0.6f;
    private static final float COLLAPSED_VIEW_SCREEN_PART = 0.3f;

    private int viewMaxHeight;
    private int viewMinHeight;
    private int lastItemHeight;
    // Height of visible screen area
    private int scrollViewHeight;
    // Current scroll position
    private int scrollTopPosition;
    private int totalElements = 1;
    private float minViewScaleFactor;

    private final Context context;
    private Bundle savedState;
    private ViewContainer itemContainerView;
    private LayoutInflater layoutInflater;
    private List<ParallaxListItem> items;
    private View lastItem;
    private Function onItemClickFunction;

    public ParallaxListView(Context context) {
        this(context, null);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
        this.context = context;
    }

    private void initView(Context c) {
        layoutInflater = LayoutInflater.from(c);
        itemContainerView = new ViewContainer(c);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        addView(itemContainerView, lp);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        scrollTopPosition = t;
        itemContainerView.onScrollChanged();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(STATE_SCROLL_POSITION, scrollTopPosition);
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            savedState = (Bundle) state;
            super.onRestoreInstanceState(savedState.getParcelable(STATE_SUPER));
            scrollTopPosition = savedState.getInt(STATE_SCROLL_POSITION);
        }
    }

    public void setDisplayItems(List<ParallaxListItem> items) {
        setDisplayItems(items, false);
    }

    public void setDisplayItems(List<ParallaxListItem> items, boolean resetScrollPosition) {
        this.items = items;

        itemContainerView.removeAllViews();
        itemContainerView.mFirstVisibleItem = 0;

        // at least we have last element
        totalElements = 1;

        if (this.items != null) {
            totalElements += this.items.size();
        }

        if (resetScrollPosition) {
            resetScrollPosition();
        } else {
            restoreScrollPosition();
        }
        savedState = null;
    }

    private void restoreScrollPosition() {
        if (savedState != null) {
            final int scrollPosition = savedState.getInt(STATE_SCROLL_POSITION);
            post(new Runnable() {
                @Override
                public void run() {
                    scrollTo(0, scrollPosition);
                }
            });
        } else {
            // if not, simply re-layout everything with current scroll position
            itemContainerView.onScrollChanged();
        }
    }

    private void resetScrollPosition() {
        if (scrollTopPosition == 0) {
            //redraw
            itemContainerView.onScrollChanged();
        } else {
            scrollTopPosition = 0;
            scrollTo(0, 0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();

        // When we have a non-null height of ScrollView it means we can calculate dataset element sizes.
        if (height > 0 && scrollViewHeight < height) {
            scrollViewHeight = getMeasuredHeight();
            viewMaxHeight = (int) (scrollViewHeight * FULL_VIEW_SCREEN_PART);

            // Scaled-down view size.
            viewMinHeight = (int) (scrollViewHeight * COLLAPSED_VIEW_SCREEN_PART);

            // Relative size of a scaled-down view to a full-size view
            minViewScaleFactor = (float) viewMinHeight / viewMaxHeight;

            // Calculate height for last element.
            lastItemHeight = scrollViewHeight - viewMaxHeight;

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            itemContainerView.onScrollChanged();
        }
    }

    private View getLastItem() {
        if (lastItem == null) {
            lastItem = layoutInflater.inflate(R.layout.speaker_list_item_last, this, false);
            lastItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParallaxListView.this.smoothScrollTo(0, 0);
                }
            });
        }

        return lastItem;
    }

    public void setOnItemClickListener(Function onItemClickFunction) {
        this.onItemClickFunction = onItemClickFunction;
    }

    /**
     * Custom ViewGroup responsible for Adapter items measurement and layout. Also contains logic for items reuse.
     */
    private final class ViewContainer extends ViewGroup {

        private final Stack<View> viewStack = new Stack<View>();

        private int mFirstVisibleItem = -1;

        public ViewContainer(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int height = 0;
            if (items != null) {
                height = (items.size() * viewMaxHeight);
            }

            // Take the last list element into account when determining ViewContainer height.
            if (height > scrollViewHeight) {
                height += lastItemHeight;
            }

            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            positionChildren();
        }

        private void onScrollChanged() {
            if (scrollViewHeight > 0) {
                // Remove views outside of visible area, add views that came into visible area and layout them.
                removeViews();
                addViews();
                positionChildren();
            }
        }

        /**
         * Lays out all children. Calculates their sizes taking scroll position into account.
         */
        private void positionChildren() {
            int top = -1;
            for (int i = 0; i < getChildCount(); i++) {
                int position = mFirstVisibleItem + i;

                int width = getMeasuredWidth();
                View child = getChildAt(i);

                if (top < 0) {
                    top = position * viewMaxHeight;
                }

                if (position < totalElements - 1) {
                    float scale = calculateScale(top - scrollTopPosition, viewMaxHeight, minViewScaleFactor);
                    // Calculate view's position.
                    int height = Math.round(viewMaxHeight * scale);
                    int bottom = top + height;

                    ViewHolder.get(child, R.id.fading_view).setBackgroundColor(getOverlayColor(scale));

                    child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(viewMaxHeight, MeasureSpec.EXACTLY));


                    child.layout(0, top, width, bottom);

                    top = bottom;
                } else {
                    // Layout the special last element on the list.
                    // Without that element, the last content element could never be fully expanded.
                    float scale = calculateScale(top - scrollTopPosition - viewMaxHeight, lastItemHeight,
                            minViewScaleFactor);

                    View fadingView = child.findViewById(R.id.fading_view);
                    fadingView.setBackgroundColor(getOverlayColor(scale));

                    child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(lastItemHeight, MeasureSpec.EXACTLY));

                    child.layout(0, top, width, top + lastItemHeight);
                }
            }
        }

        private int getOverlayColor(float scale) {
            float alphaDiff = OVERLAY_MAX_TRANSPARENCY / minViewScaleFactor * (scale - minViewScaleFactor);
            return Color.argb(Math.max(0, Math.round(OVERLAY_MAX_TRANSPARENCY - alphaDiff)), 0, 0, 0);
        }

        private float calculateScale(int disposition, int normalHeight, float minScaleFactor) {
            float scale = 1f;

            if (disposition > normalHeight) {
                scale = minScaleFactor;
            } else if (disposition > 0) {
                // itemPosition from top in percents
                // 1 when item is 0px from top, 0 when item is mItemMaxHeight from top
                float deltaPercent = (float) (normalHeight - disposition) / normalHeight;

                // calculation result will be in range from MIN_SCALE_FACTOR to 1
                scale = minScaleFactor + deltaPercent * (1.0f - minScaleFactor);
            }

            return scale;
        }

        private void removeViews() {
            // remove children at top
            boolean removed = true;
            while (removed && getChildCount() > 0) {
                removed = false;
                View child = getChildAt(0);
                if (child.getBottom() < scrollTopPosition) {
                    mFirstVisibleItem++;
                    storeViewInStack(child);
                    removed = true;
                }
            }

            // remove children from bottom
            if (getChildCount() > 0) {
                View firstVisible = getChildAt(0);
                int scrollBottomPosition = scrollTopPosition + scrollViewHeight;
                int visibleChildCount = ((scrollBottomPosition - firstVisible.getBottom()) / viewMinHeight) + 2;

                removed = true;

                while (removed && getChildCount() > 0 && getChildCount() > visibleChildCount) {
                    removed = false;
                    View child = getChildAt(getChildCount() - 1);
                    if (child.getTop() > scrollBottomPosition) {
                        storeViewInStack(child);
                        removed = true;
                    }
                }
            }

            if (getChildCount() == 0) {
                mFirstVisibleItem = -1;
            }
        }

        private void addViews() {
            // bottom of visible area
            int b = scrollTopPosition + scrollViewHeight;

            if (getChildCount() == 0) {
                mFirstVisibleItem = scrollTopPosition / viewMaxHeight;
                addView(getView(mFirstVisibleItem));
            }

            int top = mFirstVisibleItem * viewMaxHeight;
            int bottom = top + viewMaxHeight + (viewMinHeight * (getChildCount() - 1));

            // add children at top of list in visible available area
            while (top > scrollTopPosition && mFirstVisibleItem > 0) {
                mFirstVisibleItem--;

                addView(getView(mFirstVisibleItem), 0);
                top -= viewMinHeight;
            }

            // add children at bottom of list in visible available area
            int lastVisiblePosition = mFirstVisibleItem + getChildCount() - 1;

            while (bottom < b && lastVisiblePosition < totalElements - 1) {
                lastVisiblePosition++;
                addView(getView(lastVisiblePosition));
                bottom += viewMinHeight;
            }
        }

        private void storeViewInStack(View view) {
            if (view.getId() != R.id.data_list_enditem) {
                viewStack.push(view);
            }
            removeView(view);
        }

        private View getView(final int position) {
            if (position == totalElements - 1) {
                return getLastItem();
            } else {
                final ParallaxListItem listItem = items.get(position);

                View convertView = null;

                if (!viewStack.isEmpty()) {
                    convertView = viewStack.pop();
                }

                if (convertView == null) {
                    convertView = layoutInflater.inflate(listItem.getItemLayoutResource(), this, false);
                }

                listItem.present(convertView, context);

                convertView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int positionDifference = scrollTopPosition - position * viewMaxHeight;
                        if (Math.abs(positionDifference) > viewMaxHeight / 3) {
                            ParallaxListView.this.smoothScrollTo(0, position * viewMaxHeight);
                        } else {
                            onItemClickFunction.apply(listItem);
                        }
                    }
                });

                return convertView;
            }
        }
    }
}
