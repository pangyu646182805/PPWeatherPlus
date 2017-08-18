package com.ppyy.ppweatherplus.utils;

import android.content.Context;
import android.content.Intent;

import com.ppyy.ppweatherplus.ui.activity.SelectCityActivity;

/**
 * Created by NeuroAndroid on 2017/8/18.
 */

public class NavUtils {
    /**
     * 跳转到
     */
    public static void toSelectCityPage(Context context) {
        Intent intent = new Intent(context, SelectCityActivity.class);
        context.startActivity(intent);
    }
}
