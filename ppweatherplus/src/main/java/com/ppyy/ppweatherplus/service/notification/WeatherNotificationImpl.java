package com.ppyy.ppweatherplus.service.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.service.WeatherService;
import com.ppyy.ppweatherplus.ui.activity.MainActivity;

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
    public void update(WeatherInfoResponse weatherInfo) {
        final RemoteViews notificationLayout = new RemoteViews(mWeatherService.getPackageName(), R.layout.layout_notification);
        final RemoteViews notificationLayoutBig = new RemoteViews(mWeatherService.getPackageName(), R.layout.layout_notification_big);

        Intent action = new Intent(mWeatherService, MainActivity.class);
        action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent openAppPendingIntent = PendingIntent.getActivity(mWeatherService, 0, action, 0);

        final Notification notification = new NotificationCompat.Builder(mWeatherService)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(openAppPendingIntent)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContent(notificationLayout)
                .setCustomBigContentView(notificationLayoutBig)
                .build();

        mWeatherService.startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public void stop() {
        stopped = true;
        mWeatherService.stopForeground(true);
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}
