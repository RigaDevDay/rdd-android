package lv.rigadevday.android.v2.ui.favorites;

import lv.rigadevday.android.v2.ui.schedule.ScheduleFragmentPresenter;

/**
 */
public class FavoritesFragmentController {

    private final FavoritesFragmentPresenter mPresenter;

    public FavoritesFragmentController(FavoritesFragmentPresenter presenter) {
        mPresenter = presenter;
    }

    public void buttonClicked() {
        mPresenter.openTalk();
    }
}
