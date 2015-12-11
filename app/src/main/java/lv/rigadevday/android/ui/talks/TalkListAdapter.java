package lv.rigadevday.android.ui.talks;

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
import lv.rigadevday.android.common.ViewHolder;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.domain.Tag;
import lv.rigadevday.android.ui.custom.view.tag.TagView;

public class TalkListAdapter extends ArrayAdapter<Presentation> {

    private final int tagColor;
    private final SimpleDateFormat dateFormat;
    private LayoutInflater inflater;
    private View.OnClickListener bookmarkClickListener;

    public TalkListAdapter(Context context, LayoutInflater inflater, View.OnClickListener bookmarkClickListener) {
        super(context, 0, Presentation.getAll());
        this.inflater = inflater;
        this.bookmarkClickListener = bookmarkClickListener;
        this.tagColor = context.getResources().getColor(R.color.tag_color);
        this.dateFormat = new SimpleDateFormat("HH:mm");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.old_talk_item, parent, false);
        }

        Presentation p = getItem(position);
        pupulateView(convertView, p);

        return convertView;
    }

    private void pupulateView(View convertView, final Presentation item) {
        TextView speakerName = ViewHolder.get(convertView, R.id.ti_speakers_name);

        String speakerNames = getSpeakerNames(item);
        speakerName.setText(speakerNames);

        TextView title = ViewHolder.get(convertView, R.id.ti_presentation_title);
        title.setText(item.getTitle());

        TextView startTime = ViewHolder.get(convertView, R.id.ti_start_time);
        String start = dateFormat.format(item.getStartTime());
        startTime.setText(start);

        final ImageView bookmark = ViewHolder.get(convertView, R.id.ti_bookmark);
        bookmark.setImageResource(item.isBookmarked() ? R.drawable.icon_bookmark : R.drawable.icon_menu_bookmark);
        bookmark.setTag(R.string.bookmark_item, item);
        bookmark.setOnClickListener(bookmarkClickListener);

        TagView tagView = ViewHolder.get(convertView, R.id.ti_tags);

        List<TagView.Tag> tagViews = getTags(item);
        tagView.setTags(tagViews, " ");
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
                .filter(speaker -> speaker == null)
                .map(Speaker::getName)
                .collect(Collectors.joining(", "));
    }


}
