package lv.rigadevday.android.v2.ui.schedule;

/**
 */
public class ScheduleFragmentController {

    private final ScheduleFragmentPresenter mPresenter;

    public ScheduleFragmentController(ScheduleFragmentPresenter presenter) {
        mPresenter = presenter;
    }

    public void buttonClicked() {
        mPresenter.openTalk();
    }
}
