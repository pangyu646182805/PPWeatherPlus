package com.ppyy.ppweatherplus.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.ppyy.ppweatherplus.alarm.PPAlarmManager;
import com.ppyy.ppweatherplus.appwidgets.WeatherHorizontalAppWidget;
import com.ppyy.ppweatherplus.appwidgets.WeatherVerticalAppWidget;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.manager.CacheManager;
import com.ppyy.ppweatherplus.model.api.ApiService;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.net.RetrofitUtils;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.xdandroid.hellodaemon.AbsWorkService;
import com.xdandroid.hellodaemon.DaemonEnv;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NeuroAndroid on 2017/9/8.
 */

public class AppWidgetService extends AbsWorkService {
    public static final String ACTION_REQUEST_WEATHER_INFO = "action_request_weather_info";
    public static final String ACTION_APP_WIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    // 是否任务完成, 不再需要服务运行?
    public static boolean sShouldStopService;
    public static boolean sIsWorkRunning;
    public static boolean sUpdateAppWidget = true;
    public static final int APPWIDGET_ALARM_ID = 333;
    private static AppWidgetService sInstance = null;

    public static AppWidgetService getInstance() {
        return sInstance;
    }

    private WeatherHorizontalAppWidget mWeatherHorizontalAppWidget = WeatherHorizontalAppWidget.getInstance();
    private WeatherVerticalAppWidget mWeatherVerticalAppWidget = WeatherVerticalAppWidget.getInstance();

    private Intent mAppWidgetIntent;
    private WeatherWidgetBroadcastReceiver mWeatherWidgetBroadcastReceiver;
    private WeatherInfoResponse mWeatherInfoResponse;
    private SharedPreferences mPrefs;
    private PPAlarmManager mAlarmManager;
    private ApiService mService;
    private boolean mIsScreenOn = true;  // 屏幕是否开启 根据此flag判断锁屏下不需要请求天气网络数据

    @Override
    public void onCreate() {
        super.onCreate();
        L.e("AppWidgetService onCreate");
        init();
    }

    private void init() {
        if (sInstance == null)
            sInstance = this;
        if (mPrefs == null)
            mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (mAlarmManager == null) {
            mAlarmManager = PPAlarmManager.getInstance();
            mAlarmManager.init(this);
        }
        if (mService == null) {
            mService = RetrofitUtils.getInstance(Constant.BASE_URL, false).create(ApiService.class);
        }
        if (mAppWidgetIntent == null) {
            mAppWidgetIntent = new Intent(ACTION_APP_WIDGET_UPDATE);
        }
        registerWeatherWidgetBroadcastReceiver();
        registerScreenBroadReceiver();
        registerRequestWeatherInfoBroadReceiver();
    }

    /**
     * 设置周期性更新AppWidget
     */
    private void setAppWidgetUpdateCycle() {
        boolean cbAutoUpdateNotificationWidget = mPrefs.getBoolean("cb_auto_update_notification_widget", false);
        if (cbAutoUpdateNotificationWidget) {
            String autoUpdateFrequency = mPrefs.getString("auto_update_frequency", "2");
            setAppWidgetUpdateCycle(true, autoUpdateFrequency);
        } else {
            setAppWidgetUpdateCycle(false, "2");
        }
    }

    /**
     * 设置周期性更新AppWidget
     */
    public void setAppWidgetUpdateCycle(boolean cbAutoUpdateNotificationWidget, String autoUpdateFrequency) {
        long intervalMillis;
        switch (Integer.parseInt(autoUpdateFrequency)) {
            case 0:
                intervalMillis = Constant.TEN_MINUTE_MILLIS;
                break;
            case 1:
                intervalMillis = Constant.THIRTY_MINUTE_MILLIS;
                break;
            case 2:
            default:
                intervalMillis = Constant.ONE_HOUR_MILLIS;
                break;
            case 3:
                intervalMillis = Constant.TWO_HOUR_MILLIS;
                break;
            case 4:
                intervalMillis = Constant.FOUR_HOUR_MILLIS;
                break;
            case 5:
                intervalMillis = Constant.SIX_HOUR_MILLIS;
                break;
        }
        L.e("intervalMillis = " + intervalMillis);
        /*intervalMillis = (Integer.parseInt(autoUpdateFrequency) + 1) * 20 * 1000;
        L.e("设置了AlarmManager intervalMillis = " + intervalMillis);*/
        if (cbAutoUpdateNotificationWidget) {
            mAlarmManager.setAppWidgetUpdateCycle(this, intervalMillis, true);
        } else {
            L.e("取消了AlarmManager定时任务");
            mAlarmManager.setAppWidgetUpdateCycle(this, intervalMillis, false);
        }
    }

    private void requestWeatherInfo() {
        L.e("AppWidgetService requestWeatherInfo 请求天气数据");
        // 先去根据本地的缓存更新AppWidget 然后根据网络获取的天气数据更新AppWidget
        mWeatherInfoResponse = CacheManager.getWeatherInfo(this, "101210114");
        updateAppWidget();
        mService.getWeatherInfo(Constant.APP_KEY, "101210114")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherInfoResponse -> {
                    WeatherInfoResponse.MetaBean meta = weatherInfoResponse.getMeta();
                    if (meta != null) {
                        if (meta.getStatus() == Constant.RESPONSE_CODE_OK) {
                            mWeatherInfoResponse = weatherInfoResponse;
                            updateAppWidget();
                        }
                    }
                }, throwable -> {
                });
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

    private void registerRequestWeatherInfoBroadReceiver() {
        L.e("注册了请求天气数据广播");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_REQUEST_WEATHER_INFO);
        registerReceiver(mRequestWeatherInfoReceiver, filter);
    }

    /**
     * 反注册
     */
    private void unregisterWeatherWidgetBroadcastReceiver() {
        if (mWeatherWidgetBroadcastReceiver != null) {
            L.e("反注册了更新AppWidget广播");
            unregisterReceiver(mWeatherWidgetBroadcastReceiver);
            mWeatherWidgetBroadcastReceiver = null;
        }
    }

    public static void startAppWidgetService() {
        AppWidgetService.sShouldStopService = false;
        DaemonEnv.startServiceMayBind(AppWidgetService.class);
    }

    public static void stopService() {
        // 我们现在不再需要服务运行了, 将标志位置为 true
        L.e("stopService");
        sShouldStopService = true;
        sUpdateAppWidget = false;
        sIsWorkRunning = false;
        // 取消 Job / Alarm / Subscription
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
        L.e("<--AppWidgetService startWork-->");
        sIsWorkRunning = true;  // 表明该任务已经运行不需要重复运行
        setAppWidgetUpdateCycle();
    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {
        L.e("<--AppWidgetService stopWork-->");
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

    /**
     * 更新AppWidget
     */
    public void updateAppWidget() {
        if (mWeatherInfoResponse == null) mWeatherInfoResponse = CacheManager.getWeatherInfo(this, "101210114");
        updateAppWidget(mWeatherInfoResponse);
    }

    public void updateAppWidget(WeatherInfoResponse weatherInfoResponse) {
        if (weatherInfoResponse != null) {
            if (mAppWidgetIntent == null) mAppWidgetIntent = new Intent(ACTION_APP_WIDGET_UPDATE);
            sendBroadcast(mAppWidgetIntent);
            mWeatherInfoResponse = weatherInfoResponse;
            mWeatherHorizontalAppWidget.updatePPAppWidget(this, null, weatherInfoResponse);
            mWeatherVerticalAppWidget.updatePPAppWidget(this, null, weatherInfoResponse);
        } else {
            L.e("AppWidgetService updateAppWidget() --> weatherInfoResponse is null");
        }
    }

    private class WeatherWidgetBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (sUpdateAppWidget) {
                L.e("current time : " + TimeUtils.millis2String(System.currentTimeMillis()));
                updateAppWidget();
            } else {
                unregisterWeatherWidgetBroadcastReceiver();
                unregisterReceiver(mScreenBroadcastReceiver);
                stopSelf();
            }
        }
    }

    private final BroadcastReceiver mScreenBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (sUpdateAppWidget) {
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    L.e("屏幕开启");
                    mIsScreenOn = true;
                    updateAppWidget();
                    registerWeatherWidgetBroadcastReceiver();
                } else {
                    L.e("屏幕关闭");
                    mIsScreenOn = false;
                    unregisterWeatherWidgetBroadcastReceiver();
                }
            }
        }
    };

    private final BroadcastReceiver mRequestWeatherInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_REQUEST_WEATHER_INFO.equals(action)) {
                if (mIsScreenOn) {
                    L.e("mRequestWeatherInfoReceiver 周期性任务 请求网络获取天气信息更新AppWidget");
                    requestWeatherInfo();
                } else {
                    L.e("周期性任务来啦 但是手机现在处于锁屏状态 所以不需要请求天气数据");
                }
            }
        }
    };
}
