package com.ppyy.ppweatherplus.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.ppyy.colorful.Colorful;
import com.ppyy.ppweatherplus.utils.L;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class BaseApplication extends Application {
    private static Context sContext;
    private static Handler sHandler;

    public static Handler getHandler() {
        return sHandler;
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 全局设置是否需要Log打印
         */
        L.setGlobalToggle(true);
        sContext = getApplicationContext();
        sHandler = new Handler();

        Colorful.defaults()
                .primaryColor(Colorful.ThemeColor.WHITE)
                .accentColor(Colorful.ThemeColor.NIGHT)
                .translucent(true)
                .night(false);
        Colorful.init(this);
    }
}
