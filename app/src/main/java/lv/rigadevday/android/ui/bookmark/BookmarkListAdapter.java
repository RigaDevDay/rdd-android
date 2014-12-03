package lv.rigadevday.android.ui.bookmark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import javax.inject.Inject;

import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.ui.ViewHolder;

public class BookmarkListAdapter extends ArrayAdapter<Presentation> {

    LayoutInflater inflater;

    @Inject
    public BookmarkListAdapter(Context context, LayoutInflater inflater) {
        super(context, 0, Presentation.getBookmarked());
        this.inflater = inflater;
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
//        TextView speakerName = ViewHolder.get(convertView, R.id.speaker_name);
//        speakerName.setText("Samuel Rats");
//
//        TextView title = ViewHolder.get(convertView, R.id.presentation_title);
//        title.setText(item.getTitle());
//
//        TextView location = ViewHolder.get(convertView, R.id.presentation_location);
//        location.setText("Hall 3");
//
//        TextView time = ViewHolder.get(convertView, R.id.presentation_time);
//        time.setText("11:30");
    }
}
