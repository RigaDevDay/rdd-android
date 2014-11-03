package lv.rigadevday.android.ui.speakers;

import android.os.Bundle;
import android.widget.GridView;

import javax.inject.Inject;

import butterknife.InjectView;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.BaseFragment;

public class SpeakersFragment extends BaseFragment {

    @Inject
    SpeakersGridAdapter gridAdapter;

    @InjectView(R.id.schedule_grid)
    GridView gridView;

    @Override
    protected int contentViewId() {
        return R.layout.speakers_screen;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        gridView.setAdapter(gridAdapter);
    }
}
