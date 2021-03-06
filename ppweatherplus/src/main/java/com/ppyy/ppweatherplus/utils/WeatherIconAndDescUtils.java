package com.ppyy.ppweatherplus.utils;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;

import java.util.List;

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
            case 10:  // 中雨
            case 11:  // 大雨
            case 13:  // 大暴雨
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
                weatherDesc = "中雨";
                break;
            case 11:
                weatherDesc = "大雨";
                break;
            case 13:
                weatherDesc = "大暴雨";
                break;
            case 34:
                weatherDesc = "阴";
                break;
        }
        return weatherDesc;
    }

    public static int getNumberIconRes(int temp) {
        if (temp >= 0) {
            return UIUtils.getResources().getIdentifier("notif_temp_" + temp, "mipmap", UIUtils.getPackageName());
        } else {
            temp = Math.abs(temp);
            return UIUtils.getResources().getIdentifier("notif_temp_neg_" + temp, "mipmap", UIUtils.getPackageName());
        }
    }

    /**
     * 获取天气描述
     * 多云 34° 优质
     */
    public static String getWeatherDesc(WeatherInfoResponse weatherInfo) {
        StringBuilder sb = new StringBuilder();
        WeatherInfoResponse.ObserveBean observeBean = weatherInfo.getObserve();
        sb.append(observeBean.getWthr()).append(" ").append(observeBean.getTemp()).append("°");

        List<WeatherInfoResponse.Forecast15Bean> forecast15 = weatherInfo.getForecast15();
        if (forecast15 != null && !forecast15.isEmpty()) {
            WeatherInfoResponse.Forecast15Bean forecast15Bean = forecast15.get(1);
            sb.append(" ").append(AqiUtils.getAqiText(forecast15Bean.getAqi()));
        }
        return sb.toString();
    }
}
