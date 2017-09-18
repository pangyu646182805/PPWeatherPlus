package com.ppyy.ppweatherplus.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.ppyy.ppweatherplus.service.AppWidgetService;
import com.ppyy.ppweatherplus.utils.L;

/**
 * Created by NeuroAndroid on 2017/9/18.
 */

public class PPAlarmManager {
    public static final String ALARM_ACTION = "com.ppyy.ppweatherplus.alarm.clock";
    private static PPAlarmManager sInstance;

    private Context mContext;
    private AlarmManager mAlarmManager;

    public AlarmManager getAlarmManager() {
        return mAlarmManager;
    }

    public static PPAlarmManager getInstance() {
        if (sInstance == null) {
            synchronized (PPAlarmManager.class) {
                if (sInstance == null) {
                    sInstance = new PPAlarmManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        mContext = context;
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * 设置appWidget更新更新周期
     */
    public void setAppWidgetUpdateCycle(Context context, long intervalMillis, boolean alarmEffective) {
        Intent intent = new Intent(ALARM_ACTION);
        intent.setClass(context, AlarmTaskReceiver.class);
        intent.putExtra("intervalMillis", intervalMillis);
        intent.putExtra("id", AppWidgetService.APPWIDGET_ALARM_ID);
        L.e("PPAlarmManager alarmEffective : " + alarmEffective);
        if (alarmEffective && AppWidgetService.sUpdateAppWidget) {
            intent.putExtra("alarmEffective", true);
            // 如果闹钟有效
            PendingIntent sender = PendingIntent.getBroadcast(context, AppWidgetService.APPWIDGET_ALARM_ID,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            long triggerAtMillis = System.currentTimeMillis() + intervalMillis;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, sender);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, sender);
            } else {
                mAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, sender);
            }
        } else {
            intent.putExtra("alarmEffective", false);
            context.sendBroadcast(intent);
        }
    }
}
