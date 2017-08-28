package com.ppyy.ppweatherplus.adapter;

import android.content.Context;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.base.BaseRvAdapter;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/8/28.
 */

public class LifeIndexAdapter extends BaseRvAdapter<WeatherInfoResponse.IndexesBean> {
    public LifeIndexAdapter(Context context, List<WeatherInfoResponse.IndexesBean> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, WeatherInfoResponse.IndexesBean item, int position, int viewType) {
        holder.setImageResource(R.id.iv_life_index, item.getIconRes())
                .setText(R.id.tv_life_index_desc, item.getName())
                .setText(R.id.tv_life_index_txt, item.getDesc());
    }
}
