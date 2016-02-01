package lv.rigadevday.android.ui.speakers;

/**
 */
public class SpeakersFragmentController {

    private final SpeakersFragmentPresenter mPresenter;

    public SpeakersFragmentController(SpeakersFragmentPresenter presenter) {
        mPresenter = presenter;
    }

    public void buttonClicked() {
        mPresenter.openTalk();
    }
}
