package com.ppyy.ppweatherplus.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.base.BaseActivity;
import com.ppyy.ppweatherplus.base.BaseFragment;
import com.ppyy.ppweatherplus.event.BaseEvent;
import com.ppyy.ppweatherplus.manager.SettingManager;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.mvp.contract.IWeatherInfoContract;
import com.ppyy.ppweatherplus.mvp.presenter.WeatherInfoPresenter;
import com.ppyy.ppweatherplus.permission.DangerousPermissions;
import com.ppyy.ppweatherplus.permission.PermissionsHelper;
import com.ppyy.ppweatherplus.provider.PPCityStore;
import com.ppyy.ppweatherplus.ui.fragment.WeatherCardFragment;
import com.ppyy.ppweatherplus.ui.fragment.WeatherInfoFragment;
import com.ppyy.ppweatherplus.utils.FragmentUtils;
import com.ppyy.ppweatherplus.utils.NavUtils;
import com.ppyy.ppweatherplus.utils.ShowUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity<IWeatherInfoContract.Presenter> implements IWeatherInfoContract.View {
    private static final String[] PERMISSIONS = new String[]{DangerousPermissions.STORAGE, DangerousPermissions.PHONE};

    private PermissionsHelper mPermissionsHelper;
    private BaseFragment mCurrentFragment;

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
        if (PPCityStore.getInstance(this).isEmptyCityList()) {
            openWeatherCardFragment();
        } else {
            openWeatherInfoFragment(null);
        }
        checkPermission();
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
                ((WeatherCardFragment) mCurrentFragment).loadData();
                break;
        }
    }

    public void openWeatherCardFragment() {
        mCurrentFragment = WeatherCardFragment.newInstance();
        FragmentUtils.replaceFragment(getSupportFragmentManager(), mCurrentFragment, R.id.fl_container, false);
    }

    public void openWeatherInfoFragment(WeatherInfoResponse weatherInfoResponse) {
        mCurrentFragment = WeatherInfoFragment.newInstance(weatherInfoResponse);
        FragmentUtils.replaceFragment(getSupportFragmentManager(), mCurrentFragment, R.id.fl_container, false);
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

    public void getWeatherInfo(String cityKey) {
        mPresenter.getWeatherInfo(cityKey);
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
    public void showWeatherInfo(WeatherInfoResponse weatherInfoResponse) {
        if (isWeatherCardFragment()) {
            getWeatherCardFragment().showWeatherInfo(weatherInfoResponse);
        } else {
            getWeatherInfoFragment().showWeatherInfo(weatherInfoResponse);
        }
    }

    @Override
    public void showTip(String tip) {
        if (isWeatherCardFragment()) {
            getWeatherCardFragment().showTip(tip);
        } else {
            getWeatherInfoFragment().showTip(tip);
        }
    }

    private boolean isWeatherCardFragment() {
        return mCurrentFragment != null && mCurrentFragment instanceof WeatherCardFragment;
    }

    private WeatherCardFragment getWeatherCardFragment() {
        return (WeatherCardFragment) mCurrentFragment;
    }

    private WeatherInfoFragment getWeatherInfoFragment() {
        return (WeatherInfoFragment) mCurrentFragment;
    }
}
