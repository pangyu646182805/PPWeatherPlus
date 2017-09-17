package com.ppyy.ppweatherplus.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.TimeUtils;

/**
 * Created by Administrator on 2017/9/16.
 */

public class AlarmTaskReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        L.e("AlarmTaskReceiver : " + TimeUtils.millis2String(System.currentTimeMillis(), "HH:mm"));
        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        if (intervalMillis != 0) {
            L.e(intervalMillis + " 毫秒之后接受定时任务");
            AlarmManagerUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis, intent);
        }
    }
}
