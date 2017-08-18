package com.ppyy.ppweatherplus.base;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.exception.APIException;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.utils.L;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by NeuroAndroid on 2017/6/12.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        if (t instanceof WeatherInfoResponse) {
            WeatherInfoResponse weatherInfoResponse = (WeatherInfoResponse) t;
            if (weatherInfoResponse != null) {
                WeatherInfoResponse.MetaBean meta = weatherInfoResponse.getMeta();
                if (meta != null) {
                    if (meta.getStatus() == Constant.RESPONSE_CODE_OK) {
                        onHandleSuccess(t);
                    } else {
                        onHandleError(meta.getDesc());
                    }
                } else {
                    onHandleError("onNext()->error");
                }
            } else {
                onHandleError("onNext()->error");
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        L.e("error:" + e.toString());
        if (e instanceof APIException) {
            APIException exception = (APIException) e;
            onHandleError(exception.getMessage());
        } else if (e instanceof UnknownHostException) {
            onHandleError("请打开网络");
        } else if (e instanceof SocketTimeoutException) {
            onHandleError("请求超时");
        } else if (e instanceof ConnectException) {
            onHandleError("连接失败");
        } else if (e instanceof HttpException) {
            onHandleError("请求超时");
        } else {
            onHandleError("请求失败");
        }
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
        Log.e("main", "onComplete");
    }

    protected abstract void onHandleSuccess(T t);

    protected abstract void onHandleError(String tip);
}
