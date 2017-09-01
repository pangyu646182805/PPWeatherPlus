package com.ppyy.ppweatherplus.service.notification;

import com.ppyy.ppweatherplus.event.WeatherServiceEvent;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.service.WeatherService;

/**
 * Created by NeuroAndroid on 2017/8/31.
 */

public class WeatherNotificationImpl24 implements IWeatherNotification {
    private WeatherService mWeatherService;
    private boolean stopped;

    @Override
    public void init(WeatherService service) {
        mWeatherService = service;
    }

    @Override
    public void update(WeatherInfoResponse weatherInfo, WeatherServiceEvent weatherServiceEvent) {

    }

    @Override
    public void stop() {
        stopped = true;
        mWeatherService.stopForeground(true);
    }
}
