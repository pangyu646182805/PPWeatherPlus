package com.ppyy.ppweatherplus.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by NeuroAndroid on 2017/9/8.
 */

public class AppWidgetService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
