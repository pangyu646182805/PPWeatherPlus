package com.ppyy.ppweatherplus.ui.fragment;

import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.CityListAdapter;
import com.ppyy.ppweatherplus.base.BaseFragment;
import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.interfaces.NetworkCallback;
import com.ppyy.ppweatherplus.manager.CacheManager;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.provider.PPCityStore;
import com.ppyy.ppweatherplus.receiver.NetworkReceiver;
import com.ppyy.ppweatherplus.ui.activity.MainActivity;
import com.ppyy.ppweatherplus.utils.DividerUtils;
import com.ppyy.ppweatherplus.utils.NavUtils;
import com.ppyy.ppweatherplus.utils.NetworkUtils;
import com.ppyy.ppweatherplus.utils.ShowUtils;
import com.ppyy.ppweatherplus.utils.SystemUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/8/8.
 * 天气卡片Fragment
 */
public class WeatherCardFragment extends BaseFragment implements NetworkCallback {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_city_list)
    RecyclerView mRvCityList;

    private List<WeatherInfoResponse> mWeatherInfoResponseList = new ArrayList<>();
    private CityListAdapter mCityListAdapter;
    private NetworkReceiver mNetworkReceiver;
    private ArrayList<CityBean> mAllCity;

    public static WeatherCardFragment newInstance() {
        WeatherCardFragment weatherCardFragment = new WeatherCardFragment();
        return weatherCardFragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_weather_card;
    }

    @Override
    protected void initView() {
        SystemUtils.setStatusBarDarkMode(mActivity);
        setToolbarTitle(R.string.city_list);
        mRefreshLayout.setRefreshHeader(new MaterialHeader(mContext));
        mRefreshLayout.setEnableHeaderTranslationContent(false);
        mRefreshLayout.setDisableContentWhenRefresh(true);
        mRefreshLayout.setNestedScrollingEnabled(true);
        mRefreshLayout.setEnableLoadmore(false);

        mRvCityList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvCityList.addItemDecoration(DividerUtils.generateHorizontalDivider(mContext, R.dimen.y8, R.color.split));
        mCityListAdapter = new CityListAdapter(mContext, mWeatherInfoResponseList, R.layout.item_city_list);
        mCityListAdapter.clearRvAnim(mRvCityList);
        mRvCityList.setAdapter(mCityListAdapter);
    }

    @Override
    protected void initData() {
        new LoadCacheTask().execute();
        mRefreshLayout.autoRefresh();
    }

    @Override
    protected void initListener() {
        mCityListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkIsEmpty();
            }
        });
        mRefreshLayout.setOnRefreshListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                super.onRefresh(refreshlayout);
                loadCityList();
            }
        });
    }

    public void loadData() {
        getStateLayout().hide();
        loadCityList();
    }

    /**
     * 加载城市列表
     */
    private void loadCityList() {
        new LoadCityTask().execute();
        // requestWeatherInfo(PPCityStore.getInstance(mContext).getAllCity());
    }

    public void showWeatherInfo(WeatherInfoResponse weatherInfoResponse) {
        CacheManager.saveWeatherInfo(mContext, weatherInfoResponse);
        hideLoading();
        handleWeatherInfoResponse(weatherInfoResponse);
    }

    private void handleWeatherInfoResponse(WeatherInfoResponse weatherInfoResponse) {
        WeatherInfoResponse.MetaBean meta = weatherInfoResponse.getMeta();
        int refreshIndex = 0;
        for (int i = 0; i < mWeatherInfoResponseList.size(); i++) {
            if (mWeatherInfoResponseList.get(i).getMeta().getCitykey().equals(meta.getCitykey())) {
                refreshIndex = i;
                break;
            }
        }
        mCityListAdapter.set(refreshIndex, weatherInfoResponse);
    }

    public void showTip(String tip) {
        registerNetworkReceiver();
        hideLoading();
        ShowUtils.showToast(tip);
        mCityListAdapter.clear();
        getStateLayout().setErrorImgLayoutParams(UIUtils.getDimen(R.dimen.x320), UIUtils.getDimen(R.dimen.y235))
                .setErrorText(UIUtils.getString(R.string.no_network_desc))
                .setImageResource(R.mipmap.ic_wifi_error)
                .setReloadBtnText(UIUtils.getString(R.string.state_reload));
        showError(() -> {
            if (NetworkUtils.isAvailable(mContext)) {
                loadCityList();
            } else {
                ShowUtils.showToast("请检查网络");
            }
        });
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mRefreshLayout != null) mRefreshLayout.finishRefresh();
    }

    /**
     * 检查城市列表是否为空
     */
    private void checkIsEmpty() {
        if (mCityListAdapter.getItemCount() == 0) {
            getStateLayout().setErrorImgLayoutParams(UIUtils.getDimen(R.dimen.x250), UIUtils.getDimen(R.dimen.y167))
                    .setErrorText(UIUtils.getString(R.string.to_add_desc))
                    .setImageResource(R.mipmap.empty)
                    .setReloadBtnText(UIUtils.getString(R.string.to_add));
            showError(() -> NavUtils.toSelectCityPage(mContext));
        } else {
            hideLoading();
        }
    }

    private void registerNetworkReceiver() {
        if (mNetworkReceiver == null) {
            mNetworkReceiver = new NetworkReceiver(this);
            IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
            mContext.registerReceiver(mNetworkReceiver, filter);
        }
    }

    private void unregisterNetworkReceiver() {
        if (mNetworkReceiver != null) {
            mContext.unregisterReceiver(mNetworkReceiver);
            mNetworkReceiver = null;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_city:
                NavUtils.toSelectCityPage(mContext);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNetworkChange(boolean isAvailable) {
        if (isAvailable) {
            unregisterNetworkReceiver();
            loadCityList();
        } else {
            ShowUtils.showToast("当前网络不可用");
        }
    }

    /**
     * 请求天气信息
     */
    private void requestWeatherInfo(ArrayList<CityBean> allCity) {
        mWeatherInfoResponseList.clear();
        if (allCity != null && !allCity.isEmpty()) {
            mCityListAdapter.setCityBeanList(allCity);
            WeatherInfoResponse weatherInfoResponse;
            for (CityBean cityBean : allCity) {
                weatherInfoResponse = new WeatherInfoResponse();
                WeatherInfoResponse.MetaBean metaBean = new WeatherInfoResponse.MetaBean();
                metaBean.setCitykey(cityBean.getCityId());
                weatherInfoResponse.setMeta(metaBean);
                mWeatherInfoResponseList.add(weatherInfoResponse);
                ((MainActivity) mActivity).getWeatherInfo(cityBean.getCityId());
            }
        } else {
            mCityListAdapter.replaceAll(mWeatherInfoResponseList);
        }
    }

    private class LoadCityTask extends AsyncTask<Void, Void, ArrayList<CityBean>> {
        @Override
        protected ArrayList<CityBean> doInBackground(Void... voids) {
            return PPCityStore.getInstance(mContext).getAllCity();
        }

        @Override
        protected void onPostExecute(ArrayList<CityBean> allCity) {
            super.onPostExecute(allCity);
            requestWeatherInfo(allCity);
        }
    }

    private class LoadCacheTask extends AsyncTask<Void, Void, ArrayList<CityBean>> {
        @Override
        protected ArrayList<CityBean> doInBackground(Void... voids) {
            return PPCityStore.getInstance(mContext).getAllCity();
        }

        @Override
        protected void onPostExecute(ArrayList<CityBean> allCity) {
            super.onPostExecute(allCity);
            mWeatherInfoResponseList.clear();
            if (allCity != null && !allCity.isEmpty()) {
                mCityListAdapter.setCityBeanList(allCity);
                for (CityBean cityBean : allCity) {
                    WeatherInfoResponse weatherInfo = CacheManager.getWeatherInfo(mContext, cityBean.getCityId());
                    mWeatherInfoResponseList.add(weatherInfo);
                }
            }
            mCityListAdapter.replaceAll(mWeatherInfoResponseList);
        }
    }
}
