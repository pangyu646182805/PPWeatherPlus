package com.ppyy.ppweatherplus.mvp.contract;

import com.ppyy.ppweatherplus.base.IPresenter;
import com.ppyy.ppweatherplus.base.IView;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;

/**
 * Created by NeuroAndroid on 2017/8/18.
 */

public interface IWeatherInfoContract {
    interface Presenter extends IPresenter {
        /**
         * 获取城市天气信息
         *
         * @param flag true 有缓存就不去请求网络了
         */
        void getWeatherInfo(String cityKey, boolean flag);
    }

    interface View extends IView<Presenter> {
        /**
         * 显示天气信息
         */
        void showWeatherInfo(WeatherInfoResponse weatherInfoResponse);
    }
}
