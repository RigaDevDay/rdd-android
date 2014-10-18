package lv.rigadevday.android.ui.schedule;

import android.os.Bundle;

import javax.inject.Inject;

import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.BaseFragment;
import lv.rigadevday.android.ui.MainActivityPresenter;

public class ScheduleFragment extends BaseFragment {

    @Override
    protected int contentViewId() {
        return R.layout.schedule;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }
}
