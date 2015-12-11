package lv.rigadevday.android.ui.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lv.rigadevday.android.R;
import lv.rigadevday.android.common.Utils;
import lv.rigadevday.android.common.ViewHolder;
import lv.rigadevday.android.domain.Event;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.domain.Tag;
import lv.rigadevday.android.ui.custom.BookmarkClickListener;
import lv.rigadevday.android.ui.custom.view.tag.TagView;

public class ScheduleTrackItemsAdapter extends ArrayAdapter<TrackItemHolder> {

    private final int tagColor;
    private LayoutInflater inflater;
    private BookmarkClickListener bookmarkClickListener;
    private SimpleDateFormat dateFormat;

    public ScheduleTrackItemsAdapter(Context context, LayoutInflater inflater, List<TrackItemHolder> items, BookmarkClickListener bookmarkClickListener) {
        super(context, 0, items);
        this.inflater = inflater;
        this.bookmarkClickListener = bookmarkClickListener;
        this.dateFormat = new SimpleDateFormat("HH:mm");
        this.tagColor = context.getResources().getColor(R.color.tag_color);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewType type = ViewType.values()[getItemViewType(i)];

        if (convertView == null) {
            int layout = ViewType.EVENT.equals(type) ? R.layout.old_schedule_item_event : R.layout.old_schedule_item_presentation;
            convertView = inflater.inflate(layout, parent, false);
        }

        setBackground(i, convertView);

        TrackItemHolder item = getItem(i);
        if (item.isEventItem()) {
            populateEventView(convertView, item);
        } else {
            populatePresentationView(convertView, item);
        }

        return convertView;
    }

    private void setBackground(int i, View convertView) {
        int colorId = i % 2 == 0 ? R.color.white : R.color.near_white;
        int color = getContext().getResources().getColor(colorId);
        convertView.setBackgroundColor(color);
    }

    private void populatePresentationView(View convertView, TrackItemHolder item) {
        Presentation presentation = item.getPresentation();

        TextView title = ViewHolder.get(convertView, R.id.sip_title);
        title.setText(presentation.getTitle());

        String names = getSpeakerNames(presentation);
        TextView name = ViewHolder.get(convertView, R.id.sip_name);
        name.setText(names);

        TextView startTime = ViewHolder.get(convertView, R.id.sip_start_time);
        String start = dateFormat.format(presentation.getStartTime());
        startTime.setText(start);

        TagView tagView = ViewHolder.get(convertView, R.id.sip_tags);

        List<TagView.Tag> tagViews = getTags(presentation);
        tagView.setTags(tagViews, " ");

        ImageView bookmark = ViewHolder.get(convertView, R.id.sip_bookmark);
        bookmark.setImageResource(presentation.isBookmarked() ? R.drawable.icon_bookmark : R.drawable.icon_menu_bookmark);
        bookmark.setTag(R.string.bookmark_item, presentation);
        bookmark.setOnClickListener(bookmarkClickListener);
    }

    private List<TagView.Tag> getTags(Presentation presentation) {
        List<Tag> tags = presentation.getTags();
        List<TagView.Tag> tagViews = new ArrayList<>();
        for (Tag tag : tags) {
            tagViews.add(new TagView.Tag(tag.getName(), tagColor));
        }
        return tagViews;
    }

    private String getSpeakerNames(Presentation presentation) {
        return Stream.of(presentation.getSpeakers())
                .filter(speaker -> speaker != null && !Utils.isNullOrEmpty(speaker.getName()))
                .map(Speaker::getName)
                .collect(Collectors.joining(", "));
    }

    private void populateEventView(View convertView, TrackItemHolder item) {
        Event event = item.getEvent();

        TextView title = ViewHolder.get(convertView, R.id.sie_title);
        title.setText(event.getTitle());

        String start = dateFormat.format(event.getStartTime());
        TextView startTime = ViewHolder.get(convertView, R.id.sie_start_time);
        startTime.setText(start);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isEventItem() ? ViewType.EVENT.ordinal() : ViewType.PRESENTATION.ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return ViewType.values().length;
    }

    private static enum ViewType {
        EVENT,
        PRESENTATION
    }
}
