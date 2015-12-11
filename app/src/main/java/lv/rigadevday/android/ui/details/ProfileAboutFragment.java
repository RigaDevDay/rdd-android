package lv.rigadevday.android.ui.details;

import android.os.Bundle;
import android.widget.TextView;


import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.v2.ui.base.BaseFragment;

public class ProfileAboutFragment extends BaseFragment {

    @Bind(R.id.profile_about)
    TextView profileAbout;


    @Override
    protected int contentViewId() {
        return R.layout.old_profile_about_fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        Speaker speaker = (Speaker) getArguments().get("speaker");
        profileAbout.setText(speaker.getBio());
    }
}