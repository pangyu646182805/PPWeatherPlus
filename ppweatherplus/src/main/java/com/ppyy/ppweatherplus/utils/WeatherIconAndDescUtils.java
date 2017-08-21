package com.ppyy.ppweatherplus.utils;

import com.ppyy.ppweatherplus.R;

/**
 * Created by NeuroAndroid on 2017/8/21.
 */
public class WeatherIconAndDescUtils {
    public static int getWeatherIconResByType(int type, boolean isNight) {
        // 默认晴天
        int weatherIconRes = R.mipmap.fifteen_weather_no;
        if (!isNight) {  // 白天天气图标
            switch (type) {
                case 1:  // 晴天
                    weatherIconRes = R.mipmap.fifteen_weather_sunny;
                    break;
                case 2:  // 多云
                    weatherIconRes = R.mipmap.fifteen_weather_mostlycloudy;
                    break;
                case 3:  // 阵雨
                    weatherIconRes = R.mipmap.fifteen_weather_chancerain;
                    break;
                case 4:  // 雷阵雨
                    weatherIconRes = R.mipmap.fifteen_weather_chancestorm;
                    break;
            }
        } else {
            switch (type) {
                case 1:  // 晴天
                    weatherIconRes = R.mipmap.fifteen_weather_sunny_n;
                    break;
                case 2:  // 多云
                    weatherIconRes = R.mipmap.fifteen_weather_mostlycloudy_n;
                    break;
                case 3:  // 阵雨
                    weatherIconRes = R.mipmap.fifteen_weather_chancerain_n;
                    break;
                case 4:  // 雷阵雨
                    weatherIconRes = R.mipmap.fifteen_weather_chancestorm_n;
                    break;
            }
        }
        switch (type) {
            case 8:  // 小雨
            case 9:  // 小到中雨
                weatherIconRes = R.mipmap.fifteen_weather_lightrain;
                break;
            case 10:  // 小到中雨
                weatherIconRes = R.mipmap.fifteen_weather_rain;
                break;
            case 34:  // 阴
                weatherIconRes = R.mipmap.fifteen_weather_cloudy;
                break;
        }
        return weatherIconRes;
    }

    public static String getWeatherDescByType(int type) {
        String weatherDesc = "--";
        switch (type) {
            case 1:
                weatherDesc = "晴";
                break;
            case 2:
                weatherDesc = "多云";
                break;
            case 3:
                weatherDesc = "阵雨";
                break;
            case 4:
                weatherDesc = "雷阵雨";
                break;
            case 8:
                weatherDesc = "小雨";
                break;
            case 9:
                weatherDesc = "小到中雨";
                break;
            case 10:
                weatherDesc = "小到中雨";
                break;
            case 34:
                weatherDesc = "阴";
                break;
        }
        return weatherDesc;
    }
}
