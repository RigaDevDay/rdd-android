package lv.rigadevday.android.ui.bookmark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import lv.rigadevday.android.R;
import lv.rigadevday.android.common.ViewHolder;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.Speaker;

public class BookmarkListAdapter extends ArrayAdapter<Presentation> {

    private final int primaryColor;
    private final int notificationColor;
    private LayoutInflater inflater;
    private List<Presentation> items;
    private SimpleDateFormat dateFormat;
    private Collection<String> startTimes;

    @Inject
    public BookmarkListAdapter(Context context, LayoutInflater inflater, List<Presentation> items) {
        super(context, 0, items);
        this.inflater = inflater;
        this.items = items;
        this.dateFormat = new SimpleDateFormat("HH:mm");
        this.startTimes = collectStartTimes(items);
        this.primaryColor = context.getResources().getColor(R.color.primary);
        this.notificationColor = context.getResources().getColor(R.color.notification);
    }

    private Collection<String> collectStartTimes(List<Presentation> items) {
        List<String> dates = new ArrayList<>(items.size());
        for (Presentation item: items) {
            dates.add(dateFormat.format(item.getStartTime()));
        }
        return dates;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.bookmark_item, parent, false);
        }

        Presentation p = getItem(position);
        pupulateView(convertView, p);

        return convertView;
    }

    private void pupulateView(View convertView, Presentation item) {
        TextView speakerName = ViewHolder.get(convertView, R.id.bi_speaker);

        List<Speaker> speakers = item.getSpeakers();
        String name = "";
        for (Speaker speaker : speakers) {
            if (!name.isEmpty()) name += ", ";
            name += speaker.getName();
        }
        speakerName.setText(name);

        TextView title = ViewHolder.get(convertView, R.id.bi_presentation_title);
        title.setText(item.getTitle());

        TextView location = ViewHolder.get(convertView, R.id.bi_presentation_location);
        location.setText(item.getRoom());

        TextView startTime = ViewHolder.get(convertView, R.id.bi_start_time);
        String start = dateFormat.format(item.getStartTime());
        startTime.setText(start);
        int frequency = Collections.frequency(startTimes, start);
        startTime.setTextColor(frequency > 1 ? notificationColor : primaryColor);

        ImageView bookmark = ViewHolder.get(convertView, R.id.bi_bookmark);
        bookmark.setTag(R.string.bookmark_item, item);
        bookmark.setOnClickListener(view -> {
            Presentation presentation = (Presentation) view.getTag(R.string.bookmark_item);
            presentation.setBookmarked(false);
            presentation.save();
            items.remove(presentation);
            startTimes = collectStartTimes(items);
            BookmarkListAdapter.this.notifyDataSetChanged();
        });
    }
}
