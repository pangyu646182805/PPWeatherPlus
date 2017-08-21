package com.ppyy.ppweatherplus.bean;

/**
 * Created by NeuroAndroid on 2017/8/21.
 */

public class CityBean {
    private String cityId;
    private String cityName;
    private String upper;  // 上级城市
    private int max;
    private int min;
    private String weatherDesc;

    public CityBean() {
    }

    public CityBean(String cityId, String cityName, String upper, int max, int min, String weatherDesc) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.upper = upper;
        this.max = max;
        this.min = min;
        this.weatherDesc = weatherDesc;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }
}
