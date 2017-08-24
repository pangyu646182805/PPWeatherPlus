package com.ppyy.ppweatherplus.mvp.presenter;

import com.ppyy.ppweatherplus.base.BaseObserver;
import com.ppyy.ppweatherplus.base.BasePresenter;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.manager.CacheManager;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.mvp.contract.IWeatherInfoContract;
import com.ppyy.ppweatherplus.mvp.model.impl.WeatherInfoModelImpl;
import com.ppyy.ppweatherplus.utils.RxUtils;
import com.ppyy.ppweatherplus.utils.ShowUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    public void getWeatherInfo(String cityKey, boolean flag) {
        Observable.create((ObservableOnSubscribe<WeatherInfoResponse>) emitter -> {
            WeatherInfoResponse weatherInfo = CacheManager.getWeatherInfo(mView.getContext(), cityKey);
            if (weatherInfo == null) weatherInfo = new WeatherInfoResponse();
            emitter.onNext(weatherInfo);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherInfoResponse -> {
                    if (weatherInfoResponse != null && weatherInfoResponse.getMeta() != null) {
                        mView.showWeatherInfo(weatherInfoResponse);
                        if (flag) return;
                        if (!isNetworkAvailable(mView.getContext())) return;
                    }
                    ShowUtils.showToast("我请求网络啦");
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
                }, throwable -> {});
        /*WeatherInfoResponse weatherInfo = CacheManager.getWeatherInfo(mView.getContext(), cityKey);
        if (weatherInfo != null) {
            mView.showWeatherInfo(weatherInfo);
            //  如果网络不可用则不去请求网络，直接加载缓存数据
            if (!isNetworkAvailable(mView.getContext())) return;
        }*/
        //  如果网络不可用则不去请求网络，直接加载缓存数据
    }
}
