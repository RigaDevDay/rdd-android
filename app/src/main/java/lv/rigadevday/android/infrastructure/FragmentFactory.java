package lv.rigadevday.android.infrastructure;

import android.support.v4.app.Fragment;

public class FragmentFactory {

    private FragmentFactory() {}

    public static  <T extends Fragment> T create(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
