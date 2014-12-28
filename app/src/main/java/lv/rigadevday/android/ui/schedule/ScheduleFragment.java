package lv.rigadevday.android.ui.schedule;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TitlePageIndicator;

import butterknife.InjectView;
import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Track;
import lv.rigadevday.android.ui.BaseFragment;

public class ScheduleFragment extends BaseFragment {

    @InjectView(R.id.scheduleViewPager)
    ViewPager scheduleViewPager;

    @InjectView(R.id.scheduleTitlePager)
    TitlePageIndicator pageIndicator;


    @Override
    protected int contentViewId() {
        return R.layout.schedule_screen;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        SchedulePageAdapter schedulePageAdapter = new SchedulePageAdapter(getFragmentManager(), Track.getAll());
        scheduleViewPager.setAdapter(schedulePageAdapter);
        pageIndicator.setViewPager(scheduleViewPager);
    }
}
