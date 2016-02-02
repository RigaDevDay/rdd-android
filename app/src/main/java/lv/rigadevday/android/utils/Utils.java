package lv.rigadevday.android.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 */
public class Utils {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static String imagePrefix(String img) {
        return "https://raw.githubusercontent.com/RigaDevDay/RigaDevDay.github.io/source/src/" + img;
    }

    public static void goToWeb(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

}
