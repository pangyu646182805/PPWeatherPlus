package com.ppyy.ppweatherplus.adapter;

import android.content.Context;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.base.BaseRvAdapter;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.model.response.HotCityResponse;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class SearchCityAdapter extends BaseRvAdapter<HotCityResponse.DataBean.HotNationalBean> {
    public SearchCityAdapter(Context context, List<HotCityResponse.DataBean.HotNationalBean> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, HotCityResponse.DataBean.HotNationalBean item, int position, int viewType) {
        holder.setText(R.id.tv, item.getName() + "," + item.getCountry());
    }
}
