package lv.rigadevday.android.ui.speakers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import lv.rigadevday.android.R;
import lv.rigadevday.android.common.TypefaceCache;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.ui.ViewHolder;

public class SpeakersGridAdapter extends ArrayAdapter<Speaker> {

    LayoutInflater inflater;

    @Inject
    public SpeakersGridAdapter(Context context, LayoutInflater inflater) {
        super(context, 0, Speaker.getAll());
        this.inflater = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = convertView != null ? convertView : inflater.inflate(R.layout.schedule_item, parent, false);

        Speaker item = getItem(position);
        pupulateView(convertView, item);

        return convertView;
    }

    private void pupulateView(View convertView, Speaker item) {
        TextView title = ViewHolder.get(convertView, R.id.item_title);
        title.setText(item.getName());

        TextView time = ViewHolder.get(convertView, R.id.item_time_place);
        time.setText(item.getCompany());

//        ImageView bookmark = ViewHolder.get(convertView, R.id.item_bookmark);
//        bookmark.setVisibility(item.isBookmarked() ? View.VISIBLE : View.INVISIBLE);

        // TODO: implement placeholder arawable assigning to presentations
        ImageView image = ViewHolder.get(convertView, R.id.item_image);
        int drawableId =  R.drawable.pic_2_edited;
        image.setImageDrawable(getContext().getResources().getDrawable(drawableId));
    }
}
