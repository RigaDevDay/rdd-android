package lv.rigadevday.android.ui.drawer;

import android.view.MenuItem;

import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.venues.VenuesFragment;
import lv.rigadevday.android.ui.speakers.SpeakersListFragment;
import lv.rigadevday.android.ui.organizers.OrganizersFragment;
import lv.rigadevday.android.ui.schedule.ScheduleFragment;
import lv.rigadevday.android.ui.sponsors.SponsorsFragment;

/**
 */
public class DrawerActivityController {

    private final DrawerActivityPresenter mPresenter;

    private ScheduleFragment mScheduleFragment;
    private SpeakersListFragment mSpeakersFragment;
    private SponsorsFragment mSponsorsFragment;
    private OrganizersFragment mOrganizersFragment;
    private VenuesFragment mVenuesFragment;

    public DrawerActivityController(DrawerActivityPresenter presenter) {
        mPresenter = presenter;
    }

    public void initScreen() {
        mPresenter.setupToolbar();
        mPresenter.setupNavigationDrawerListener();

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
            case R.id.navigation_item_sponsors:
                lazyLoadSponsors();
                return true;
            case R.id.navigation_item_organizers:
                lazyLoadOrganizers();
                return true;
        }
        return false;
    }

    private void lazyLoadSchedule() {
        if (mScheduleFragment == null)
            mScheduleFragment = new ScheduleFragment();
        mPresenter.openFragment(R.string.drawer_schedule, mScheduleFragment);
    }

    private void lazyLoadSpeakers() {
        if (mSpeakersFragment == null)
            mSpeakersFragment = new SpeakersListFragment();
        mPresenter.openFragment(R.string.drawer_speakers, mSpeakersFragment);
    }

    private void lazyLoadSponsors() {
        if (mSponsorsFragment == null)
            mSponsorsFragment = new SponsorsFragment();
        mPresenter.openFragment(R.string.drawer_sponsors, mSponsorsFragment);
    }

    private void lazyLoadOrganizers() {
        if (mOrganizersFragment == null)
            mOrganizersFragment = new OrganizersFragment();
        mPresenter.openFragment(R.string.drawer_organizers, mOrganizersFragment);
    }

    private void lazyLoadVenues() {
        if (mVenuesFragment == null)
            mVenuesFragment = new VenuesFragment();
        mPresenter.openFragment(R.string.drawer_venues, mVenuesFragment);
    }

}
