package lv.rigadevday.android.ui.organizers;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseFragment;
import lv.rigadevday.android.ui.navigation.ShowErrorMessageEvent;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 */
public class OrganizersFragment extends BaseFragment {

    @Bind(R.id.organizers_recycler)
    protected RecyclerView recycler;

    private OrganizersAdapter adapter;

    @Override
    protected int contentViewId() {
        return R.layout.fragment_organizers;
    }

    @Override
    protected void init() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case OrganizersAdapter.TYPE_TITLE:
                        return 3;
                    case OrganizersAdapter.TYPE_LOGO:
                        return 1;
                    default:
                        return -1;
                }
            }
        });
        recycler.setLayoutManager(manager);

        dataFetchSubscription = repository.getSponsors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            adapter = new OrganizersAdapter(list);
                            recycler.setAdapter(adapter);
                        },
                        error -> bus.post(new ShowErrorMessageEvent())
                );
    }

}