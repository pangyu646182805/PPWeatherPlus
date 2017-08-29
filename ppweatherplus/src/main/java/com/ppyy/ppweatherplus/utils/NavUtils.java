package com.ppyy.ppweatherplus.utils;

import android.content.Context;
import android.content.Intent;

import com.ppyy.ppweatherplus.ui.activity.CityManagerActivity;
import com.ppyy.ppweatherplus.ui.activity.SelectCityActivity;

/**
 * Created by NeuroAndroid on 2017/8/18.
 */

public class NavUtils {
    /**
     * 跳转到选择城市界面
     */
    public static void toSelectCityPage(Context context) {
        Intent intent = new Intent(context, SelectCityActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到城市管理界面
     */
    public static void toCityManagerPage(Context context) {
        Intent intent = new Intent(context, CityManagerActivity.class);
        context.startActivity(intent);
    }
}
