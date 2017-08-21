package com.ppyy.ppweatherplus.mvp.model;

import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IWeatherInfoModel {
    Observable<WeatherInfoResponse> getWeatherInfo(String cityKey);
}
