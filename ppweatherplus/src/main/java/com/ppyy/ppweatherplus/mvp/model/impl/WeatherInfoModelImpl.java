package com.ppyy.ppweatherplus.mvp.model.impl;

import com.ppyy.ppweatherplus.base.BaseModel;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.mvp.model.IWeatherInfoModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/8/18.
 */

public class WeatherInfoModelImpl extends BaseModel implements IWeatherInfoModel {
    public WeatherInfoModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<WeatherInfoResponse> getWeatherInfo(String cityKey) {
        return mService.getWeatherInfo(Constant.APP_KEY, cityKey);
    }
}
