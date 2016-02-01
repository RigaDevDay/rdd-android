package lv.rigadevday.android.ui.favorites;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import javax.inject.Inject;

import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.navigation.OpenTalkEvent;
import lv.rigadevday.android.ui.base.BaseFragment;

/**
 */
public class FavoritesFragment extends BaseFragment implements FavoritesFragmentPresenter {

    @Inject
    EventBus mBus;

    private FavoritesFragmentController mController;

    @Override
    @LayoutRes
    protected int contentViewId() {
        return R.layout.fragment_favorites;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mController = new FavoritesFragmentController(this);
    }

    @Override
    public void openTalk() {
        mBus.post(new OpenTalkEvent());
    }

    @OnClick(R.id.favorites_button)
    protected void onButtonClicked() {
        mController.buttonClicked();
    }

}

