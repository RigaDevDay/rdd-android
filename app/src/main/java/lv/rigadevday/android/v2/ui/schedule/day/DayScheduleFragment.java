package lv.rigadevday.android.v2.ui.schedule.day;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.v2.model.Schedule;
import lv.rigadevday.android.v2.ui.base.BaseFragment;

/**
 */
public class DayScheduleFragment extends BaseFragment {

    @Bind(R.id.day_schedule_recycler)
    protected RecyclerView mRecycler;

    // Not sure if this is the best way to do things
    @Bind({
            R.id.schedule_room_1,
            R.id.schedule_room_2,
            R.id.schedule_room_3,
            R.id.schedule_room_4,
            R.id.schedule_room_5
    })
    protected List<TextView> mRooms;

    private Schedule mSchedule;

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
        this.mSchedule = schedule;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        setRooms(mSchedule.roomNames);

        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecycler.setAdapter(new DayScheduleAdapter(mSchedule.schedule));
    }

    public void setRooms(List<String> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            mRooms.get(i).setVisibility(View.VISIBLE);
            mRooms.get(i).setText(rooms.get(i));
        }
    }

}
