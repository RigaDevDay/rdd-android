package lv.rigadevday.android.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lv.rigadevday.android.R;
import lv.rigadevday.android.common.SharedPrefsService;

public class MainActivityPresenterImpl implements MainActivityPresenter {

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

    @Override
    public void initPresenter(Activity activity) {
        ButterKnife.inject(this, activity);
    }

    @Override
    public void initNavigationDrawer(final Activity activity) {
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

    @Override
    public void syncNavigationDrawerState() {
        drawerToggle.syncState();
    }

    @Override
    public void openNavigationDrawerOnFirstAppStart() {
        boolean subsequentStart = preferences.getBool(Preferences.SUBSEQUENT_START);
        if (!subsequentStart) {
            preferences.setBool(Preferences.SUBSEQUENT_START, true);
            drawerLayout.openDrawer(leftDrawer);
        }
    }

    @Override
    public void changeConfigurationOfNavigationDrawer(Configuration newConfig) {
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean navigationDrawerHasHandledTouchEvent(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item);
    }
}
