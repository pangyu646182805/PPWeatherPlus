package com.ppyy.ppweatherplus.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.CityListAdapter;
import com.ppyy.ppweatherplus.base.BaseActivity;
import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.event.BaseEvent;
import com.ppyy.ppweatherplus.manager.SettingManager;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.mvp.contract.IWeatherInfoContract;
import com.ppyy.ppweatherplus.mvp.presenter.WeatherInfoPresenter;
import com.ppyy.ppweatherplus.permission.DangerousPermissions;
import com.ppyy.ppweatherplus.permission.PermissionsHelper;
import com.ppyy.ppweatherplus.provider.PPCityStore;
import com.ppyy.ppweatherplus.utils.DividerUtils;
import com.ppyy.ppweatherplus.utils.NavUtils;
import com.ppyy.ppweatherplus.utils.ShowUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<IWeatherInfoContract.Presenter> implements IWeatherInfoContract.View {
    private static final String[] PERMISSIONS = new String[]{DangerousPermissions.STORAGE};

    @BindView(R.id.rv_city_list)
    RecyclerView mRvCityList;

    private List<WeatherInfoResponse> mWeatherInfoResponseList = new ArrayList<>();
    private CityListAdapter mCityListAdapter;
    private PermissionsHelper mPermissionsHelper;

    @Override
    protected void initPresenter() {
        mPresenter = new WeatherInfoPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mRvCityList.setLayoutManager(new LinearLayoutManager(this));
        mRvCityList.addItemDecoration(DividerUtils.defaultHorizontalDivider(this));
        mCityListAdapter = new CityListAdapter(this, mWeatherInfoResponseList, R.layout.item_city_list);
        mCityListAdapter.clearRvAnim(mRvCityList);
        mRvCityList.setAdapter(mCityListAdapter);
    }

    @Override
    protected void initData() {
        checkPermission();
        loadCityList();
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
    }

    private void checkPermission() {
        mPermissionsHelper = new PermissionsHelper(this, PERMISSIONS);
        if (mPermissionsHelper.checkAllPermissions(PERMISSIONS)) {
            UIUtils.getHandler().postDelayed(() -> judgeFirstIntoApp(), 200);
            mPermissionsHelper.onDestroy();
        } else {
            // 申请权限
            mPermissionsHelper.startRequestNeedPermissions();
        }
        mPermissionsHelper.setonAllNeedPermissionsGrantedListener(new PermissionsHelper.onAllNeedPermissionsGrantedListener() {
            @Override
            public void onAllNeedPermissionsGranted() {
                ShowUtils.showToast("权限申请成功");
                judgeFirstIntoApp();
            }

            @Override
            public void onPermissionsDenied() {
                ShowUtils.showToast("权限申请失败");
                finish();
            }
        });
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {
        switch (baseEvent.getEventFlag()) {
            case BaseEvent.EVENT_SELECT_CITY:
                loadCityList();
                break;
        }
    }

    /**
     * 加载城市列表
     */
    private void loadCityList() {
        ArrayList<CityBean> allCity = PPCityStore.getInstance(this).getAllCity();
        mWeatherInfoResponseList.clear();
        if (allCity != null && !allCity.isEmpty()) {
            showLoading();
            WeatherInfoResponse weatherInfoResponse;
            for (CityBean cityBean : allCity) {
                weatherInfoResponse = new WeatherInfoResponse();
                WeatherInfoResponse.MetaBean metaBean = new WeatherInfoResponse.MetaBean();
                metaBean.setCitykey(cityBean.getCityId());
                weatherInfoResponse.setMeta(metaBean);
                mWeatherInfoResponseList.add(weatherInfoResponse);
                mPresenter.getWeatherInfo(cityBean.getCityId());
            }
        } else {
            mCityListAdapter.replaceAll(mWeatherInfoResponseList);
        }
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
            showError(() -> NavUtils.toSelectCityPage(this));
        } else {
            hideLoading();
        }
    }

    /**
     * 判断是否是第一次进入app
     * 如果是则进入城市选择界面
     */
    private void judgeFirstIntoApp() {
        if (SettingManager.isFirstIntoApp(this)) {
            NavUtils.toSelectCityPage(this);
        }
    }

    @Override
    public void showWeatherInfo(WeatherInfoResponse weatherInfoResponse) {
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

    @Override
    public void showTip(String tip) {
        hideLoading();
        ShowUtils.showToast(tip);
        getStateLayout().setErrorImgLayoutParams(UIUtils.getDimen(R.dimen.x320), UIUtils.getDimen(R.dimen.y235))
                .setErrorText(UIUtils.getString(R.string.no_network_desc))
                .setImageResource(R.mipmap.ic_wifi_error)
                .setReloadBtnText(UIUtils.getString(R.string.state_reload));
        showError(() -> {
            ShowUtils.showToast("reload");
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionsHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_city:
                NavUtils.toSelectCityPage(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
