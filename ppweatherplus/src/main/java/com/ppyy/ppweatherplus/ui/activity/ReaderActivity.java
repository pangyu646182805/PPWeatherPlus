package com.ppyy.ppweatherplus.ui.activity;

import android.content.Intent;
import android.transition.Slide;
import android.view.Gravity;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.base.BaseActivity;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.utils.SystemUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/9/4.
 */

public class ReaderActivity extends BaseActivity {
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_reader;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        setToolbarTitle(R.string.reader);
    }

    @Override
    protected void initData() {
        if (SystemUtils.greaterLOLLIPOP()) {
            Intent intent = getIntent();
            String transition = intent.getStringExtra(Constant.TRANSITION);
            if ("slide".equals(transition)) {
                Slide slide = new Slide();
                slide.setSlideEdge(Gravity.BOTTOM);
                slide.setDuration(333);
                getWindow().setEnterTransition(slide);
            }
        }
    }
}
