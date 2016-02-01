package lv.rigadevday.android.ui.favorites;

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
