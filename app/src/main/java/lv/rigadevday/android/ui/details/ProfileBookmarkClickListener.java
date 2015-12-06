package lv.rigadevday.android.ui.details;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lv.rigadevday.android.R;
import lv.rigadevday.android.common.DialogHelper;
import lv.rigadevday.android.domain.Presentation;

public class ProfileBookmarkClickListener implements View.OnClickListener {

    private final Context ctx;
    private final List<Presentation> presentations;
    private final ImageView icon;


    public ProfileBookmarkClickListener(Context ctx, List<Presentation> presentations, ImageView icon) {
        this.ctx = ctx;
        this.presentations = presentations;
        this.icon = icon;
    }

    @Override
    public void onClick(View view) {
        if (presentations.size() == 1) {
            bookmarkSingle();
        } else {
            DialogBuilderHelper helper = new DialogBuilderHelper();
            DialogHelper.getStyled(ctx)
                    .title(R.string.presentations)
                    .items(helper.titles)
                    .itemsCallbackMultiChoice(helper.getSelectedIndeces(), (dialog, which, text) -> {
                        List<Integer> selected = Arrays.asList(which);
                        for (int i = 0; i < presentations.size(); i++) {
                            Presentation p = presentations.get(i);
                            p.setBookmarked(selected.contains(i));
                            p.save();
                        }
                        icon.setImageResource(which.length > 0 ? R.drawable.icon_bookmark : R.drawable.icon_bookmark_empty);
                        return true;
                    })
                    .positiveText(R.string.choose)
                    .negativeText(R.string.cancel)
                    .show();
        }
    }

    private void bookmarkSingle() {
        Presentation p = presentations.get(0);
        p.setBookmarked(!p.isBookmarked());
        p.save();
        icon.setImageResource(p.isBookmarked() ? R.drawable.icon_bookmark : R.drawable.icon_bookmark_empty);
    }


    private class DialogBuilderHelper {
        Set<Integer> selectedIndeces = new HashSet<>();
        CharSequence[] titles = new CharSequence[presentations.size()];

        private DialogBuilderHelper() {
            for (int i = 0; i < presentations.size(); i++) {
                Presentation p = presentations.get(i);
                titles[i] = p.getTitle();
                if (p.isBookmarked()) {
                    selectedIndeces.add(i);
                }
            }
        }

        public Integer[] getSelectedIndeces() {
            return selectedIndeces.toArray(new Integer[selectedIndeces.size()]);
        }
    }
}