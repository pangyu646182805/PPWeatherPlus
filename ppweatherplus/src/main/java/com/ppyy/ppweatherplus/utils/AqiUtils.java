package com.ppyy.ppweatherplus.utils;

import com.ppyy.ppweatherplus.R;

/**
 * Created by NeuroAndroid on 2017/8/24.
 */

public class AqiUtils {
    /**
     * 六个级别相对应的颜色
     */
    private static final int[] AIR_QUALITY_LEVEL = {R.color.air_quality_level_1, R.color.air_quality_level_2, R.color.air_quality_level_3,
            R.color.air_quality_level_4, R.color.air_quality_level_5, R.color.air_quality_level_6};
    private static int sAirQualityLevelAndColor = 0;

    public static String getAqiText(int aqi) {
        String aqiDesc;
        if (aqi >= 0 && aqi <= 50) {
            aqiDesc = "优质";
            sAirQualityLevelAndColor = 0;
        } else if (aqi > 50 && aqi <= 100) {
            aqiDesc = "良好";
            sAirQualityLevelAndColor = 1;
        } else if (aqi > 100 && aqi <= 150) {
            aqiDesc = "轻度";
            sAirQualityLevelAndColor = 2;
        } else if (aqi > 150 && aqi <= 200) {
            aqiDesc = "中度";
            sAirQualityLevelAndColor = 3;
        } else if (aqi > 200 && aqi <= 300) {
            aqiDesc = "重度";
            sAirQualityLevelAndColor = 4;
        } else {
            aqiDesc = "严重";
            sAirQualityLevelAndColor = 5;
        }
        return aqiDesc;
    }

    public static int getAqiColor() {
        return AIR_QUALITY_LEVEL[sAirQualityLevelAndColor];
    }
}
