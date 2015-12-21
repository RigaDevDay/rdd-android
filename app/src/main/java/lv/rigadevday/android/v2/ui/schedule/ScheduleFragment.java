package lv.rigadevday.android.v2.ui.schedule;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.R;
import lv.rigadevday.android.v2.model.Days;
import lv.rigadevday.android.v2.ui.base.BaseFragment;
import lv.rigadevday.android.v2.navigation.OpenTalkEvent;

/**
 */
public class ScheduleFragment extends BaseFragment implements ScheduleFragmentPresenter {

    @Inject
    EventBus mBus;

    @Bind(R.id.schedule_label)
    TextView mLabel;

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

    @Override
    public void onResume() {
        super.onResume();
        mController.onResume();
        mController.fetch();
    }

    @Override
    public void onPause() {
        super.onPause();
        mController.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setupList(List<Days> data) {
        mLabel.setText(data.toString());
    }
}

