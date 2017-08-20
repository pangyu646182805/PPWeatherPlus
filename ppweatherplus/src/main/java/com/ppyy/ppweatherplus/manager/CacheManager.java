package com.ppyy.ppweatherplus.manager;

import android.content.Context;

import com.google.gson.Gson;
import com.ppyy.ppweatherplus.model.response.HotCityResponse;
import com.ppyy.ppweatherplus.utils.ACache;
import com.ppyy.ppweatherplus.utils.UIUtils;

/**
 * Created by Administrator on 2017/8/19.
 */

public class CacheManager {
    private static final String HOT_CITY_LIST = "hot_city_list";
    private static final Gson GSON = new Gson();

    /**
     * 保存热门城市列表
     */
    public static void saveHotCityList(Context context, HotCityResponse hotCityResponse) {
        ACache.get(context).put(HOT_CITY_LIST, GSON.toJson(hotCityResponse), ACache.TIME_DAY);
    }

    /**
     * 获取热门城市列表
     */
    public static HotCityResponse getHotCityList(Context context) {
        String json = ACache.get(context).getAsString(HOT_CITY_LIST);
        if (UIUtils.isEmpty(json)) {
            return null;
        } else {
            return GSON.fromJson(json, HotCityResponse.class);
        }
    }
}
