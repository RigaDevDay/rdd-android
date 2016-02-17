package lv.rigadevday.android.ui.talk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.R;
import lv.rigadevday.android.repository.model.Event;
import lv.rigadevday.android.ui.base.BaseFragment;
import lv.rigadevday.android.ui.navigation.OpenSpeakerScreen;
import lv.rigadevday.android.ui.navigation.ShowErrorMessageEvent;
import lv.rigadevday.android.utils.Utils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class TalkFragment extends BaseFragment {

    public static final String EXTRA_DAY = "talk_day";
    public static final String EXTRA_TIME = "talk_time";
    public static final String EXTRA_INDEX = "talk_index_in_day";
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
    @Bind(R.id.talk_title)
    protected TextView title;
    @Bind(R.id.talk_speaker_layout)
    protected LinearLayout speakerLayout;
    @Bind(R.id.talk_speaker1)
    protected TextView speaker1;
    @Bind(R.id.talk_speakers_amp)
    protected TextView speakersAmp;
    @Bind(R.id.talk_speaker2)
    protected TextView speaker2;
    @Bind(R.id.talk_description)
    protected TextView description;
    @Bind(R.id.talk_tags)
    protected TextView tagsLayout;
    @Inject
    EventBus bus;
    private String day;
    private String time;
    private int index;

    public static Fragment newInstance(Bundle extras) {
        TalkFragment fragment = new TalkFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    protected int contentViewId() {
        return R.layout.fragment_talk;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        day = getArguments().getString(EXTRA_DAY);
        time = getArguments().getString(EXTRA_TIME);
        index = getArguments().getInt(EXTRA_INDEX);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TalkActivity) getActivity()).setupToolbar(toolbar);
    }

    @Override
    protected void init() {
        dataFetchSubscription = repository.getTimeSlot(day, time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        timeSlot -> {
                            toolbar.setTitle(String.format(
                                    getString(R.string.talk_time_pattern),
                                    timeSlot.time,
                                    timeSlot.endTime));

                            Event event = timeSlot.events.get(index);

                            title.setText(Utils.nullToEmpty(event.subtitle));
                            setSpeakers(event.speakers);
                            setTags(event.tags);
                            description.setText(Html.fromHtml(Utils.nullToEmpty(event.description)));
                        },
                        error -> bus.post(new ShowErrorMessageEvent())
                );
    }

    private void setTags(List<String> tags) {
        Observable.from(tags)
                .defaultIfEmpty("")
                .reduce((r, s) -> r.concat(", ").concat(s))
                .subscribe(tagsLayout::setText);
    }

    private void setSpeakers(List<Integer> speakers) {
        repository.getSpeakers(speakers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(
                        list -> {
                            speaker1.setText(list.get(0).name);
                            speaker1.setOnClickListener(v -> bus.post(new OpenSpeakerScreen(list.get(0).id)));

                            if (list.size() > 1) {
                                speakersAmp.setVisibility(View.VISIBLE);
                                speaker2.setVisibility(View.VISIBLE);

                                speaker2.setText(list.get(1).name);
                                speaker2.setOnClickListener(v -> bus.post(new OpenSpeakerScreen(list.get(1).id)));
                            }

                            speakerLayout.setVisibility(View.VISIBLE);
                        },
                        error -> bus.post(new ShowErrorMessageEvent())
                );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.talk_menu, menu);

        repository.hasFavoredTimeSlot(day, time, index)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isFavored -> {
                    setupCorrectImage(menu.findItem(R.id.action_favorite), isFavored);
                });
    }

    private void setupCorrectImage(MenuItem item, Boolean favoredNow) {
        item.setIcon(favoredNow
                ? R.drawable.vector_action_star_fill
                : R.drawable.vector_action_star_outline);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            repository.toggleTimeSlotFavored(day, time, index)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((favoredNow) -> {
                        setupCorrectImage(item, favoredNow);
                    });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
