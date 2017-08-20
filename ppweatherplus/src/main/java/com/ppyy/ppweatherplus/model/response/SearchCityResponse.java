package com.ppyy.ppweatherplus.model.response;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class SearchCityResponse {
    private int status;
    private String desc;
    private List<HotCityResponse.DataBean.HotNationalBean> data;

    public int getStatus() {
        return status;
    }

    public SearchCityResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public SearchCityResponse setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public List<HotCityResponse.DataBean.HotNationalBean> getData() {
        return data;
    }

    public SearchCityResponse setData(List<HotCityResponse.DataBean.HotNationalBean> data) {
        this.data = data;
        return this;
    }
}
