package lv.rigadevday.android.common;

import android.content.Context;
import android.graphics.Typeface;

import com.afollestad.materialdialogs.MaterialDialog;

import lv.rigadevday.android.R;

public class DialogHelper {

    public static MaterialDialog.Builder getStyled(Context context) {

        return new MaterialDialog.Builder(context)
                .titleColorRes(R.color.primary)
                .contentColorRes(R.color.color_404040)
                .positiveColorRes(R.color.cerulean)
                .negativeColorRes(R.color.cerulean);
    }
}
