package com.ppyy.ppweatherplus.utils;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/8/28.
 */

public class LifeIndexUtils {
    public static List<WeatherInfoResponse.IndexesBean> generate(List<WeatherInfoResponse.IndexesBean> indexes,int start, int size) {
        List<WeatherInfoResponse.IndexesBean> dataList = new ArrayList<>();
        for (int i = start; i < size; i++) {
            WeatherInfoResponse.IndexesBean indexesBean = indexes.get(i);
            indexesBean.setIconRes(getIconRes(i));
            dataList.add(indexesBean);
        }
        return dataList;
    }

    private static int getIconRes(int index) {
        int iconRes;
        switch (index) {
            case 0:
            default:
                iconRes = R.drawable.ic_chenlian;
                break;
            case 1:
                iconRes = R.drawable.ic_chuanyi;
                break;
            case 2:
                iconRes = R.drawable.ic_shushidu;
                break;
            case 3:
                iconRes = R.drawable.ic_ganmao;
                break;
            case 4:
                iconRes = R.drawable.ic_ziwaixian;
                break;
            case 5:
                iconRes = R.drawable.ic_lvyou;
                break;
            case 6:
                iconRes = R.drawable.ic_xiche;
                break;
            case 7:
                iconRes = R.drawable.ic_liangshai;
                break;
            case 8:
                iconRes = R.drawable.ic_diaoyu;
                break;
            case 9:
                iconRes = R.drawable.ic_huazhuang;
                break;
            case 10:
                iconRes = R.drawable.ic_yundong;
                break;
            case 11:
                iconRes = R.drawable.ic_yusan;
                break;
            case 12:
                iconRes = R.drawable.ic_yuehui;
                break;
        }
        return iconRes;
    }
}
