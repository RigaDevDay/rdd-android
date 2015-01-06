package lv.rigadevday.android.common;

import android.content.Context;
import android.graphics.Typeface;

import com.google.common.collect.Maps;

import java.util.Map;

public class TypefaceHelper {

    private static final Map<String, Typeface> map = Maps.newHashMap();

    public static Typeface getFont(Context ctx, String name) {
        if (map.containsKey(name)) return map.get(name);

        Typeface typeface = Typeface.createFromAsset(ctx.getAssets(), name);
        map.put(name, typeface);

        return typeface;
    }
}
