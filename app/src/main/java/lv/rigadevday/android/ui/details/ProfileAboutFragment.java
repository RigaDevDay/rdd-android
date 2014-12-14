package lv.rigadevday.android.ui.details;

import android.os.Bundle;
import android.widget.TextView;


import butterknife.InjectView;
import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Speaker;
import lv.rigadevday.android.ui.BaseFragment;

public class ProfileAboutFragment extends BaseFragment {

    @InjectView(R.id.profile_about)
    TextView profileAbout;


    @Override
    protected int contentViewId() {
        return R.layout.profile_about_fragment;
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