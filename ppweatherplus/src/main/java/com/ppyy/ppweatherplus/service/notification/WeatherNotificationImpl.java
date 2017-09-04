package com.ppyy.ppweatherplus.service.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.event.WeatherServiceEvent;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.service.WeatherService;
import com.ppyy.ppweatherplus.ui.activity.MainActivity;
import com.ppyy.ppweatherplus.ui.activity.SettingActivity;
import com.ppyy.ppweatherplus.utils.ColorUtils;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;
import com.ppyy.ppweatherplus.utils.WeatherIconAndDescUtils;

/**
 * Created by NeuroAndroid on 2017/8/31.
 */

public class WeatherNotificationImpl implements IWeatherNotification {
    private static final int NOTIFY_MODE_FOREGROUND = 1;
    private static final int NOTIFY_MODE_BACKGROUND = 0;

    private int mNotifyMode = NOTIFY_MODE_BACKGROUND;
    private boolean stopped;
    private WeatherService mWeatherService;
    private NotificationManager mNotificationManager;

    @Override
    public void init(WeatherService service) {
        mWeatherService = service;
        mNotificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void update(WeatherInfoResponse weatherInfo, WeatherServiceEvent weatherServiceEvent) {
        final RemoteViews notificationLayout = new RemoteViews(mWeatherService.getPackageName(), R.layout.layout_notification);
        // final RemoteViews notificationLayoutBig = new RemoteViews(mWeatherService.getPackageName(), R.layout.layout_notification_big);
        updateWeatherInfo(notificationLayout, weatherInfo);

        Intent action = new Intent(mWeatherService, MainActivity.class);
        action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent openAppPendingIntent = PendingIntent.getActivity(mWeatherService, 0, action, 0);

        boolean notificationNumberIcon;
        boolean notificationShowIcon;
        boolean notificationShowLockScreen;
        int notificationBackgroundColor;
        int notificationTextColor;
        if (weatherServiceEvent == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mWeatherService);
            notificationNumberIcon = prefs.getBoolean("notification_number_icon", false);
            notificationShowIcon = prefs.getBoolean("notification_show_icon", false);
            notificationShowLockScreen = prefs.getBoolean("notification_show_lock_screen", false);
            int index = Integer.parseInt(prefs.getString("notification_background_color", "0"));
            if (index == 2) {
                notificationBackgroundColor = prefs.getInt(SettingActivity
                        .SettingFragment.CUSTOM_NOTIFICATION_BACKGROUND_COLOR, Color.TRANSPARENT);
            } else {
                notificationBackgroundColor = SettingActivity
                        .SettingFragment.NOTIFICATION_BACKGROUND_COLOR[index];
            }
            index = Integer.parseInt(prefs.getString("notification_text_color", "0"));
            if (index == 2) {
                notificationTextColor = prefs.getInt(SettingActivity
                        .SettingFragment.CUSTOM_NOTIFICATION_TEXT_COLOR, Color.parseColor("#333333"));
            } else {
                notificationTextColor = SettingActivity
                        .SettingFragment.NOTIFICATION_TEXT_COLOR[index];
            }
        } else {
            notificationNumberIcon = weatherServiceEvent.isNotificationNumberIcon();
            notificationShowIcon = weatherServiceEvent.isNotificationShowIcon();
            notificationShowLockScreen = weatherServiceEvent.isNotificationShowLockScreen();
            notificationBackgroundColor = weatherServiceEvent.getNotificationBackgroundColor();
            notificationTextColor = weatherServiceEvent.getNotificationTextColor();
        }
        updateNotificationLayoutBackgroundColor(notificationLayout, notificationBackgroundColor);
        updateNotificationLayoutTextColor(notificationLayout, notificationTextColor);

        // PRIORITY_MAX 和 VISIBILITY_SECRET组合通知栏显示图标但是锁屏不显示通知
        // PRIORITY_MIN 和 VISIBILITY_SECRET组合两个都不显示
        // PRIORITY_MAX 和 VISIBILITY_PUBLIC组合两个都显示
        final Notification notification = new NotificationCompat.Builder(mWeatherService)
                .setSmallIcon(getSmallIconRes(weatherInfo, notificationNumberIcon))
                .setColor(Color.BLACK)
                .setContentIntent(openAppPendingIntent)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setPriority(!notificationShowIcon ?
                        NotificationCompat.PRIORITY_MAX : NotificationCompat.PRIORITY_MIN)
                .setVisibility(!notificationShowLockScreen ?
                        NotificationCompat.VISIBILITY_PUBLIC : NotificationCompat.VISIBILITY_SECRET)
                .setContent(notificationLayout)
                // .setCustomBigContentView(notificationLayoutBig)
                .build();

        mWeatherService.startForeground(NOTIFICATION_ID, notification);
    }

    private int getSmallIconRes(WeatherInfoResponse weatherInfo, boolean notificationNumberIcon) {
        WeatherInfoResponse.ObserveBean observeBean = weatherInfo.getObserve();
        if (observeBean != null) {
            if (notificationNumberIcon) {
                return WeatherIconAndDescUtils.getNumberIconRes(observeBean.getTemp());
            } else {
                return WeatherIconAndDescUtils.getWeatherIconResByType(observeBean.getType(), !TimeUtils.judgeDayOrNight());
            }
        } else {
            return R.mipmap.fifteen_weather_no;
        }
    }

    /**
     * 更新通知栏天气信息
     */
    private void updateWeatherInfo(RemoteViews notificationLayout, WeatherInfoResponse weatherInfo) {
        WeatherInfoResponse.ObserveBean observeBean = weatherInfo.getObserve();
        WeatherInfoResponse.MetaBean metaBean = weatherInfo.getMeta();
        if (metaBean != null) {
            notificationLayout.setTextViewText(R.id.tv_city_name, metaBean.getCity());
            String updateTime = metaBean.getUp_time();
            if (UIUtils.isEmpty(updateTime)) {
                notificationLayout.setViewVisibility(R.id.tv_update_time, View.INVISIBLE);
            } else {
                notificationLayout.setViewVisibility(R.id.tv_update_time, View.VISIBLE);
                notificationLayout.setTextViewText(R.id.tv_update_time, updateTime + "发布");
            }
        }
        if (observeBean != null) {
            notificationLayout.setImageViewResource(R.id.iv_weather_icon,
                    WeatherIconAndDescUtils.getWeatherIconResByType(observeBean.getType(), !TimeUtils.judgeDayOrNight()));
            notificationLayout.setTextViewText(R.id.tv_weather_desc, WeatherIconAndDescUtils.getWeatherDesc(weatherInfo));
        }
    }

    private void updateNotificationLayoutBackgroundColor(RemoteViews notificationLayout, @ColorInt int color) {
        notificationLayout.setInt(R.id.ll_background, "setBackgroundColor", color);
    }

    private void updateNotificationLayoutTextColor(RemoteViews notificationLayout, @ColorInt int color) {
        notificationLayout.setTextColor(R.id.tv_city_name, color);
        notificationLayout.setTextColor(R.id.tv_update_time, ColorUtils.adjustAlpha(color, 0.8f));
        notificationLayout.setTextColor(R.id.tv_weather_desc, ColorUtils.adjustAlpha(color, 0.8f));
    }

    @Override
    public void stop() {
        stopped = true;
        mWeatherService.stopForeground(true);
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}
