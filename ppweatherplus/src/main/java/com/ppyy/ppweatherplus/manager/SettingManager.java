package com.ppyy.ppweatherplus.manager;

import android.content.Context;

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
}
