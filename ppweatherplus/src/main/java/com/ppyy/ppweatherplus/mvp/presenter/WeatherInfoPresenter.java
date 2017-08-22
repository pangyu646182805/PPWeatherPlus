package com.ppyy.ppweatherplus.mvp.presenter;

import com.ppyy.ppweatherplus.base.BaseObserver;
import com.ppyy.ppweatherplus.base.BasePresenter;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.manager.CacheManager;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.mvp.contract.IWeatherInfoContract;
import com.ppyy.ppweatherplus.mvp.model.impl.WeatherInfoModelImpl;
import com.ppyy.ppweatherplus.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/8/18.
 */

public class WeatherInfoPresenter extends BasePresenter<WeatherInfoModelImpl, IWeatherInfoContract.View>
        implements IWeatherInfoContract.Presenter {
    public WeatherInfoPresenter(IWeatherInfoContract.View view) {
        super(view);
        mModel = new WeatherInfoModelImpl(Constant.BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getWeatherInfo(String cityKey) {
        WeatherInfoResponse weatherInfo = CacheManager.getWeatherInfo(mView.getContext(), cityKey);
        if (weatherInfo != null) {
            mView.showWeatherInfo(weatherInfo);
            //  如果网络不可用则不去请求网络，直接加载缓存数据
            if (!isNetworkAvailable(mView.getContext())) return;
        }
        getModelFilteredFactory(WeatherInfoResponse.class).compose(mModel.getWeatherInfo(cityKey))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<WeatherInfoResponse>() {
                    @Override
                    protected void onHandleSuccess(WeatherInfoResponse weatherInfoResponse) {
                        mView.showWeatherInfo(weatherInfoResponse);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
