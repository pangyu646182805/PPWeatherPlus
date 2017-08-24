package com.ppyy.ppweatherplus.loader;

import android.content.Context;

import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.provider.PPCityStore;

import java.util.ArrayList;

/**
 * Created by NeuroAndroid on 2017/8/23.
 */

public class CityListLoader {
    public static ArrayList<CityBean> loadCityList(Context context) {
        return PPCityStore.getInstance(context).getAllCity();
    }
}
