package lv.rigadevday.android.ui.bookmark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.common.ViewHolder;

public class BookmarkListAdapter extends ArrayAdapter<Presentation> {

    LayoutInflater inflater;
    SimpleDateFormat dateFormat;

    @Inject
    public BookmarkListAdapter(Context context, LayoutInflater inflater) {
        super(context, 0, Presentation.getBookmarked());
        this.inflater = inflater;
        this.dateFormat = new SimpleDateFormat("HH:mm");
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
        TextView speakerName = ViewHolder.get(convertView, R.id.speaker_name);

        List<Speaker> speakers = item.getSpeakers();
        String name = "";
        for (Speaker speaker : speakers) {
            if (!name.isEmpty()) name += ", ";
            name += speaker.getName();
        }
        speakerName.setText(name);

        TextView title = ViewHolder.get(convertView, R.id.presentation_title);
        title.setText(item.getTitle());

        TextView location = ViewHolder.get(convertView, R.id.presentation_location);
        location.setText(item.getRoom());

        Date startTime = item.getStartTime();
        String start = dateFormat.format(startTime);

        TextView time = ViewHolder.get(convertView, R.id.presentation_time);
        time.setText(start);
    }
}
