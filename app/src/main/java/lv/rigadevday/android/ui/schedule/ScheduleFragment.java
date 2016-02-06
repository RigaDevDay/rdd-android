package lv.rigadevday.android.ui.schedule;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.repository.model.Day;
import lv.rigadevday.android.ui.base.BaseFragment;
import lv.rigadevday.android.ui.base.ViewPagerAdapter;
import lv.rigadevday.android.ui.schedule.day.DayScheduleFragment;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class ScheduleFragment extends BaseFragment {

    @Bind(R.id.schedule_tabs)
    TabLayout mTabs;
    @Bind(R.id.schedule_pager)
    ViewPager mPager;

    private ViewPagerAdapter mAdapter;

    @Override
    @LayoutRes
    protected int contentViewId() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mAdapter = new ViewPagerAdapter(getChildFragmentManager());

        mDataFetch = mRepository.getAllDays()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Day>() {
                    @Override
                    public void onCompleted() {
                        mPager.setAdapter(mAdapter);
                        mTabs.setupWithViewPager(mPager);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Day day) {
                        mAdapter.addFragment(DayScheduleFragment.newInstance(day.title), day.title);
                    }
                });
    }

}

