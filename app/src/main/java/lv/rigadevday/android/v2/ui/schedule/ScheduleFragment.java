package lv.rigadevday.android.v2.ui.schedule;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.v2.model.Day;
import lv.rigadevday.android.v2.networking.DataFetchStub;
import lv.rigadevday.android.v2.ui.base.BaseFragment;
import lv.rigadevday.android.v2.ui.base.ViewPagerAdapter;
import lv.rigadevday.android.v2.ui.schedule.day.DayScheduleFragment;
import rx.Observable;
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
    private Subscription mDataFetch;

    @Override
    @LayoutRes
    protected int contentViewId() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null)
            setupList();
    }

    public void setupList() {
        // TODO make call for DataFetchService.getData() when it is in place
        mDataFetch = DataFetchStub.getData(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(data -> Observable.from(data.days))
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
                        mAdapter.addFragment(DayScheduleFragment.newInstance(day.schedule), day.title);
                    }
                });
    }

    @Override
    public void onDestroy() {
        mDataFetch.unsubscribe();
        super.onDestroy();
    }

}

