package com.ppyy.ppweatherplus.ui.fragment;

import android.os.Bundle;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.base.BaseFragment;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;

/**
 * Created by NeuroAndroid on 2017/8/22.
 */

public class WeatherInfoFragment extends BaseFragment {
    public static WeatherInfoFragment newInstance(WeatherInfoResponse weatherInfoResponse) {
        WeatherInfoFragment weatherInfoFragment = new WeatherInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", weatherInfoResponse);
        weatherInfoFragment.setArguments(bundle);
        return weatherInfoFragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_weather_info;
    }

    @Override
    protected void initView() {
        WeatherInfoResponse weatherInfoResponse = (WeatherInfoResponse) getArguments().getSerializable("info");
        setToolbarTitle(weatherInfoResponse.getMeta().getCity());
    }
}
