package com.ppyy.ppweatherplus.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.base.BaseActivity;
import com.ppyy.ppweatherplus.base.BaseFragment;
import com.ppyy.ppweatherplus.event.BaseEvent;
import com.ppyy.ppweatherplus.event.StatusBarColoringEvent;
import com.ppyy.ppweatherplus.event.WeatherServiceEvent;
import com.ppyy.ppweatherplus.manager.CacheManager;
import com.ppyy.ppweatherplus.manager.SettingManager;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.mvp.contract.IWeatherInfoContract;
import com.ppyy.ppweatherplus.mvp.presenter.WeatherInfoPresenter;
import com.ppyy.ppweatherplus.permission.DangerousPermissions;
import com.ppyy.ppweatherplus.permission.PermissionsHelper;
import com.ppyy.ppweatherplus.provider.PPCityStore;
import com.ppyy.ppweatherplus.service.WeatherServiceRemote;
import com.ppyy.ppweatherplus.ui.fragment.WeatherCardFragment;
import com.ppyy.ppweatherplus.ui.fragment.WeatherInfoFragment;
import com.ppyy.ppweatherplus.utils.FragmentUtils;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.NavUtils;
import com.ppyy.ppweatherplus.utils.ShowUtils;
import com.ppyy.ppweatherplus.utils.SystemUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity<IWeatherInfoContract.Presenter> implements IWeatherInfoContract.View {
    private static final String[] PERMISSIONS = new String[]{DangerousPermissions.STORAGE, DangerousPermissions.PHONE};

    private PermissionsHelper mPermissionsHelper;
    private BaseFragment mCurrentFragment;
    private WeatherServiceRemote.ServiceToken mServiceToken;

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

        mServiceToken = WeatherServiceRemote.bindToService(this, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                L.e("天气服务开启");
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                L.e("天气服务关闭");
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
                ((WeatherCardFragment) mCurrentFragment).loadData();
                break;
            case BaseEvent.EVENT_STATUS_BAR_COLORING:
                StatusBarColoringEvent statusBarColoringEvent = (StatusBarColoringEvent) baseEvent;
                boolean statusBarColoring = statusBarColoringEvent.isStatusBarColoring();
                if (isWeatherCardFragment()) {
                    SystemUtils.setStatusBarDarkMode(this, statusBarColoring);
                } else {
                    SystemUtils.setTranslateStatusBar(this, statusBarColoring);
                }
                break;
            case BaseEvent.EVENT_LINE_TYPE:
                if (!isWeatherCardFragment()) {
                    getWeatherInfoFragment().refreshLineType();
                }
                break;
            case BaseEvent.EVENT_WEATHER_SERVICE:
                WeatherServiceEvent weatherServiceEvent = (WeatherServiceEvent) baseEvent;
                if (weatherServiceEvent.isOpenNotificationOperate()) {
                    if (WeatherServiceRemote.sWeatherService != null) {
                        if (weatherServiceEvent.isShowNotification()) {
                            L.e("打开天气通知栏");
                            WeatherServiceRemote.sWeatherService.resumeWeatherNotification();
                        } else {
                            L.e("取消天气通知栏");
                            WeatherServiceRemote.sWeatherService.cancelWeatherNotification();
                        }
                    }
                } else {
                    updateNotification(null, weatherServiceEvent);
                }
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
        CacheManager.saveWeatherInfo(this, weatherInfoResponse);
        updateDataBase(weatherInfoResponse);
        if (isWeatherCardFragment()) {
            getWeatherCardFragment().showWeatherInfo(weatherInfoResponse);
        } else {
            getWeatherInfoFragment().showWeatherInfo(weatherInfoResponse);
        }
        updateNotification(weatherInfoResponse, null);
    }

    /**
     * 更新通知栏天气信息
     */
    private void updateNotification(WeatherInfoResponse weatherInfoResponse, WeatherServiceEvent weatherServiceEvent) {
        if (WeatherServiceRemote.sWeatherService != null) {
            WeatherServiceRemote.sWeatherService.setWeatherInfoResponse(weatherInfoResponse);
            WeatherServiceRemote.sWeatherService.updateWeatherInfoAndAppWidget(weatherServiceEvent);
            WeatherServiceRemote.sWeatherService.updateAppWidget();
        }
    }

    private void updateDataBase(WeatherInfoResponse weatherInfoResponse) {
        WeatherInfoResponse.MetaBean metaBean = weatherInfoResponse.getMeta();
        WeatherInfoResponse.Forecast15Bean forecast15Bean = weatherInfoResponse.getForecast15().get(1);
        PPCityStore.getInstance(this).update(metaBean.getCitykey(), metaBean.getCity(), metaBean.getUpper(),
                forecast15Bean.getHigh(), forecast15Bean.getLow(), weatherInfoResponse.getObserve().getWthr());
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

    @Override
    protected void onDestroy() {
        WeatherServiceRemote.unbindFromService(mServiceToken);
        super.onDestroy();
    }
}
