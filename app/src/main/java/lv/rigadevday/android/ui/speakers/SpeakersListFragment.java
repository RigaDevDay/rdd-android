package lv.rigadevday.android.ui.speakers;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseFragment;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class SpeakersListFragment extends BaseFragment {

    @Bind(R.id.speakers_recycler)
    protected RecyclerView mRecycler;

    private SpeakersAdapter mAdapter;

    @Override
    @LayoutRes
    protected int contentViewId() {
        return R.layout.fragment_speakers;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null)
            setupList();
    }

    private void setupList() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(manager);

        mDataFetch = mRepository.getAllSpeakers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(list -> {
                    mAdapter = new SpeakersAdapter(list);
                    mRecycler.setAdapter(mAdapter);
                });
    }

}

