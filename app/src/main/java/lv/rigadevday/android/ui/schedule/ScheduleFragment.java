package lv.rigadevday.android.ui.schedule;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.repository.model.Day;
import lv.rigadevday.android.ui.base.BaseFragment;
import lv.rigadevday.android.ui.base.ViewPagerAdapter;
import lv.rigadevday.android.ui.schedule.day.DayScheduleFragment;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class ScheduleFragment extends BaseFragment {

    @Bind(R.id.schedule_tabs)
    TabLayout tabs;
    @Bind(R.id.schedule_pager)
    ViewPager pager;

    private ViewPagerAdapter adapter;

    @Override
    @LayoutRes
    protected int contentViewId() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        dataFetchSubscription = repository.getAllDays()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Day>() {
                    @Override
                    public void onCompleted() {
                        pager.setAdapter(adapter);
                        tabs.setupWithViewPager(pager);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Day day) {
                        adapter.addFragment(DayScheduleFragment.newInstance(day.title), day.title);
                    }
                });
    }

}

