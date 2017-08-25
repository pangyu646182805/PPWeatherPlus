package com.ppyy.ppweatherplus.utils;

/**
 * Created by NeuroAndroid on 2017/8/25.
 */

public class WeatherTimeUtils {
    /**
     * 201708251500->15:00
     */
    public static String getWeatherTime(String time) {
        return TimeUtils.date2String(TimeUtils.string2Date(time, "yyyyMMddHHmm"), "HH:mm");
    }
}
