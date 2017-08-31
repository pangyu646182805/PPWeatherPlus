package com.ppyy.ppweatherplus.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by NeuroAndroid on 2017/8/31.
 */

public class WeatherServiceRemote {
    @Nullable
    public static WeatherService sWeatherService;
    private static ServiceBinder sBinder;

    public static void bindToService(@NonNull final Activity activity,
                                     final ServiceConnection callback) {
        activity.startService(new Intent(activity, WeatherService.class));

        sBinder = new ServiceBinder(callback);
        activity.bindService(new Intent().setClass(activity, WeatherService.class), sBinder, Context.BIND_AUTO_CREATE);
    }

    public static void unbindFromService(@Nullable final Activity activity) {
        if (activity == null) {
            return;
        }
        if (sBinder == null) {
            return;
        }
        activity.unbindService(sBinder);
        sWeatherService = null;
    }

    public static final class ServiceBinder implements ServiceConnection {
        private final ServiceConnection mCallback;

        public ServiceBinder(final ServiceConnection callback) {
            mCallback = callback;
        }

        @Override
        public void onServiceConnected(final ComponentName className, final IBinder service) {
            WeatherService.WeatherBinder binder = (WeatherService.WeatherBinder) service;
            sWeatherService = binder.getService();
            if (mCallback != null) {
                mCallback.onServiceConnected(className, service);
            }
        }

        @Override
        public void onServiceDisconnected(final ComponentName className) {
            if (mCallback != null) {
                mCallback.onServiceDisconnected(className);
            }
            sWeatherService = null;
        }
    }
}
