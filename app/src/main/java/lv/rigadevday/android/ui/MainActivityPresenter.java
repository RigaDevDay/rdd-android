package lv.rigadevday.android.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.MenuItem;

public interface MainActivityPresenter {

    void initPresenter(final Activity activity);
    void initNavigationDrawer(final Activity activity);

    void syncNavigationDrawerState();
    void openNavigationDrawerOnFirstAppStart();

    void changeConfigurationOfNavigationDrawer(Configuration newConfig);

    boolean navigationDrawerHasHandledTouchEvent(MenuItem item);
}
