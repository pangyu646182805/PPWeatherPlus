package com.ppyy.ppweatherplus.adapter;

import android.content.Context;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.adapter.base.SelectAdapter;
import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.utils.UIUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/8/29.
 */

public class CityManagerAdapter extends SelectAdapter<CityBean> {
    public CityManagerAdapter(Context context, List<CityBean> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, CityBean item, int position, int viewType) {
        String cityZh = item.getCityName();
        int max = item.getMax();
        int min = item.getMin();
        String weatherDesc = item.getWeatherDesc();
        holder.setText(R.id.tv_title, cityZh)
                .setText(R.id.tv_first_word, cityZh.substring(0, 1))
                .setText(R.id.tv_temp, (max == -1 || min == -1) ? "N/A" : max + "~" + min + Constant.TEMP)
                .setText(R.id.tv_sub_title, UIUtils.isEmpty(weatherDesc) ? "N/A" : weatherDesc);
    }
}
