package lv.rigadevday.android.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import lv.rigadevday.android.BaseApplication;

public abstract class BaseFragment extends Fragment {

    @Inject
    Bus bus;

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

    protected abstract int contentViewId();

    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

}
