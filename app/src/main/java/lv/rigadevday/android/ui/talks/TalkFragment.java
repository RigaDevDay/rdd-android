package lv.rigadevday.android.ui.talks;

import android.os.Bundle;

import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.BaseFragment;

public class TalkFragment extends BaseFragment {

    @Override
    protected int contentViewId() {
        return R.layout.talks_screen;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }
}