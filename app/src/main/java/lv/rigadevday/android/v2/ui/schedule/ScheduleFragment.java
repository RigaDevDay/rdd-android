package lv.rigadevday.android.v2.ui.schedule;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import javax.inject.Inject;

import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.R;
import lv.rigadevday.android.v2.ui.base.BaseFragment;
import lv.rigadevday.android.v2.navigation.OpenTalkEvent;

/**
 */
public class ScheduleFragment extends BaseFragment implements ScheduleFragmentPresenter {

    @Inject
    EventBus mBus;

    private ScheduleFragmentController mController;

    @Override
    @LayoutRes
    protected int contentViewId() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mController = new ScheduleFragmentController(this);
    }

    @Override
    public void openTalk() {
        mBus.post(new OpenTalkEvent());
    }

    @OnClick(R.id.schedule_button)
    protected void onButtonClicked() {
        mController.buttonClicked();
    }

}

