package lv.rigadevday.android.ui.schedule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnItemClick;
import lv.rigadevday.android.R;
import lv.rigadevday.android.common.DialogHelper;
import lv.rigadevday.android.domain.Event;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.SpeakerPresentation;
import lv.rigadevday.android.domain.TrackPresentations;
import lv.rigadevday.android.infrastructure.FragmentFactory;
import lv.rigadevday.android.ui.BaseFragment;
import lv.rigadevday.android.ui.custom.BookmarkClickListener;
import lv.rigadevday.android.ui.details.ProfileFragment;

public class ScheduleTrackFragment extends BaseFragment {

    public static final String TRACK = "track";
    private long trackId;

    @Bind(R.id.schedule_track_items)
    ListView scheduleTrackItems;

    @Inject
    Context context;

    @Inject
    LayoutInflater layoutInflater;
    private ScheduleTrackItemsAdapter adapter;

    public static ScheduleTrackFragment newInstance(long trackId) {
        Bundle args = new Bundle();
        args.putLong(TRACK, trackId);
        ScheduleTrackFragment fragment = new ScheduleTrackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int contentViewId() {
        return R.layout.schedule_track;
    }

    @Override
    protected void init(Bundle in) {
        Bundle bundle = in != null ? in : getArguments();
        if (bundle != null) {
            trackId = bundle.getLong(TRACK);
        }
        List<TrackItemHolder> items = getItemsList(trackId);
        BookmarkClickListener bookmarkClickListener = new BookmarkClickListener(getActivity(), scheduleTrackItems);
        adapter = new ScheduleTrackItemsAdapter(context, layoutInflater, items, bookmarkClickListener);
        scheduleTrackItems.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putLong(TRACK, trackId);
    }

    private List<TrackItemHolder> getItemsList(long trackId) {
        List<TrackPresentations> presentationsByTrack = TrackPresentations.getByTrack(trackId);
        List<Event> events = Event.getAll();

        TreeSet<TrackItemHolder> trackItemsSet = new TreeSet<>();
        for (TrackPresentations p : presentationsByTrack) {
            trackItemsSet.add(new TrackItemHolder(p.getPresentation()));
        }
        for (Event e : events) {
            trackItemsSet.add(new TrackItemHolder(e));
        }
        return new ArrayList<>(trackItemsSet);
    }

    @OnItemClick(R.id.schedule_track_items)
    public void onPresentationItemClick(int position) {
        TrackItemHolder item = adapter.getItem(position);
        if (item.isEventItem()) {
            if (item.getEvent().getTitle().contains("Afterparty")) {
                DialogHelper.getStyled(getActivity())
                        .positiveText(R.string.OK)
                        .title(R.string.afterparty)
                        .content(R.string.afterparty_description)
                        .show();
            }
            return;
        }

        Presentation presentation = item.getPresentation();

        ProfileFragment profileFragment = FragmentFactory.create(ProfileFragment.class);

        List<SpeakerPresentation> sp = SpeakerPresentation.getByPresentation(presentation.getId());
        Bundle bundle = new Bundle();
        bundle.putSerializable("speaker", sp.get(0).getSpeaker());
        profileFragment.setArguments(bundle);

        this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, profileFragment, profileFragment.getClass().getName())
                .addToBackStack(null)
                .commit();
    }
}
