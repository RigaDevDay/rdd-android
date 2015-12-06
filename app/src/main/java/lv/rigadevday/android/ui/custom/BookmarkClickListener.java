package lv.rigadevday.android.ui.custom;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Presentation;

public class BookmarkClickListener implements View.OnClickListener {

    private Context context;
    private ListView listView;

    public BookmarkClickListener(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    public void onClick(View view) {
        Presentation item = (Presentation) view.getTag(R.string.bookmark_item);
        changeBookmarkState(item);
        animateImageTransition(view, item.isBookmarked());
        showSnackbar(item.isBookmarked());
    }

    private void changeBookmarkState(Presentation item) {
        item.setBookmarked(!item.isBookmarked());
        item.save();
    }

    private void animateImageTransition(View view, final boolean bookmarked) {
        final ImageView imageView = (ImageView) view;
        Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        imageView.startAnimation(fadeOut);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setImageResource(bookmarked ? R.drawable.icon_bookmark : R.drawable.icon_menu_bookmark);
                Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                imageView.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public Void showSnackbar(Boolean input) {
        int msg = input ? R.string.bookmarked : R.string.unbookmarked;
        Snackbar.make(listView.getRootView(), msg, Snackbar.LENGTH_SHORT)
                .show();
        return null;
    }


}
