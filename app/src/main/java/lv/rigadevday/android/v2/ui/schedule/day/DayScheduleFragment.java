package lv.rigadevday.android.v2.ui.schedule.day;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.v2.model.Schedule;
import lv.rigadevday.android.v2.ui.base.BaseFragment;

/**
 */
public class DayScheduleFragment extends BaseFragment {

    @Bind(R.id.day_schedule_label)
    TextView mLabel;

    private Schedule schedule;

    public static DayScheduleFragment newInstance(Schedule schedule) {
        DayScheduleFragment fragment = new DayScheduleFragment();
        fragment.setSchedule(schedule);
        return fragment;
    }

    @Override
    protected int contentViewId() {
        return R.layout.fragment_day_schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mLabel.setText(schedule.toString());
    }
}
