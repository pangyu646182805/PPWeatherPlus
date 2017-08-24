package com.ppyy.ppweatherplus.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.base.BaseFragment;
import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.loader.AsyncCityListLoader;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.widget.WeatherTitleCustomWidget;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/8/22.
 */

public class WeatherInfoFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<ArrayList<CityBean>> {
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    @BindView(R.id.view_weather_title)
    WeatherTitleCustomWidget mWeatherTitleView;

    public static WeatherInfoFragment newInstance(WeatherInfoResponse weatherInfoResponse) {
        WeatherInfoFragment weatherInfoFragment = new WeatherInfoFragment();
        if (weatherInfoResponse != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", weatherInfoResponse);
            weatherInfoFragment.setArguments(bundle);
        }
        return weatherInfoFragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_weather_info;
    }

    @Override
    protected void initView() {
        setDisplayShowTitleEnabled(false);
        WeatherInfoResponse weatherInfoResponse = (WeatherInfoResponse) getArguments().getSerializable("info");
        if (weatherInfoResponse != null) {
        }
    }

    @Override
    public Loader<ArrayList<CityBean>> onCreateLoader(int id, Bundle args) {
        return new AsyncCityListLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<CityBean>> loader, ArrayList<CityBean> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<CityBean>> loader) {

    }
}
