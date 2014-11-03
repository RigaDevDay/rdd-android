package lv.rigadevday.android.ui.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersBaseAdapter;

import javax.inject.Inject;

import lv.rigadevday.android.R;
import lv.rigadevday.android.common.TypefaceCache;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.ui.ViewHolder;

public class ScheduleGridAdapter extends BaseAdapter implements StickyGridHeadersBaseAdapter {
    private Schedule schedule;

    private int headerResId;
    private int itemResId;

    @Inject
    Context context;

    @Inject
    LayoutInflater inflater;

    @Inject
    TypefaceCache typefaceCache;

    public ScheduleGridAdapter() {
        this.schedule = new Schedule(Presentation.getAll());
        this.headerResId = R.layout.schedule_header;
        this.itemResId = R.layout.schedule_item;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public int getCount() {
        return schedule.getPresentations().size();
    }

    @Override
    public Presentation getItem(int position) {
        return schedule.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCountForHeader(int index) {
        return schedule.getCountForHeader(index);
    }

    @Override
    public int getNumHeaders() {
        return schedule.getHeaderCount();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(headerResId, parent, false);
        }
        Presentation item = getItem(position);
        pupulateView(convertView, item, ViewIds.HEADER);

        return convertView;
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(itemResId, parent, false);
        }
        Presentation item = getItem(position);
        pupulateView(convertView, item, ViewIds.ITEM);

        return convertView;
    }

    private void pupulateView(View convertView, Presentation item, ViewIds ids) {
        TextView title = ViewHolder.get(convertView, ids.titleId);
        title.setTypeface(typefaceCache.loadFromAsset("fonts/Roboto-Regular.ttf"));
        title.setText(item.getTitle());

        TextView time = ViewHolder.get(convertView, ids.timePlaceId);
        time.setTypeface(typefaceCache.loadFromAsset("fonts/Roboto-Regular.ttf"));
        // TODO fix
        String info = assembleEventInfo("Start", "End", "Room");
        time.setText(info);

        ImageView bookmark = ViewHolder.get(convertView, ids.bookmarkId);
        int bookmarkIcon = item.isBookmarked() ? R.drawable.icon_bookmark : R.drawable.icon_bookmark_empty;
        bookmark.setImageDrawable(context.getResources().getDrawable(bookmarkIcon));

        // TODO: implement placeholder arawable assigning to presentations
        ImageView image = ViewHolder.get(convertView, ids.imageId);
        int drawableId = item.isHeader() ? R.drawable.pic_1_edited : R.drawable.pic_2_edited;
        image.setImageDrawable(context.getResources().getDrawable(drawableId));
    }

    private String assembleEventInfo(String start, String end, String room) {
        return String.format("%s - %s, %s", start, end, room);
    }

    private static enum ViewIds {
        HEADER(R.id.header_title, R.id.header_time_place, R.id.icon_bookmark, R.id.header_image),
        ITEM(R.id.item_title, R.id.item_time_place, R.id.icon_bookmark, R.id.item_image);

        final int titleId;
        final int timePlaceId;
        final int bookmarkId;
        final int imageId;

        private ViewIds(int titleId, int timePlaceId, int bookmarkId, int imageId) {
            this.titleId = titleId;
            this.timePlaceId = timePlaceId;
            this.bookmarkId = bookmarkId;
            this.imageId = imageId;
        }
    }
}
