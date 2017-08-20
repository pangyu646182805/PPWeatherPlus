package com.ppyy.ppweatherplus.mvp.contract;

import com.ppyy.ppweatherplus.base.IPresenter;
import com.ppyy.ppweatherplus.base.IView;
import com.ppyy.ppweatherplus.model.response.HotCityResponse;
import com.ppyy.ppweatherplus.model.response.SearchCityResponse;

/**
 * Created by NeuroAndroid on 2017/8/18.
 */

public interface ISelectCityContract {
    interface Presenter extends IPresenter {
        /**
         * 获取热门城市列表
         */
        void getHotCity();

        /**
         * 通过关键字搜索城市
         */
        void searchCityByKeyword(String keyword);
    }

    interface View extends IView<Presenter> {
        /**
         * 显示天气信息
         */
        void showHotCity(HotCityResponse hotCityResponse);

        /**
         * 显示搜索结果
         */
        void showSearchResponse(SearchCityResponse searchCityResponse);
    }
}
