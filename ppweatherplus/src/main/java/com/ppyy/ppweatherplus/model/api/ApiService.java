package com.ppyy.ppweatherplus.model.api;

import com.ppyy.ppweatherplus.model.response.HotCityResponse;
import com.ppyy.ppweatherplus.model.response.ReaderResponse;
import com.ppyy.ppweatherplus.model.response.SearchCityResponse;
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
    @GET("v2/weather")
    Observable<WeatherInfoResponse> getWeatherInfo(@Query("app_key") int appKey, @Query("citykey") String cityKey);


    @GET("city?lon=&app_ts=1502957577098&app_key=99817749&device_id=29c82fbe10331817eb2ba134d575d541&city_key=101210101&ver_name=6.9.6&uid=&keyword=&ver_code=716&channel=own&auth_token=eyJhY2N0ayI6IiIsInVwIjoiQU5EUk9JRCIsImRldmljZSI6IlNNLUc5NTUwMzUyNTYyMDc3MjY0MzM0In0%3D&lat=&type=hotV2&devid=a47cc0669be48a6b49aba18d1c42e200&os_version=70")
    Observable<HotCityResponse> getHotCity();

    @GET("city?lon=&app_ts=1502957830998&app_key=99817749&foreign=true&device_id=29c82fbe10331817eb2ba134d575d541&city_key=101210101&ver_name=6.9.6&ver_code=716&uid=&channel=own&auth_token=eyJhY2N0ayI6IiIsInVwIjoiQU5EUk9JRCIsImRldmljZSI6IlNNLUc5NTUwMzUyNTYyMDc3MjY0MzM0In0%3D&lat=&type=search&devid=a47cc0669be48a6b49aba18d1c42e200&os_version=70")
    Observable<SearchCityResponse> searchCityByKeyword(@Query("keyword") String keyword);

    @GET("index.php?r=find/GetArticleList&art_id=&page_size=20")
    Observable<ReaderResponse> getReaderJsonData(@Query("cate_id") int categoryId);
}
