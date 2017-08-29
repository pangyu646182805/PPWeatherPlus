package com.ppyy.ppweatherplus.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.github.mmin18.widget.RealtimeBlurView;
import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.WeatherPagerAdapter;
import com.ppyy.ppweatherplus.base.BaseFragment;
import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.loader.AsyncCityListLoader;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.ui.activity.MainActivity;
import com.ppyy.ppweatherplus.utils.ImageLoader;
import com.ppyy.ppweatherplus.utils.SystemUtils;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;
import com.ppyy.ppweatherplus.widget.WeatherTitleCustomWidget;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/8/22.
 */

public class WeatherInfoFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<ArrayList<CityBean>> {
    @BindView(R.id.iv_weather_background)
    ImageView mIvWeatherBackground;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    @BindView(R.id.view_weather_title)
    WeatherTitleCustomWidget mWeatherTitleView;
    @BindView(R.id.blur_view)
    RealtimeBlurView mBlurView;

    private WeatherPagerAdapter mWeatherPagerAdapter;

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
        SystemUtils.setTranslateStatusBar(mActivity);
        setDisplayShowTitleEnabled(false);
        WeatherInfoResponse weatherInfoResponse = (WeatherInfoResponse) getArguments().getSerializable("info");
        if (weatherInfoResponse != null) {
        }
    }

    @Override
    protected void initData() {
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void initListener() {
        mVpContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                WeatherDetailFragment weatherDetailFragment = getFragment(position);
                WeatherInfoResponse weatherInfo = weatherDetailFragment.getWeatherInfo();
                if (weatherInfo != null) {
                    setWeatherCustomTitle(weatherInfo);
                    setWeatherBackground(weatherInfo);
                }
                int scrollYDistance = weatherDetailFragment.getScrollYDistance();
                if (scrollYDistance == -1) {
                    setBlurRadius(UIUtils.getDimen(R.dimen.x64), UIUtils.getColor(R.color.black_9));
                } else {
                    weatherDetailFragment.changeSomeState(scrollYDistance);
                }
            }
        });
    }

    private void setUpViewPager(ArrayList<CityBean> data) {
        mWeatherPagerAdapter = new WeatherPagerAdapter(mContext, getChildFragmentManager(), data);
        mVpContent.setAdapter(mWeatherPagerAdapter);
        mVpContent.setOffscreenPageLimit(mWeatherPagerAdapter.getCount() - 1);
    }

    public void setBlurRadius(float blurRadius, int color) {
        if (mBlurView != null) {
            mBlurView.setBlurRadius(blurRadius);
            mBlurView.setOverlayColor(color);
        }
    }

    private void setWeatherCustomTitle(WeatherInfoResponse weatherBean) {
        mWeatherTitleView.setWeatherBean(weatherBean);
    }

    public void expandWeatherCustomTitle() {
        mWeatherTitleView.expand();
    }

    public void shrinkWeatherCustomTitle() {
        mWeatherTitleView.shrink();
    }

    /**
     * 获取天气信息
     */
    public void getWeatherInfo(String cityKey) {
        ((MainActivity) mActivity).getWeatherInfo(cityKey);
    }

    public void showWeatherInfo(WeatherInfoResponse weatherInfoResponse) {
        setWeatherCustomTitle(weatherInfoResponse);
        setWeatherBackground(weatherInfoResponse);
        getFragment(mVpContent.getCurrentItem()).showWeatherInfo(weatherInfoResponse);
    }

    public void showTip(String tip) {
        getFragment(mVpContent.getCurrentItem()).showTip(tip);
    }

    public void setWeatherBackground(WeatherInfoResponse weatherInfoResponse) {
        String url = getImgBackground(weatherInfoResponse);
        ImageLoader.getInstance().displayImage(mContext, url, R.color.transparent, mIvWeatherBackground);
    }

    /**
     * 根据position返回Fragment
     */
    public WeatherDetailFragment getFragment(int position) {
        return (WeatherDetailFragment) mWeatherPagerAdapter.getFragment(position);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_weather_info, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_city_manager:
                ((MainActivity) mActivity).openWeatherCardFragment();
                break;
            case R.id.action_settings:

                break;
            case R.id.action_share:

                break;
            case R.id.action_exit:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<CityBean>> onCreateLoader(int id, Bundle args) {
        return new AsyncCityListLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<CityBean>> loader, ArrayList<CityBean> data) {
        setUpViewPager(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<CityBean>> loader) {

    }

    private String getImgBackground(WeatherInfoResponse weatherInfoResponse) {
        String bgPic;
        boolean isDay = TimeUtils.judgeDayOrNight();
        WeatherInfoResponse.ObserveBean observe = weatherInfoResponse.getObserve();
        if (observe != null && observe.getDay() != null && observe.getNight() != null) {
            if (isDay) {
                bgPic = observe.getDay().getBgPic();
            } else {
                bgPic = observe.getNight().getBgPic();
            }
        } else {
            List<WeatherInfoResponse.Forecast15Bean> forecast15 = weatherInfoResponse.getForecast15();
            if (forecast15 != null && !forecast15.isEmpty()) {
                bgPic = isDay ? forecast15.get(1).getDay().getBgPic() : forecast15.get(1).getDay().getBgPic();
            } else {
                bgPic = "";
            }
        }
        return bgPic;
    }
}
