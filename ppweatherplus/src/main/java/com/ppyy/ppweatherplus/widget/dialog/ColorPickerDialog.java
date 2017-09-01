package com.ppyy.ppweatherplus.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.interfaces.SimpleTextWatcher;
import com.ppyy.ppweatherplus.utils.ColorUtils;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.UIUtils;
import com.ppyy.ppweatherplus.widget.ColorPickerView;
import com.ppyy.ppweatherplus.widget.dialog.base.DialogViewHelper;
import com.ppyy.ppweatherplus.widget.dialog.base.PYDialog;

/**
 * Created by NeuroAndroid on 2017/7/31.
 */

public class ColorPickerDialog extends PYDialog<ColorPickerDialog> {
    private EditText mEtR;
    private EditText mEtG;
    private EditText mEtB;
    private ColorPickerView mColorPickerView;

    private boolean mFromColorPickerView;
    private View mViewSelectedColor;
    private @ColorInt int mSelectedColor;

    public @ColorInt int getSelectedColor() {
        return mSelectedColor;
    }

    public ColorPickerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_color_picker_dialog;
    }

    @Override
    protected void initView() {
        DialogViewHelper viewHelper = getViewHelper();
        mViewSelectedColor = viewHelper.getView(R.id.view_selected_color);
        mEtR = viewHelper.getView(R.id.et_r);
        mEtG = viewHelper.getView(R.id.et_g);
        mEtB = viewHelper.getView(R.id.et_b);
        mColorPickerView = viewHelper.getView(R.id.picker_view);

        int[] toRGB = ColorUtils.colorToRGB(Color.BLACK);
        mEtR.setText(String.valueOf(toRGB[0]));
        mEtG.setText(String.valueOf(toRGB[1]));
        mEtB.setText(String.valueOf(toRGB[2]));
        transformRGB();

        mColorPickerView.setColorPickerListener(color -> {
            mFromColorPickerView = true;
            mSelectedColor = color;
            mViewSelectedColor.setBackgroundColor(mSelectedColor);
            int[] colorToRGB = ColorUtils.colorToRGB(mSelectedColor);
            mEtR.setText(String.valueOf(colorToRGB[0]));
            mEtG.setText(String.valueOf(colorToRGB[1]));
            mEtB.setText(String.valueOf(colorToRGB[2]));
        });

        mEtR.setOnTouchListener(new MyTouchListener(mEtR));
        mEtG.setOnTouchListener(new MyTouchListener(mEtG));
        mEtB.setOnTouchListener(new MyTouchListener(mEtB));

        mEtR.addTextChangedListener(new MyTextWatcher());
        mEtG.addTextChangedListener(new MyTextWatcher());
        mEtB.addTextChangedListener(new MyTextWatcher());
    }

    /**
     * 防止R，G，B的值超过255
     */
    private void preventOver255(EditText et, int value) {
        if (value > 255) {
            et.setText("255");
        }
    }

    private class MyTouchListener implements View.OnTouchListener {
        private EditText mEt;

        public MyTouchListener(EditText et) {
            mEt = et;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP:
                    L.e("onTouch ACTION_UP");
                    mFromColorPickerView = false;
                    mEt.clearFocus();
                    mEt.requestFocus();
                    break;
            }
            return false;
        }
    }

    private class MyTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!mFromColorPickerView) {
                transformRGB();
            }
        }
    }

    private void transformRGB() {
        String rValue = mEtR.getText().toString();
        String gValue = mEtG.getText().toString();
        String bValue = mEtB.getText().toString();

        int r;
        int g;
        int b;
        if (UIUtils.isEmpty(rValue)) {
            rValue = "0";
            r = 0;
            mEtR.setText(rValue);
        } else {
            r = Integer.parseInt(rValue);
            if (r > 255) mEtR.setText("255");
        }
        if (UIUtils.isEmpty(gValue)) {
            gValue = "0";
            g = 0;
            mEtG.setText(gValue);
        } else {
            g = Integer.parseInt(gValue);
            if (g > 255) mEtG.setText("255");
        }
        if (UIUtils.isEmpty(bValue)) {
            bValue = "0";
            b = 0;
            mEtB.setText(bValue);
        } else {
            b = Integer.parseInt(bValue);
            if (b > 255) mEtB.setText("255");
        }

        int color = ColorUtils.rgbToColor(r, g, b);
        mSelectedColor = color;
        mViewSelectedColor.setBackgroundColor(mSelectedColor);
        mColorPickerView.generateLinearGradient(mSelectedColor);
    }
}
