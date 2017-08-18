package com.ppyy.ppweatherplus.model.api;

import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface ApiService {
    /**
     * 登录
     */
    /*@GET("login/{param}")
    Observable<BaseResponse<User>> login(@Path("param") String param,
                                         @Query("password") String password,
                                         @Query("userType") int userType,
                                         @Query("visitIp") String ip);*/

    /**
     * 获取城市天气信息
     */
    @GET("weather")
    Observable<WeatherInfoResponse> getWeatherInfo(@Query("app_key") int appKey, @Query("citykey") int cityKey);
}
