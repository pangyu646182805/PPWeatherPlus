package com.ppyy.ppweatherplus.utils;

/**
 * Created by NeuroAndroid on 2017/8/24.
 */

public class AqiUtils {
    public static String getAqiText(int aqi) {
        String aqiDesc;
        if (aqi >= 0 && aqi <= 50) {
            aqiDesc = "优质";
        } else if (aqi > 50 && aqi <= 100) {
            aqiDesc = "良好";
        } else if (aqi > 100 && aqi <= 150) {
            aqiDesc = "轻度";
        } else if (aqi > 150 && aqi <= 200) {
            aqiDesc = "中度";
        } else if (aqi > 200 && aqi <= 300) {
            aqiDesc = "重度";
        } else {
            aqiDesc = "严重";
        }
        return aqiDesc;
    }
}
