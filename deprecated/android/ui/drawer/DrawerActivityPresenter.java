package lv.rigadevday.android.ui.drawer;

import android.support.annotation.StringRes;

import lv.rigadevday.android.ui.base.BaseFragment;

/**
 */
public interface DrawerActivityPresenter {
    void setupToolbar();

    void setupNavigationDrawerListener();

    void openFragment(@StringRes int titleId, BaseFragment fragment);

    void showMessage(@StringRes int textId);
}
