package com.ppyy.ppweatherplus.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.event.WeatherServiceEvent;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.provider.PPCityStore;
import com.ppyy.ppweatherplus.service.notification.IWeatherNotification;
import com.ppyy.ppweatherplus.service.notification.WeatherNotificationImpl;

/**
 * Created by NeuroAndroid on 2017/8/31.
 */

public class WeatherService extends Service {
    private IWeatherNotification mWeatherNotification;
    private WeatherBinder mWeatherBinder;
    private WeatherInfoResponse mWeatherInfoResponse;

    private boolean isServiceBind;
    private SharedPreferences mPrefs;

    public void setWeatherInfoResponse(WeatherInfoResponse weatherInfoResponse) {
        if (weatherInfoResponse == null) {
            if (mWeatherInfoResponse != null) {
                weatherInfoResponse = mWeatherInfoResponse;
            }
        }
        WeatherInfoResponse.MetaBean metaBean = weatherInfoResponse.getMeta();
        if (metaBean != null) {
            CityBean cityBean = PPCityStore.getInstance(this).findCityBean(metaBean.getCitykey());
            if (cityBean.getLocation() == 1) {
                // 是定位城市才去更新通知栏天气信息
                mWeatherInfoResponse = weatherInfoResponse;
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mWeatherNotification = new WeatherNotificationImpl24();
        } else {
        }*/
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean showNotification = mPrefs.getBoolean("cb_notification_show", true);
        if (showNotification) {
            mWeatherNotification = new WeatherNotificationImpl();
            mWeatherNotification.init(this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    /**
     * 更新通知栏天气信息
     */
    public void updateWeatherInfoAndAppWidget(WeatherServiceEvent weatherServiceEvent) {
        if (mWeatherNotification != null) {
            if (mWeatherInfoResponse != null) {
                WeatherInfoResponse.MetaBean metaBean = mWeatherInfoResponse.getMeta();
                if (metaBean != null) {
                    CityBean cityBean = PPCityStore.getInstance(this).findCityBean(metaBean.getCitykey());
                    if (cityBean.getLocation() == 1)  // 是定位城市才去更新通知栏天气信息
                        mWeatherNotification.update(mWeatherInfoResponse, weatherServiceEvent);
                }
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        isServiceBind = true;
        mWeatherBinder = new WeatherBinder();
        return mWeatherBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        isServiceBind = true;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isServiceBind = false;
        boolean showNotification = mPrefs.getBoolean("cb_notification_show", true);
        if (!showNotification) {
            // 解除绑定的时候如果不显示天气通知栏则停止自身
            stopSelf();
        }
        return true;
    }

    /**
     * 恢复天气通知
     */
    public void resumeWeatherNotification() {
        if (mWeatherNotification == null) {
            mWeatherNotification = new WeatherNotificationImpl();
            mWeatherNotification.init(this);
            updateWeatherInfoAndAppWidget(null);
        }
    }

    /**
     * 取消天气通知
     */
    public void cancelWeatherNotification() {
        if (mWeatherNotification != null) {
            mWeatherNotification.stop();
            mWeatherNotification = null;
            if (!isServiceBind) {
                // 如果服务没有绑定就停止自身
                stopSelf();
            }
        }
    }

    public class WeatherBinder extends Binder {
        @NonNull
        public WeatherService getService() {
            return WeatherService.this;
        }
    }
}
