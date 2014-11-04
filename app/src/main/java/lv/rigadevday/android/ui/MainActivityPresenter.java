package lv.rigadevday.android.ui;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import lv.rigadevday.android.Mocks;
import lv.rigadevday.android.R;
import lv.rigadevday.android.application.navigation.NavigationOption;
import lv.rigadevday.android.common.SharedPrefsService;
import lv.rigadevday.android.infrastructure.FragmentFactory;
import lv.rigadevday.android.ui.navigation.NavigationAdapter;
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
    @InjectView(R.id.navigation_listView)
    ListView listView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    SharedPrefsService preferences;

    @Inject
    NavigationAdapter navigationAdapter;

    ActionBarDrawerToggle drawerToggle;

    private ActionBarActivity activity;

    public void initPresenter(ActionBarActivity activity) {
        this.activity = activity;
        ButterKnife.inject(this, activity);
    }

    public void initNavigationDrawer() {
        activity.setSupportActionBar(toolbar);

        drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, R.string.app_name, R.string.app_name);
        drawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.setDrawerListener(drawerToggle);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        listView.setAdapter(navigationAdapter);       
    }

    public void syncNavigationDrawerState() {
        drawerToggle.syncState();
    }

    public void openNavigationDrawer() {
        drawerLayout.openDrawer(leftDrawer);
    }

    public void changeConfigurationOfNavigationDrawer(Configuration newConfig) {
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean navigationDrawerHasHandledTouchEvent(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item);
    }

    public void openScheduleScreen() {
        changeContentFragment(new ScheduleFragment());
    }

    public void closeLeftDrawerLayout() {
        drawerLayout.closeDrawer(leftDrawer);
    }

    public void changeContentFragment(Fragment fragment) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment, fragment.getClass().getName())
                .commit();
    }


    @OnItemClick(R.id.navigation_listView)
    public void onItemClick(int position) {
        //TODO: doesn't work anymore ;[
        NavigationOption option = navigationAdapter.getItem(position);
        BaseFragment fragment = FragmentFactory.create(option.getFragmentClass());

        changeContentFragment(fragment);
        closeLeftDrawerLayout();
    }

    public boolean firstApplicationStart() {
        boolean subsequentStart = preferences.getBool(PreferencesConstants.SUBSEQUENT_START);
        if (!subsequentStart) {
            preferences.setBool(PreferencesConstants.SUBSEQUENT_START, true);
            return true;
        }
        return false;
    }

    public void syncData() {
        Mocks.createScheduleSlots(activity);
        Mocks.createSpeakers(activity);
    }
}
