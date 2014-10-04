package lv.rigadevday.android.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import lv.rigadevday.android.BaseApplication;
import lv.rigadevday.android.R;

public class MainActivity extends FragmentActivity {

    @Inject
    Bus bus;

    @Inject
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.inject(this);
        setContentView(R.layout.activity_my);
        ButterKnife.inject(presenter, this);

        presenter.initNavigationDrawer(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = drawerLayout.isDrawerOpen(leftDrawer);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
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
        if (presenter.navigationDrawerHasHandledTouchEvent(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
