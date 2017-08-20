package com.ppyy.ppweatherplus.mvp.model.impl;

import com.ppyy.ppweatherplus.base.BaseModel;
import com.ppyy.ppweatherplus.model.response.HotCityResponse;
import com.ppyy.ppweatherplus.model.response.SearchCityResponse;
import com.ppyy.ppweatherplus.mvp.model.ISelectCityModel;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/8/19.
 */

public class SelectCityModelImpl extends BaseModel implements ISelectCityModel {
    public SelectCityModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<HotCityResponse> getHotCity() {
        return mService.getHotCity();
    }

    @Override
    public Observable<SearchCityResponse> searchCityByKeyword(String keyword) {
        return mService.searchCityByKeyword(keyword);
    }
}
