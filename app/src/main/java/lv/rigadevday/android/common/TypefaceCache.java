package lv.rigadevday.android.common;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class TypefaceCache {

    @Inject
    Context context;

    private Map<String, Typeface> cache = new HashMap<String, Typeface>();

    public Typeface loadFromAsset(String s) {
        Typeface typeface = cache.get(s);
        if(typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), s);
        }
        return typeface;
    }
}
