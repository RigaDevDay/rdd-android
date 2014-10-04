package lv.rigadevday.android.common;

import java.util.List;

public class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    public static String join(List<?> list, String separator) {
        return join(list.toArray(), separator);
    }

    public static String join(final Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        final int noOfItems = array.length;
        if (noOfItems <= 0) {
            return "";
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }
}
