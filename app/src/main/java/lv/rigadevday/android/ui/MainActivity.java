package lv.rigadevday.android.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import lv.rigadevday.android.BaseApplication;
import lv.rigadevday.android.R;

public class MainActivity extends ActionBarActivity {

    @Inject
    EventBus bus;

    @Inject
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.inject(this);
        setContentView(R.layout.old_activity_screen);

        presenter.initPresenter(this);
        presenter.initNavigationDrawer();
        presenter.openScheduleScreen();
        presenter.openNavigationDrawer();

        if (presenter.firstApplicationStart()) {
            presenter.openNavigationDrawer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        presenter.syncNavigationDrawerState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        presenter.changeConfigurationOfNavigationDrawer(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return presenter.navigationDrawerHasHandledTouchEvent(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0 && !presenter.isDrawerOpen()) {
            presenter.openNavigationDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
