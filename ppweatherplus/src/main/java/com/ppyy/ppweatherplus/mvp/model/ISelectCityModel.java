package com.ppyy.ppweatherplus.mvp.model;

import com.ppyy.ppweatherplus.model.response.HotCityResponse;
import com.ppyy.ppweatherplus.model.response.SearchCityResponse;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface ISelectCityModel {
    Observable<HotCityResponse> getHotCity();

    Observable<SearchCityResponse> searchCityByKeyword(String keyword);
}
