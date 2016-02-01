package lv.rigadevday.android.ui.speakers;

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
public class SpeakersFragment extends BaseFragment implements SpeakersFragmentPresenter {

    @Inject
    EventBus mBus;

    private SpeakersFragmentController mController;

    @Override
    @LayoutRes
    protected int contentViewId() {
        return R.layout.fragment_speakers;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mController = new SpeakersFragmentController(this);
    }

    @Override
    public void openTalk() {
        mBus.post(new OpenTalkEvent());
    }

    @OnClick(R.id.speakers_button)
    protected void onButtonClicked() {
        mController.buttonClicked();
    }

}

