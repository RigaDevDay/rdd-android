package lv.rigadevday.android.ui.schedule;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Track;
import lv.rigadevday.android.v2.ui.base.BaseFragment;

public class ScheduleFragment extends BaseFragment {

    @Bind(R.id.scheduleViewPager)
    ViewPager scheduleViewPager;

    @Bind(R.id.scheduleTitlePager)
    TabLayout pageIndicator;

    @Override
    protected int contentViewId() {
        return R.layout.old_schedule_screen;
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
        SchedulePageAdapter schedulePageAdapter = new SchedulePageAdapter(getChildFragmentManager(), Track.getAll());
        scheduleViewPager.setAdapter(schedulePageAdapter);
        //pageIndicator.setTabsFromPagerAdapter(schedulePageAdapter);
        pageIndicator.setupWithViewPager(scheduleViewPager);
    }
}
