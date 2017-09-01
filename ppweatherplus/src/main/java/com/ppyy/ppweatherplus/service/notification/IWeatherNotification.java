package com.ppyy.ppweatherplus.service.notification;

import com.ppyy.ppweatherplus.event.WeatherServiceEvent;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.service.WeatherService;

/**
 * Created by NeuroAndroid on 2017/8/31.
 */

public interface IWeatherNotification {
    int NOTIFICATION_ID = 1;

    void init(WeatherService service);

    void update(WeatherInfoResponse weatherInfo, WeatherServiceEvent weatherServiceEvent);

    void stop();
}
