package lv.rigadevday.android.ui;

import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.FrameLayout;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lv.rigadevday.android.R;
import lv.rigadevday.android.common.SharedPrefsService;
import lv.rigadevday.android.ui.schedule.ScheduleFragment;

@Singleton
public class MainActivityPresenter {

    @InjectView(R.id.content_frame)
    FrameLayout contentFrame;
    @InjectView(R.id.left_drawer)
    FrameLayout leftDrawer;
    @InjectView(R.id.right_drawer)
    FrameLayout rightDrawer;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Inject
    SharedPrefsService preferences;

    ActionBarDrawerToggle drawerToggle;

    private FragmentActivity activity;

    public void initPresenter(FragmentActivity activity) {
        this.activity = activity;
        ButterKnife.inject(this, activity);
    }

    public void initNavigationDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, rightDrawer);

        drawerToggle = new ActionBarDrawerToggle(
                activity,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.app_name,
                R.string.app_name
        );
        drawerLayout.setDrawerListener(drawerToggle);

        activity.getActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getActionBar().setHomeButtonEnabled(true);
    }

    public void syncNavigationDrawerState() {
        drawerToggle.syncState();
    }

    public void openNavigationDrawerOnFirstAppStart() {
        boolean subsequentStart = preferences.getBool(Preferences.SUBSEQUENT_START);
        if (!subsequentStart) {
            preferences.setBool(Preferences.SUBSEQUENT_START, true);
            drawerLayout.openDrawer(leftDrawer);
        }
    }

    public void changeConfigurationOfNavigationDrawer(Configuration newConfig) {
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean navigationDrawerHasHandledTouchEvent(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item);
    }

    public void openScheduleScreen() {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_frame, new ScheduleFragment(), ScheduleFragment.class.getName())
                .commit();
    }

    public void changeContentFragment(Fragment fragment) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment, fragment.getClass().getName())
                .commit();
    }
}
