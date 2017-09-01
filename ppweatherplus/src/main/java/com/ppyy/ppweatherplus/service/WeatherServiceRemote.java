package com.ppyy.ppweatherplus.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by NeuroAndroid on 2017/8/31.
 */

public class WeatherServiceRemote {
    @Nullable
    public static WeatherService sWeatherService;
    private static final Map<Context, ServiceBinder> mConnectionMap = new WeakHashMap<>();

    public static ServiceToken bindToService(@NonNull final Context context,
                                     final ServiceConnection callback) {
        Activity realActivity = ((Activity) context).getParent();
        if (realActivity == null) {
            realActivity = (Activity) context;
        }

        final ContextWrapper contextWrapper = new ContextWrapper(realActivity);
        contextWrapper.startService(new Intent(contextWrapper, WeatherService.class));

        ServiceBinder binder = new ServiceBinder(callback);
        if (context.bindService(new Intent().setClass(context, WeatherService.class), binder, Context.BIND_AUTO_CREATE)) {
            mConnectionMap.put(contextWrapper, binder);
            return new ServiceToken(contextWrapper);
        }
        return null;
    }

    public static void unbindFromService(@Nullable final ServiceToken token) {
        if (token == null) {
            return;
        }
        final ContextWrapper contextWrapper = token.mWrappedContext;
        final ServiceBinder binder = mConnectionMap.remove(contextWrapper);
        if (binder == null) {
            return;
        }
        contextWrapper.unbindService(binder);
        if (mConnectionMap.isEmpty()) {
            sWeatherService = null;
        }
    }

    public static final class ServiceToken {
        public ContextWrapper mWrappedContext;

        public ServiceToken(final ContextWrapper context) {
            mWrappedContext = context;
        }
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
