package lv.rigadevday.android.ui.custom.view.parallax;

import android.content.Context;
import android.view.View;

public interface ParallaxListItem {
    int getItemLayoutResource();

    void present(View view, Context context);
}
