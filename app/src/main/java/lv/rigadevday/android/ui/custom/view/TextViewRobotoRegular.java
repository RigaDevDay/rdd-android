package lv.rigadevday.android.ui.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewRobotoRegular extends TextView {
    public TextViewRobotoRegular(Context context) {
        super(context);
        createFont();
    }

    public TextViewRobotoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }

    public TextViewRobotoRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        try {
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
            setTypeface(font);
        } catch (RuntimeException e) {

        }
    }
}
