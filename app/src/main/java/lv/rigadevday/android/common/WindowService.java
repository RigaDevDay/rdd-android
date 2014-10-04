package lv.rigadevday.android.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import javax.inject.Inject;

public class WindowService {

    @Inject
    public Context context;

    public int getDisplayWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return wm.getDefaultDisplay().getWidth();

    }

    public int getDisplayHeight() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
