package lv.rigadevday.android.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import javax.inject.Inject;

public class DrawableService {

    @Inject
    public Context context;

    public Drawable loadDrawable(String photo) {
        Resources resources = context.getResources();
        int id = resources.getIdentifier(photo, "drawable", context.getPackageName());
        return resources.getDrawable(id);
    }

    public Drawable loadDrawable(int photo) {
        Resources resources = context.getResources();
        return resources.getDrawable(photo);
    }
}
