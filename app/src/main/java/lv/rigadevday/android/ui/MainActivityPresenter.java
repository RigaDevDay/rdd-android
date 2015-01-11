package lv.rigadevday.android.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import lv.rigadevday.android.R;
import lv.rigadevday.android.application.navigation.NavigationOption;
import lv.rigadevday.android.common.SharedPrefsService;
import lv.rigadevday.android.common.SocialNetworkNagivationService;
import lv.rigadevday.android.infrastructure.FragmentFactory;
import lv.rigadevday.android.ui.navigation.NavigationAdapter;
import lv.rigadevday.android.ui.schedule.ScheduleFragment;

@Singleton
public class MainActivityPresenter {

    public static final String MAIN_FRAGMENT_TAG = "MainFragment";
    @Inject
    Context context;
    @InjectView(R.id.content_frame)
    FrameLayout contentFrame;
    @InjectView(R.id.left_drawer)
    FrameLayout leftDrawer;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.navigation_listView)
    ListView listView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    SocialNetworkNagivationService socialsService;

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

        drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                listView.invalidateViews(); //Refresh counter for bookmarks
            }
        };
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
                .replace(R.id.content_frame, fragment, MAIN_FRAGMENT_TAG)
                .commit();
    }

    @OnItemClick(R.id.navigation_listView)
    public void onItemClick(int position) {
        NavigationOption option = navigationAdapter.getItem(position);
        Fragment currentFragment = activity.getSupportFragmentManager().findFragmentByTag(MAIN_FRAGMENT_TAG);

        if (currentFragment == null || !currentFragment.getClass().equals(option.getFragmentClass())) {
            BaseFragment fragment = FragmentFactory.create(option.getFragmentClass());
            clearBackStack();
            changeContentFragment(fragment);
        }

        closeLeftDrawerLayout();
    }

    @OnClick(R.id.google_plus)
    public void onGooglePlus() {
        socialsService.goGooglePlus(context.getString(R.string.rddGooglePlus));
    }

    @OnClick(R.id.facebook)
    public void onFacebook() {
        socialsService.goFacebook(context.getString(R.string.rddFacebook));
    }

    @OnClick(R.id.twitter)
    public void onTwitter() {
        socialsService.goTwitter(context.getString(R.string.rddTwitter));
    }

    @OnClick(R.id.linkedin)
    public void onLinkedIn() {
        String linkedInUrl = context.getString(R.string.rddLinkedIn);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void clearBackStack() {
        FragmentManager fm = activity.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public boolean firstApplicationStart() {
        boolean subsequentStart = preferences.getBool(PreferencesConstants.SUBSEQUENT_START);
        if (!subsequentStart) {
            preferences.setBool(PreferencesConstants.SUBSEQUENT_START, true);
            return true;
        }
        return false;
    }

    public boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }
}
