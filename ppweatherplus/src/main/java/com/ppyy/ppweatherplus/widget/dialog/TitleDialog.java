package com.ppyy.ppweatherplus.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.widget.dialog.base.PYDialog;

/**
 * Created by NeuroAndroid on 2017/6/16.
 */

public class TitleDialog extends PYDialog<TitleDialog> {
    public TitleDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.layout_title_dialog;
    }
}
