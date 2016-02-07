package lv.rigadevday.android.ui.speakers;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseFragment;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class SpeakersListFragment extends BaseFragment {

    @Bind(R.id.speakers_recycler)
    protected RecyclerView recyclerView;

    private SpeakersAdapter adapter;

    @Override
    @LayoutRes
    protected int contentViewId() {
        return R.layout.fragment_speakers;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        dataFetchSubscription = repository.getAllSpeakers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(list -> {
                    adapter = new SpeakersAdapter(list);
                    recyclerView.setAdapter(adapter);
                });
    }

}

