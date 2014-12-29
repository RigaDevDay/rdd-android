package lv.rigadevday.android.ui.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewRobotoLight extends TextView {
    public TextViewRobotoLight(Context context) {
        super(context);
        createFont();
    }

    public TextViewRobotoLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }

    public TextViewRobotoLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        try {
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
            setTypeface(font);
        } catch (RuntimeException e) {

        }
    }
}
