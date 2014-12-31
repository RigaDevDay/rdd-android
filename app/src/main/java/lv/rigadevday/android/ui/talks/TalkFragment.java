package lv.rigadevday.android.ui.talks;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.google.common.base.Function;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import javax.inject.Inject;

import butterknife.InjectView;
import lv.rigadevday.android.R;
import lv.rigadevday.android.ui.BaseFragment;
import lv.rigadevday.android.ui.custom.BookmarkSnackBarDisplayFunction;

public class TalkFragment extends BaseFragment {

    @Inject
    LayoutInflater inflater;

    @InjectView(R.id.talks_list)
    ListView talksList;


    TalkListAdapter adapter;

    @Override
    protected int contentViewId() {
        return R.layout.talks_screen;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        BookmarkSnackBarDisplayFunction function = new BookmarkSnackBarDisplayFunction(getActivity(), talksList);
        adapter = new TalkListAdapter(getActivity(), inflater, function);
        talksList.setAdapter(adapter);
    }
}