package lv.rigadevday.android.ui.drawer;

import android.view.MenuItem;

import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.usefulstuff.UsefulStuffFragment;
import lv.rigadevday.android.ui.favorites.FavoritesFragment;
import lv.rigadevday.android.ui.organizers.OrganizersFragment;
import lv.rigadevday.android.ui.schedule.ScheduleFragment;
import lv.rigadevday.android.ui.sponsors.SponsorsFragment;

/**
 */
public class DrawerActivityController {

    private final DrawerActivityPresenter mPresenter;

    private ScheduleFragment mScheduleFragment;
    private FavoritesFragment mFavoritesFragment;
    private SponsorsFragment mSponsorsFragment;
    private OrganizersFragment mOrganizersFragment;
    private UsefulStuffFragment mUsefulFragment;

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
            case R.id.navigation_item_favorites:
                lazyLoadFavorites();
                return true;
            case R.id.navigation_item_sponsors:
                lazyLoadSponsors();
                return true;
            case R.id.navigation_item_organizers:
                lazyLoadOrganizers();
                return true;
            case R.id.navigation_item_useful:
                lazyLoadUseful();
                return true;
        }
        return false;
    }

    private void lazyLoadSchedule() {
        if (mScheduleFragment == null)
            mScheduleFragment = new ScheduleFragment();
        mPresenter.openFragment(R.string.drawer_schedule, mScheduleFragment);
    }

    private void lazyLoadFavorites() {
        if (mFavoritesFragment == null)
            mFavoritesFragment = new FavoritesFragment();
        mPresenter.openFragment(R.string.drawer_favorites, mFavoritesFragment);
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

    private void lazyLoadUseful() {
        if (mUsefulFragment == null)
            mUsefulFragment = new UsefulStuffFragment();
        mPresenter.openFragment(R.string.drawer_useful, mUsefulFragment);
    }

}
