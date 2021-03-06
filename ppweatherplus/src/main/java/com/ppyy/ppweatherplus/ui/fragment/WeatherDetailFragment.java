package com.ppyy.ppweatherplus.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.WeatherDetailAdapter;
import com.ppyy.ppweatherplus.base.BaseLazyFragment;
import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.manager.CacheManager;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.ui.activity.ReaderActivity;
import com.ppyy.ppweatherplus.utils.ColorUtils;
import com.ppyy.ppweatherplus.utils.DividerUtils;
import com.ppyy.ppweatherplus.utils.SystemUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
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
    private boolean hasLoad;

    private float mMaxBlurRadius;
    private int mOverlayColor;

    public WeatherInfoResponse getWeatherInfo() {
        if (!mWeatherInfoResponses.isEmpty()) {
            return mWeatherInfoResponses.get(0);
        }
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_weather_detail;
    }

    @Override
    protected void initView() {
        mMaxBlurRadius = mContext.getResources().getDimension(R.dimen.x64);
        mOverlayColor = mContext.getResources().getColor(R.color.black_9);
        mCityBean = (CityBean) getArguments().getSerializable(Constant.CITY);
        WeatherInfoResponse weatherInfo = CacheManager.getWeatherInfo(mContext, mCityBean.getCityId());

        mRefreshLayout.setRefreshHeader(new MaterialHeader(mContext));
        ClassicsFooter classicsFooter = new ClassicsFooter(mContext);
        classicsFooter.setPrimaryColor(Color.TRANSPARENT);
        classicsFooter.setAccentColor(Color.WHITE);
        mRefreshLayout.setRefreshFooter(classicsFooter);
        mRefreshLayout.setEnableHeaderTranslationContent(false);
        mRefreshLayout.setDisableContentWhenRefresh(true);
        mRefreshLayout.setNestedScrollingEnabled(true);
        mRefreshLayout.setEnableAutoLoadmore(false);

        mRvWeatherDetail.setLayoutManager(new LinearLayoutManager(mContext));
        mRvWeatherDetail.addItemDecoration(DividerUtils.generateHorizontalDivider(mContext, R.dimen.y1, R.color.white_3));
        mWeatherDetailAdapter = new WeatherDetailAdapter(mContext, mWeatherInfoResponses, null);
        mRvWeatherDetail.setAdapter(mWeatherDetailAdapter);

        mWeatherInfoResponses.clear();
        mWeatherInfoResponses.add(weatherInfo);
        mWeatherDetailAdapter.replaceAll(mWeatherInfoResponses);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {  // 不可见 -> 可见
            if (!hasLoad) {
                hasLoad = true;
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
        mRefreshLayout.setOnRefreshLoadmoreListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                super.onRefresh(refreshlayout);
                ((WeatherInfoFragment) getParentFragment()).getWeatherInfo(mCityBean.getCityId());
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                super.onLoadmore(refreshlayout);
                refreshlayout.finishLoadmore(0);
                // ShowUtils.showToast("我修复了一个bug");
                Intent intent = new Intent();
                intent.setClass(mContext, ReaderActivity.class);
                if (SystemUtils.greaterLOLLIPOP()) {
                    intent.putExtra(Constant.TRANSITION, "slide");
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
                } else {
                    UIUtils.toLayout(intent);
                }
            }
        });
        mRvWeatherDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                changeSomeState(UIUtils.getScrollYDistance(mRvWeatherDetail));
            }
        });
    }

    public int getScrollYDistance() {
        return UIUtils.getScrollYDistance(mRvWeatherDetail);
    }

    public void refreshLineType() {
        if (mWeatherDetailAdapter != null) {
            mWeatherDetailAdapter.refreshItem(WeatherDetailAdapter.ITEM_HOUR_WEATHER_VIEW_TYPE);
            mWeatherDetailAdapter.refreshItem(WeatherDetailAdapter.ITEM_DAY_WEATHER_VIEW_TYPE);
        }
    }

    public void changeSomeState(int scrolledY) {
        if (scrolledY != -1 && mWeatherDetailAdapter.getRlHeaderHeight() != 0) {
            if (scrolledY >= mWeatherDetailAdapter.getRlHeaderHeight()) {
                // 展开WeatherCustomTitle
                ((WeatherInfoFragment) getParentFragment()).expandWeatherCustomTitle();
            } else {
                // 收缩WeatherCustomTitle
                ((WeatherInfoFragment) getParentFragment()).shrinkWeatherCustomTitle();
            }
            if (scrolledY <= 0) {
                ((WeatherInfoFragment) getParentFragment()).setBlurRadius(0f, Color.TRANSPARENT);
            } else if (scrolledY > 0 && scrolledY <= mWeatherDetailAdapter.getRlHeaderHeight()) {
                float scale = (float) scrolledY / mWeatherDetailAdapter.getRlHeaderHeight();
                ((WeatherInfoFragment) getParentFragment())
                        .setBlurRadius(scale * mMaxBlurRadius, ColorUtils.adjustAlpha(mOverlayColor, scale));
            }
        }
    }

    public void showWeatherInfo(WeatherInfoResponse weatherInfoResponse) {
        finishRefresh();
        mWeatherInfoResponses.clear();
        mWeatherInfoResponses.add(weatherInfoResponse);
        mWeatherDetailAdapter.replaceAll(mWeatherInfoResponses);
    }

    public void showTip(String tip) {
        finishRefresh();
    }
}
