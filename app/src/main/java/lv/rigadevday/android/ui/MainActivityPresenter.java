package lv.rigadevday.android.ui;

import android.content.res.Configuration;
import android.view.MenuItem;

import lv.rigadevday.android.MainActivity;

public interface MainActivityPresenter {

    void initNavigationDrawer();

    void syncNavigationDrawerState();

    void changeConfigurationOfNavigationDrawer(Configuration newConfig);

    boolean navigationDrawerHasHandledTouchEvent(MenuItem item);
}
