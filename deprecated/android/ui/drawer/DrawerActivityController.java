package lv.rigadevday.android.ui.drawer;

import android.view.MenuItem;

import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.organizers.OrganizersFragment;
import lv.rigadevday.android.ui.schedule.ScheduleFragment;
import lv.rigadevday.android.ui.speakers.SpeakersListFragment;
import lv.rigadevday.android.ui.venues.VenuesRootFragment;

/**
 */
public class DrawerActivityController {

    private final DrawerActivityPresenter presenter;

    private ScheduleFragment scheduleFragment;
    private SpeakersListFragment speakersFragment;
    private OrganizersFragment organizersFragment;
    private VenuesRootFragment venuesFragment;

    public DrawerActivityController(DrawerActivityPresenter presenter) {
        this.presenter = presenter;
    }

    public void initScreen() {
        presenter.setupToolbar();
        presenter.setupNavigationDrawerListener();

        lazyLoadSchedule();
    }

    public boolean navigationItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_item_schedule:
                lazyLoadSchedule();
                return true;
            case R.id.navigation_item_speakers:
                lazyLoadSpeakers();
                return true;
            case R.id.navigation_item_venues:
                lazyLoadVenues();
                return true;
            case R.id.navigation_item_organizers:
                lazyLoadOrganizers();
                return true;
        }
        return false;
    }

    private void lazyLoadSchedule() {
        if (scheduleFragment == null)
            scheduleFragment = new ScheduleFragment();
        presenter.openFragment(R.string.drawer_schedule, scheduleFragment);
    }

    private void lazyLoadSpeakers() {
        if (speakersFragment == null)
            speakersFragment = new SpeakersListFragment();
        presenter.openFragment(R.string.drawer_speakers, speakersFragment);
    }

    private void lazyLoadOrganizers() {
        if (organizersFragment == null)
            organizersFragment = new OrganizersFragment();
        presenter.openFragment(R.string.drawer_organizers, organizersFragment);
    }

    private void lazyLoadVenues() {
        if (venuesFragment == null)
            venuesFragment = new VenuesRootFragment();
        presenter.openFragment(R.string.drawer_venues, venuesFragment);
    }

}
