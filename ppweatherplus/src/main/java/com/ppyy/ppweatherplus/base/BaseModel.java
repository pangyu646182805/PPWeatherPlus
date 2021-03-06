package com.ppyy.ppweatherplus.base;

import com.ppyy.ppweatherplus.model.api.ApiService;
import com.ppyy.ppweatherplus.net.RetrofitUtils;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class BaseModel implements IModel {
    protected ApiService mService;

    public BaseModel(String baseUrl) {
        this(baseUrl, false);
    }

    public BaseModel(String baseUrl, boolean needCache) {
        mService = RetrofitUtils.getInstance(baseUrl, needCache).create(ApiService.class);
    }

    @Override
    public void onDestroy() {
        mService = null;
    }
}
