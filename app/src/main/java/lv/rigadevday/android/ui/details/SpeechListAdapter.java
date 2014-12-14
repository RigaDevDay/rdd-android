package lv.rigadevday.android.ui.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import lv.rigadevday.android.R;
import lv.rigadevday.android.common.ViewHolder;
import lv.rigadevday.android.domain.Presentation;

public class SpeechListAdapter extends ArrayAdapter<Presentation> {

    LayoutInflater inflater;
    SimpleDateFormat dateFormat;

    public SpeechListAdapter(Context context, LayoutInflater inflater, List<Presentation> presentationList) {
        super(context, 0, presentationList);
        this.inflater = inflater;
        this.dateFormat = new SimpleDateFormat("HH:mm");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.profile_speech_item, parent, false);
        }

        Presentation p = getItem(position);
        pupulateView(convertView, p);

        return convertView;
    }

    private void pupulateView(View convertView, Presentation item) {
        TextView speechVenue = ViewHolder.get(convertView, R.id.speech_venue);

        String start = dateFormat.format(item.getStartTime());
        String end = dateFormat.format(item.getEndTime());

        String venue = String.format("%s - %s, %s", start, end, item.getRoom());
        speechVenue.setText(venue);

        TextView speechTitle = ViewHolder.get(convertView, R.id.speech_title);
        speechTitle.setText(item.getTitle());

        TextView speechDescription = ViewHolder.get(convertView, R.id.speech_description);
        speechDescription.setText(item.getDescription());
    }
}