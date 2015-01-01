package lv.rigadevday.android.ui.schedule;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.TreeSet;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Event;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.domain.SpeakerPresentation;
import lv.rigadevday.android.domain.Track;
import lv.rigadevday.android.domain.TrackPresentations;
import lv.rigadevday.android.infrastructure.FragmentFactory;
import lv.rigadevday.android.ui.BaseFragment;
import lv.rigadevday.android.ui.custom.BookmarkSnackBarDisplayFunction;
import lv.rigadevday.android.ui.details.ProfileFragment;

public class ScheduleTrackFragment extends BaseFragment {

    public static final String TRACK = "track";
    private Track track;

    @InjectView(R.id.schedule_track_items)
    ListView scheduleTrackItems;

    @Inject
    Context context;

    @Inject
    LayoutInflater layoutInflater;
    private ScheduleTrackItemsAdapter adapter;
    private BookmarkSnackBarDisplayFunction snackBarDisplayFunction;

    public static ScheduleTrackFragment newInstance(Track track) {
        Bundle args = new Bundle();
        args.putSerializable(TRACK, track);
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
            track = (Track) bundle.getSerializable(TRACK);
        }

        List<TrackItemHolder> items = getItemsList(track);
        snackBarDisplayFunction = new BookmarkSnackBarDisplayFunction(getActivity(), scheduleTrackItems);
        adapter = new ScheduleTrackItemsAdapter(context, layoutInflater, items);
        scheduleTrackItems.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putSerializable(TRACK, track);
    }

    private List<TrackItemHolder> getItemsList(Track track) {
        List<TrackPresentations> presentationsByTrack = TrackPresentations.getByTrack(track.getId());
        List<Event> events = Event.getAll();

        TreeSet<TrackItemHolder> trackItemsSet = new TreeSet<>();
        for (TrackPresentations p : presentationsByTrack) {
            trackItemsSet.add(new TrackItemHolder(p.getPresentation()));
        }
        for (Event e : events) {
            trackItemsSet.add(new TrackItemHolder(e));
        }
        return Lists.newArrayList(trackItemsSet);
    }

    @OnItemClick(R.id.schedule_track_items)
    public void onPresentationItemClick(int position) {
        TrackItemHolder item = adapter.getItem(position);
        if (item.isEventItem()) return;

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

    @OnItemLongClick(R.id.schedule_track_items)
    public boolean onPresentationLongItemClick(int position) {
        TrackItemHolder item = adapter.getItem(position);
        if (item.isEventItem()) return false;

        Presentation presentation = item.getPresentation();

        presentation.setBookmarked(!presentation.isBookmarked());
        presentation.save();
        snackBarDisplayFunction.apply(presentation.isBookmarked());

        return true;
    }
}
