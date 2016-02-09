package lv.rigadevday.android.ui.schedule.day;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.repository.model.Schedule;
import lv.rigadevday.android.ui.base.BaseFragment;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class DayScheduleFragment extends BaseFragment {

    public static final String DAY_TITLE = "day_title";
    @Bind(R.id.day_schedule_recycler)
    protected RecyclerView recycler;

    // Not sure if this is the best way to do things
    @Bind({
            R.id.schedule_room_1,
            R.id.schedule_room_2,
            R.id.schedule_room_3,
            R.id.schedule_room_4,
            R.id.schedule_room_5
    })
    protected List<TextView> rooms;

    private Schedule daySchedule;
    private DayScheduleAdapter adapter;

    public static DayScheduleFragment newInstance(String dayTitle) {
        Bundle b = new Bundle();
        b.putString(DAY_TITLE, dayTitle);

        DayScheduleFragment fragment = new DayScheduleFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    protected int contentViewId() {
        return R.layout.fragment_day_schedule;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        dataFetchSubscription = repository.getDay(getArguments().getString(DAY_TITLE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(day -> {
                    daySchedule = day.schedule;

                    setRooms(daySchedule.roomNames);

                    recycler.setHasFixedSize(true);
                    recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    adapter = new DayScheduleAdapter(day.title, daySchedule.schedule);
                    recycler.setAdapter(adapter);
                });
    }

    public void setRooms(List<String> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            this.rooms.get(i).setVisibility(View.VISIBLE);
            this.rooms.get(i).setText(rooms.get(i));
        }
    }

}
