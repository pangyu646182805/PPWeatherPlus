package com.ppyy.ppweatherplus.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ppyy.ppweatherplus.appwidgets.WeatherHorizontalAppWidget;
import com.ppyy.ppweatherplus.appwidgets.WeatherVerticalAppWidget;
import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.event.WeatherServiceEvent;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.provider.PPCityStore;
import com.ppyy.ppweatherplus.service.notification.IWeatherNotification;
import com.ppyy.ppweatherplus.service.notification.WeatherNotificationImpl;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.TimeUtils;

/**
 * Created by NeuroAndroid on 2017/8/31.
 */

public class WeatherService extends Service {
    private IWeatherNotification mWeatherNotification;
    private WeatherBinder mWeatherBinder;
    private WeatherInfoResponse mWeatherInfoResponse;

    private boolean isServiceBind;
    private SharedPreferences mPrefs;

    private WeatherHorizontalAppWidget mWeatherHorizontalAppWidget = WeatherHorizontalAppWidget.getInstance();
    private WeatherVerticalAppWidget mWeatherVerticalAppWidget = WeatherVerticalAppWidget.getInstance();

    private WeatherWidgetBroadcastReceiver mWeatherWidgetBroadcastReceiver;

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

        registerWeatherWidgetBroadcastReceiver();
        registerScreenBroadReceiver();
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

    /**
     * 更新AppWidget
     */
    public void updateAppWidget() {
        if (mWeatherInfoResponse != null) {
            mWeatherHorizontalAppWidget.updateWeatherInfo(this, null, mWeatherInfoResponse);
            mWeatherVerticalAppWidget.updateWeatherInfo(this, null, mWeatherInfoResponse);
        }
    }

    /**
     * 注册桌面天气Widget广播
     */
    private void registerWeatherWidgetBroadcastReceiver() {
        if (mWeatherWidgetBroadcastReceiver == null) {
            mWeatherWidgetBroadcastReceiver = new WeatherWidgetBroadcastReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mWeatherWidgetBroadcastReceiver, filter);
    }

    /**
     * 反注册
     */
    private void unregisterWeatherWidgetBroadcastReceiver() {
        if (mWeatherWidgetBroadcastReceiver != null) {
            unregisterReceiver(mWeatherWidgetBroadcastReceiver);
            mWeatherWidgetBroadcastReceiver = null;
        }
    }

    /**
     * 注册屏幕开启关闭广播
     */
    private void registerScreenBroadReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenBroadcastReceiver, filter);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterWeatherWidgetBroadcastReceiver();
        unregisterReceiver(mScreenBroadcastReceiver);
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

    private class WeatherWidgetBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            L.e("current time : " + TimeUtils.millis2String(System.currentTimeMillis()));
            mWeatherHorizontalAppWidget.updateTime(context, null);
            mWeatherVerticalAppWidget.updateTime(context, null);
        }
    }

    private final BroadcastReceiver mScreenBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                mWeatherHorizontalAppWidget.updateTime(context, null);
                registerWeatherWidgetBroadcastReceiver();
                L.e("屏幕开启");
            } else {
                unregisterWeatherWidgetBroadcastReceiver();
                L.e("屏幕关闭");
            }
        }
    };
}
