package com.ppyy.ppweatherplus.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.WeatherDetailAdapter;
import com.ppyy.ppweatherplus.base.BaseLazyFragment;
import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.manager.CacheManager;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.utils.DividerUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/8/24.
 */

public class WeatherDetailFragment extends BaseLazyFragment {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_weather_detail)
    RecyclerView mRvWeatherDetail;

    private CityBean mCityBean;
    private WeatherDetailAdapter mWeatherDetailAdapter;
    private List<WeatherInfoResponse> mWeatherInfoResponses = new ArrayList<>();

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_weather_detail;
    }

    @Override
    protected void initView() {
        mRefreshLayout.setRefreshHeader(new MaterialHeader(mContext));
        mRefreshLayout.setEnableHeaderTranslationContent(false);
        mRefreshLayout.setDisableContentWhenRefresh(true);
        mRefreshLayout.setNestedScrollingEnabled(true);
        mRefreshLayout.setEnableLoadmore(false);

        mRvWeatherDetail.setLayoutManager(new LinearLayoutManager(mContext));
        mRvWeatherDetail.addItemDecoration(DividerUtils.generateHorizontalDivider(mContext, R.dimen.y1, R.color.white_3));
        mWeatherDetailAdapter = new WeatherDetailAdapter(mContext, mWeatherInfoResponses, null);
        mRvWeatherDetail.setAdapter(mWeatherDetailAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (mCityBean == null) {
            mCityBean = (CityBean) getArguments().getSerializable(Constant.CITY);
            WeatherInfoResponse weatherInfo = CacheManager.getWeatherInfo(mContext, mCityBean.getCityId());
        }
        if (isVisible) {  // 不可见 -> 可见
            if (mWeatherDetailAdapter.getDataList().isEmpty()) {
                startRefresh();
            }
        } else {
            finishRefresh();
        }
    }

    private void startRefresh() {
        if (mRefreshLayout != null && !mRefreshLayout.isRefreshing())
            mRefreshLayout.autoRefresh();
    }

    private void finishRefresh() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing())
            mRefreshLayout.finishRefresh();
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                super.onRefresh(refreshlayout);
                ((WeatherInfoFragment) getParentFragment()).getWeatherInfo(mCityBean.getCityId());
            }
        });
    }

    public void showWeatherInfo(WeatherInfoResponse weatherInfoResponse) {
        finishRefresh();
        mWeatherInfoResponses.clear();
        mWeatherInfoResponses.add(weatherInfoResponse);
        mWeatherDetailAdapter.replaceAll(mWeatherInfoResponses);
    }

    public void showTip(String tip) {

    }
}
