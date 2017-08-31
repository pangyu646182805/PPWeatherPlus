package com.ppyy.ppweatherplus.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ppyy.ppweatherplus.service.notification.IWeatherNotification;
import com.ppyy.ppweatherplus.service.notification.WeatherNotificationImpl;

/**
 * Created by NeuroAndroid on 2017/8/31.
 */

public class WeatherService extends Service {
    private IWeatherNotification mWeatherNotification;
    private WeatherBinder mWeatherBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        mWeatherNotification = new WeatherNotificationImpl();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mWeatherNotification = new WeatherNotificationImpl24();
        } else {
        }*/
        mWeatherNotification.init(this);
        mWeatherNotification.update(null);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mWeatherBinder = new WeatherBinder();
        return mWeatherBinder;
    }

    public class WeatherBinder extends Binder {
        @NonNull
        public WeatherService getService() {
            return WeatherService.this;
        }
    }
}
