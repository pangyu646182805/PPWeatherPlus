package com.ppyy.ppweatherplus.alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ppyy.ppweatherplus.service.AppWidgetService;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.TimeUtils;

/**
 * Created by Administrator on 2017/9/16.
 */

public class AlarmTaskReceiver extends BroadcastReceiver {
    private PPAlarmManager mAlarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mAlarmManager == null) {
            mAlarmManager = PPAlarmManager.getInstance();
            mAlarmManager.init(context);
        }
        boolean alarmEffective = intent.getBooleanExtra("alarmEffective", true);
        if (alarmEffective) {
            // 周期性任务
            L.e("AlarmTaskReceiver 周期性任务 AppWidgetEvent");
            Intent requestWeatherInfoSender = new Intent();
            requestWeatherInfoSender.setAction(AppWidgetService.ACTION_REQUEST_WEATHER_INFO);
            context.sendBroadcast(requestWeatherInfoSender);

            // 如果闹钟有效
            L.e("AlarmTaskReceiver : " + TimeUtils.millis2String(System.currentTimeMillis(), "HH:mm:ss"));
            long intervalMillis = intent.getLongExtra("intervalMillis", 0);
            if (intervalMillis != 0) {
                L.e(intervalMillis + " 毫秒之后接受定时任务");
                mAlarmManager.setAppWidgetUpdateCycle(context, intervalMillis, true);
            }
        } else {
            cancelAlarm(context, intent);
        }
    }

    private void cancelAlarm(Context context, Intent intent) {
        L.e("AlarmTaskReceiver里面取消了闹钟");
        PendingIntent pi = PendingIntent.getBroadcast(context, AppWidgetService.APPWIDGET_ALARM_ID,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.getAlarmManager().cancel(pi);
    }
}
