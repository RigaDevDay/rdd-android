package lv.rigadevday.android.ui.drawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseFragment;
import lv.rigadevday.android.ui.base.BaseActivity;

/**
 */
public class DrawerActivity extends BaseActivity implements DrawerActivityPresenter {

    @Nullable
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
    @Bind(R.id.activity_drawer_layout)
    protected DrawerLayout drawer;
    @Bind(R.id.activity_drawer_navigation_view)
    protected NavigationView navigation;

    private ActionBarDrawerToggle drawerToggle;
    private DrawerActivityController controller;

    @Override
    public int getLayoutId() {
        return R.layout.activity_drawer;
    }

    @Override
    public void initializeScreen() {
        controller = new DrawerActivityController(this);
        controller.initScreen();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setupToolbar() {
        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(drawerToggle);
    }

    @Override
    public void setupNavigationDrawerListener() {
        navigation.setNavigationItemSelectedListener(item -> {
            if (item.isChecked())
                item.setChecked(false);
            else
                item.setChecked(true);

            drawer.closeDrawers();
            return controller.navigationItemClicked(item);
        });
    }

    @Override
    public void openFragment(@StringRes int titleId, BaseFragment fragment) {
        getSupportActionBar().setTitle(titleId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_drawer_content_frame, fragment)
                .commit();
    }

    @Override
    public void showMessage(@StringRes int textId) {
        Snackbar.make(drawer, textId, Snackbar.LENGTH_SHORT).show();
    }

}
