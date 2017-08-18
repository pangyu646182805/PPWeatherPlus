package com.ppyy.ppweatherplus.ui.activity;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.base.BaseActivity;

/**
 * Created by NeuroAndroid on 2017/8/18.
 */

public class SelectCityActivity extends BaseActivity {
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        setToolbarTitle(R.string.select_city);
    }
}
