package com.ppyy.ppweatherplus.loader;

import android.content.Context;

import com.ppyy.ppweatherplus.bean.CityBean;

import java.util.ArrayList;

/**
 * Created by NeuroAndroid on 2017/8/23.
 */

public class AsyncCityListLoader extends WrappedAsyncTaskLoader<ArrayList<CityBean>> {
    public AsyncCityListLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<CityBean> loadInBackground() {
        return CityListLoader.loadCityList(getContext());
    }
}
