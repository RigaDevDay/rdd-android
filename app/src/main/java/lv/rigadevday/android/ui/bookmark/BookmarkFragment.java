package lv.rigadevday.android.ui.bookmark;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import lv.rigadevday.android.R;
import lv.rigadevday.android.domain.Presentation;
import lv.rigadevday.android.ui.BaseFragment;

public class BookmarkFragment extends BaseFragment {

    @Inject
    Context context;

    @InjectView(R.id.bookmark_list)
    ListView bookmarkList;

    @Inject
    BookmarkListAdapter adapter;

    @Override
    protected int contentViewId() {
        return R.layout.bookmark_screen;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        bookmarkList.setAdapter(adapter);
    }
}