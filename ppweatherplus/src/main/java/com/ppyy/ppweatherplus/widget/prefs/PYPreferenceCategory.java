package com.ppyy.ppweatherplus.widget.prefs;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.ppyy.ppweatherplus.R;

/**
 * Created by NeuroAndroid on 2017/8/31.
 */

public class PYPreferenceCategory extends PreferenceCategory {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PYPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PYPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PYPreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PYPreferenceCategory(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView tvTitle = (TextView) view.findViewById(android.R.id.title);

        tvTitle.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
    }
}
