package lv.rigadevday.android.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.utils.BaseApplication;
import lv.rigadevday.android.ui.navigation.StubEvent;
import lv.rigadevday.android.repository.Repository;

public abstract class BaseFragment extends Fragment {

    @Inject
    EventBus bus;

    @Inject
    protected Repository mRepository;



	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BaseApplication.inject(this);
        View view = inflater.inflate(contentViewId(), container, false);
        ButterKnife.bind(this, view);
        init(savedInstanceState);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    protected void init(Bundle savedInstanceState){}

    @LayoutRes
    protected abstract int contentViewId();

    public void onEvent(final StubEvent event){
        // EventBus supports registering of base classes, but it needs at
        // least one onEvent() method in base class to register
    }

}
