package lv.rigadevday.android.ui.talks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

import lv.rigadevday.android.R;
import lv.rigadevday.android.common.ViewHolder;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.domain.Tag;
import lv.rigadevday.android.ui.custom.view.tag.TagView;

public class TalkListAdapter extends ArrayAdapter<Presentation> {

    private final int tagColor;
    private Context context;
    private Function<Boolean, Void> bookmarkFunction;
    LayoutInflater inflater;

    public TalkListAdapter(Context context, LayoutInflater inflater, Function<Boolean, Void> bookmarkFunction) {
        super(context, 0, Presentation.getAll());
        this.context = context;
        this.inflater = inflater;
        this.bookmarkFunction = bookmarkFunction;
        this.tagColor = context.getResources().getColor(R.color.tag_color);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.talk_item, parent, false);
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

        final ImageView bookmark = ViewHolder.get(convertView, R.id.ti_bookmark);
        bookmark.setImageResource(item.isBookmarked() ? R.drawable.icon_bookmark : R.drawable.icon_bookmark_empty_dark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBookmarkState(item);
                animateImageTransition(view, item.isBookmarked());
                if (bookmarkFunction != null) {
                    bookmarkFunction.apply(item.isBookmarked());
                }
            }
        });

        TagView tagView = ViewHolder.get(convertView, R.id.ti_tags);

        List<TagView.Tag> tagViews = getTags(item);
        tagView.setTags(tagViews, " ");
    }

    private void changeBookmarkState(Presentation item) {
        item.setBookmarked(!item.isBookmarked());
        item.save();
    }

    private List<TagView.Tag> getTags(Presentation presentation) {
        List<Tag> tags = presentation.getTags();
        List<TagView.Tag> tagViews = Lists.newArrayList();
        for (Tag tag : tags) {
            tagViews.add(new TagView.Tag(tag.getName(), tagColor));
        }
        return tagViews;
    }

    private String getSpeakerNames(Presentation presentation) {
        List<Speaker> speakers = presentation.getSpeakers();
        Iterable<String> names = Iterables.transform(speakers, new Function<Speaker, String>() {
            public String apply(Speaker s) {
                return s.getName();
            }
        });
        return Joiner.on(", ").skipNulls().join(names);
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
                imageView.setImageResource(bookmarked ? R.drawable.icon_bookmark : R.drawable.icon_bookmark_empty_dark);
                Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                imageView.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }
}
