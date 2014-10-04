package lv.rigadevday.android.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.InjectView;
import lv.rigadevday.android.MainActivity;
import lv.rigadevday.android.R;

public class MainActivityPresenterImpl implements MainActivityPresenter {

    @InjectView(R.id.content_frame)
    FrameLayout contentFrame;
    @InjectView(R.id.left_drawer)
    FrameLayout leftDrawer;
    @InjectView(R.id.right_drawer)
    FrameLayout rightDrawer;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    Activity activity;
    ActionBarDrawerToggle drawerToggle;

    public MainActivityPresenterImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void initNavigationDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, rightDrawer);

        drawerToggle = new ActionBarDrawerToggle(
                activity,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                activity.getActionBar().setTitle(R.string.app_name);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                activity.getActionBar().setTitle("Opened");
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(drawerToggle);

        activity.getActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void syncNavigationDrawerState() {
        drawerToggle.syncState();
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
