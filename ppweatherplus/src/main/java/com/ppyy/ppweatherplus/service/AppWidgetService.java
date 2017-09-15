package com.ppyy.ppweatherplus.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ppyy.ppweatherplus.appwidgets.WeatherHorizontalAppWidget;
import com.ppyy.ppweatherplus.appwidgets.WeatherVerticalAppWidget;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.xdandroid.hellodaemon.AbsWorkService;

/**
 * Created by NeuroAndroid on 2017/9/8.
 */

public class AppWidgetService extends AbsWorkService {
    // 是否任务完成, 不再需要服务运行?
    public static boolean sShouldStopService;
    public static boolean sIsWorkRunning;

    private WeatherHorizontalAppWidget mWeatherHorizontalAppWidget = WeatherHorizontalAppWidget.getInstance();
    private WeatherVerticalAppWidget mWeatherVerticalAppWidget = WeatherVerticalAppWidget.getInstance();

    private WeatherWidgetBroadcastReceiver mWeatherWidgetBroadcastReceiver;
    private WeatherInfoResponse mWeatherInfoResponse;

    @Override
    public void onCreate() {
        super.onCreate();
        mWeatherHorizontalAppWidget.updateTime(this, null);
        mWeatherVerticalAppWidget.updateTime(this, null);
        registerWeatherWidgetBroadcastReceiver();
        registerScreenBroadReceiver();
    }

    /**
     * 注册桌面天气Widget广播
     */
    private void registerWeatherWidgetBroadcastReceiver() {
        L.e("注册了天气桌面插件广播");
        if (mWeatherWidgetBroadcastReceiver == null) {
            mWeatherWidgetBroadcastReceiver = new WeatherWidgetBroadcastReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mWeatherWidgetBroadcastReceiver, filter);
    }

    /**
     * 注册屏幕开启关闭广播
     */
    private void registerScreenBroadReceiver() {
        L.e("注册了锁屏解锁广播");
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenBroadcastReceiver, filter);
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

    public static void stopService() {
        //我们现在不再需要服务运行了, 将标志位置为 true
        sShouldStopService = true;
        sIsWorkRunning = false;
        //取消 Job / Alarm / Subscription
        cancelJobAlarmSub();
    }

    /**
     * 是否 任务完成, 不再需要服务运行?
     *
     * @return 应当停止服务, true; 应当启动服务, false; 无法判断, 什么也不做, null.
     */
    @Override
    public Boolean shouldStopService(Intent intent, int flags, int startId) {
        return sShouldStopService;
    }

    @Override
    public void startWork(Intent intent, int flags, int startId) {
        L.e("AppWidgetService startWork");
    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {
        stopService();
    }

    /**
     * 任务是否正在运行?
     *
     * @return 任务正在运行, true; 任务当前不在运行, false; 无法判断, 什么也不做, null.
     */
    @Override
    public Boolean isWorkRunning(Intent intent, int flags, int startId) {
        return sIsWorkRunning;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent, Void alwaysNull) {
        return null;
    }

    @Override
    public void onServiceKilled(Intent rootIntent) {
        L.e("AppWidgetService onServiceKilled 服务被杀死");
    }

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }*/

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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