package lv.rigadevday.android.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.R;
import lv.rigadevday.android.repository.Repository;
import lv.rigadevday.android.ui.navigation.RefreshDataEvent;
import lv.rigadevday.android.ui.navigation.ShowErrorMessageEvent;
import lv.rigadevday.android.utils.BaseApplication;
import rx.Subscription;

public abstract class BaseFragment extends Fragment {

    @Inject
    protected EventBus bus;

    @Inject
    protected Repository repository;

    protected Subscription dataFetchSubscription;

    Snackbar snackbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BaseApplication.inject(this);
        View view = inflater.inflate(contentViewId(), container, false);
        ButterKnife.bind(this, view);
        init();

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

        if (snackbar != null && snackbar.isShownOrQueued())
            snackbar.dismiss();
    }

    @Override
    public void onDestroy() {
        if (dataFetchSubscription != null)
            dataFetchSubscription.unsubscribe();
        super.onDestroy();
    }

    protected abstract void init();

    @LayoutRes
    protected abstract int contentViewId();

    public void onEvent(final ShowErrorMessageEvent event) {
        if (getView() == null) return;
        snackbar = Snackbar.make(getView(), R.string.error_message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.error_retry, v -> bus.post(new RefreshDataEvent()))
                .setActionTextColor(ContextCompat.getColor(getContext(), R.color.color_accent));
        snackbar.show();
    }

    public void onEvent(final RefreshDataEvent event) {
        init();
    }

}
