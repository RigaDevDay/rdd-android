package lv.rigadevday.android.v2.ui.schedule;

import android.util.Log;

import javax.inject.Inject;

import lv.rigadevday.android.v2.model.DataRoot;
import lv.rigadevday.android.v2.networking.DataFetchService;
import lv.rigadevday.android.v2.networking.DataFetchStub;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class ScheduleFragmentController {


    private final ScheduleFragmentPresenter mPresenter;
    private Subscription mDataFetch;

    public ScheduleFragmentController(ScheduleFragmentPresenter presenter) {
        mPresenter = presenter;
    }

    public void buttonClicked() {
        mPresenter.openTalk();
    }

    public void fetch() {
        Log.wtf("DEBUG", "fetch requested!");
        // TODO make call for DataFetchService.getData() when it is in place
        mDataFetch = DataFetchStub.getData(mPresenter.getContext())
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(data -> data.days)
                .subscribe(mPresenter::setupList);
    }

    public void onResume() {

    }

    public void onPause() {
        if (mDataFetch != null && !mDataFetch.isUnsubscribed())
            mDataFetch.unsubscribe();
    }
}
