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
    protected Toolbar mToolbar;
    @Bind(R.id.activity_drawer_layout)
    protected DrawerLayout mDrawer;
    @Bind(R.id.activity_drawer_navigation_view)
    protected NavigationView mNavigation;
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerActivityController mController;

    @Override
    public int getLayoutId() {
        return R.layout.activity_drawer;
    }

    @Override
    public void initializeScreen() {
        mController = new DrawerActivityController(this);
        mController.initScreen();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setupToolbar() {
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.app_name, R.string.app_name);
        mDrawer.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void setupNavigationDrawerListener() {
        mNavigation.setNavigationItemSelectedListener(item -> {
            if (item.isChecked())
                item.setChecked(false);
            else
                item.setChecked(true);

            mDrawer.closeDrawers();
            return mController.navigationItemClicked(item);
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
        Snackbar.make(mDrawer, textId, Snackbar.LENGTH_SHORT).show();
    }

}
