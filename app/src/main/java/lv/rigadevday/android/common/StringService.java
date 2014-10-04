package lv.rigadevday.android.common;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class StringService {

    @Inject
    public Context context;

    public String loadString(String name) {
        Resources resources = context.getResources();
        int id = resources.getIdentifier(name, "string", context.getPackageName());
        return resources.getString(id);
    }

    public String safeLoadString(String name) {
        try {
            Resources resources = context.getResources();
            int id = resources.getIdentifier(name, "string", context.getPackageName());
            return resources.getString(id);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }

    public String loadString(int id) {
        Resources resources = context.getResources();
        return resources.getString(id);
    }

    public String[] loadStringArray(String name) {
        Resources resources = context.getResources();
        int id = resources.getIdentifier(name, "array", context.getPackageName());
        return resources.getStringArray(id);
    }

    public String[] loadStringArray(int id) {
        Resources resources = context.getResources();
        return resources.getStringArray(id);
    }

    public String loadStringArrayItem(int id, int index) {
        return loadStringArray(id)[index];
    }

    public String loadStringArrayItem(String name, int index) {
        return loadStringArray(name)[index];
    }

    public List<String> loadStrings(List<String> strings) {
        List<String> result = new ArrayList<String>();
        for (String string : strings) {
            result.add(loadString(string));
        }
        return result;
    }

    public List<String> loadStrings(int ... ids) {
        List<String> result = new ArrayList<String>();
        for (int id : ids) {
            result.add(loadString(id));
        }
        return result;
    }
}
