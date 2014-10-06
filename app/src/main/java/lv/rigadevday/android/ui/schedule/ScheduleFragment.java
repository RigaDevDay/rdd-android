package lv.rigadevday.android.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;

import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.BaseFragment;

public class ScheduleFragment extends BaseFragment {

    @Override
    protected int contentViewId() {
        return R.layout.schedule_screen;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        StickyGridHeadersGridView grid = (StickyGridHeadersGridView) getView().findViewById(R.id.schedule_grid);
        grid.setAreHeadersSticky(false);
        grid.setAdapter(new ScheduleGridAdapter(getActivity()
                , new Schedule(Presentation.getAll()),
                R.layout.schedule_header, R.layout.schedule_item));
    }
}
