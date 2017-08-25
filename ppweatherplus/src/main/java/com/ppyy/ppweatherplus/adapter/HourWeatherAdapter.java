package com.ppyy.ppweatherplus.adapter;

import android.content.Context;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.base.BaseRvAdapter;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.widget.HourWeatherView;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/8/25.
 */

public class HourWeatherAdapter extends BaseRvAdapter<String> {
    private List<WeatherInfoResponse.HourfcBean> mHourWeatherDataList;

    public void setHourWeatherDataList(List<WeatherInfoResponse.HourfcBean> hourWeatherDataList) {
        mHourWeatherDataList = hourWeatherDataList;
        notifyDataSetChanged();
    }

    public HourWeatherAdapter(Context context, List<String> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (isInHeadViewPos(position) || isInFootViewPos(position)) {
            return;
        }
        convert(holder, null, position, getItemViewType(position));
    }

    @Override
    public void convert(BaseViewHolder holder, String item, int position, int viewType) {
        if (mHourWeatherDataList != null && !mHourWeatherDataList.isEmpty()) {
            HourWeatherView hourWeatherView = holder.getView(R.id.hour_weather_view);
            hourWeatherView.setHourWeatherDataList(mHourWeatherDataList);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
