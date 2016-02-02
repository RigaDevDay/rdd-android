package lv.rigadevday.android.utils;

/**
 */
public class Utils {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static String imagePrefix(String img) {
        return "https://raw.githubusercontent.com/RigaDevDay/RigaDevDay.github.io/source/src/" + img;
    }
}
