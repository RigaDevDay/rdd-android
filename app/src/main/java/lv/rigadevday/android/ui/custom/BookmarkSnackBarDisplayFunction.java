package lv.rigadevday.android.ui.custom;

import android.content.Context;
import android.widget.ListView;

import com.google.common.base.Function;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import lv.rigadevday.android.R;

public class BookmarkSnackBarDisplayFunction implements Function<Boolean, Void> {
    private Context context;
    private ListView listView;

    public BookmarkSnackBarDisplayFunction(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    public Void apply(Boolean input) {
        int msg = input ? R.string.bookmarked : R.string.unbookmarked;
        SnackbarManager.show(
                Snackbar.with(context)
                        .attachToAbsListView(listView)
                        .colorResource(R.color.primary)
                        .textColorResource(R.color.white)
                        .text(msg));
        return null;
    }
}