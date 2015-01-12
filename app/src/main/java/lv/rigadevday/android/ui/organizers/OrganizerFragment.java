package lv.rigadevday.android.ui.organizers;

import android.os.Bundle;

import butterknife.OnClick;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.BaseFragment;
import lv.rigadevday.android.common.DialogHelper;

public class OrganizerFragment extends BaseFragment {

    @Override
    protected int contentViewId() {
        return R.layout.organizer_screen;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @OnClick(R.id.jug)
    public void showJugDialog() {
        showDetailsDialog(R.string.jug_name, R.string.jug_description);
    }

    @OnClick(R.id.gdg)
    public void showGdgDialog() {
        showDetailsDialog(R.string.gdg_name, R.string.gdg_description);
    }

    @OnClick(R.id.lvoug)
    public void showLvougDialog() {
        showDetailsDialog(R.string.lvoug_name, R.string.lvoug_description);
    }

    private void showDetailsDialog(int titleRes, int descriptionRes) {
        DialogHelper.getStyled(getActivity())
                .title(titleRes)
                .content(descriptionRes)
                .positiveText(R.string.OK)
                .show();
    }
}