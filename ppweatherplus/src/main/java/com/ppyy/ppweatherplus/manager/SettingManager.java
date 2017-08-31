package com.ppyy.ppweatherplus.manager;

import android.content.Context;
import android.preference.PreferenceManager;

import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.utils.SPUtils;

/**
 * Created by NeuroAndroid on 2017/8/18.
 */

public class SettingManager {
    /**
     * 判断是否是第一次进入app
     */
    public static boolean isFirstIntoApp(Context context) {
        return SPUtils.getBoolean(context, Constant.IS_FIRST_INTO_APP, true);
    }

    public static void firstIntoApp(Context context) {
        setFirstIntoApp(context, false);
    }

    public static void setFirstIntoApp(Context context, boolean firstIntoApp) {
        SPUtils.putBoolean(context, Constant.IS_FIRST_INTO_APP, firstIntoApp);
    }

    /**
     * 获取天气趋势线的类型
     * 0 : 折线
     * 1 : 曲线
     */
    public static String getWeatherLineType(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("custom_weather_line_type", "0");
    }
}
