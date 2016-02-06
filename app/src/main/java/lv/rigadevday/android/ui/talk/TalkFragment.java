package lv.rigadevday.android.ui.talk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import butterknife.Bind;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.base.BaseFragment;

/**
 */
public class TalkFragment extends BaseFragment {

    public static final String EXTRA_DAY = "talk_day";
    public static final String EXTRA_TIME = "talk_time";
    public static final String EXTRA_INDEX = "talk_index_in_day";

    @Bind(R.id.speakers_label)
    TextView label;

    public static Fragment newInstance(Bundle extras) {
        TalkFragment fragment = new TalkFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    protected int contentViewId() {
        return R.layout.fragment_talk;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String day = getArguments().getString(EXTRA_DAY);
        String time = getArguments().getString(EXTRA_TIME);
        int index = getArguments().getInt(EXTRA_INDEX);

        label.setText(String.format("%s %s %d", day, time, index));
    }
}
