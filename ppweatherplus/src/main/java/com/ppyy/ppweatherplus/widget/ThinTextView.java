package com.ppyy.ppweatherplus.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ThinTextView extends AppCompatTextView {
    public ThinTextView(Context context) {
        this(context, null);
    }

    public ThinTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThinTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setIncludeFontPadding(false);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Thin.ttf");
        setTypeface(typeface);
    }
}
