package com.ppyy.ppweatherplus.adapter;

import android.content.Context;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.base.BaseRvAdapter;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.widget.DayWeatherView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

public class DayWeatherAdapter extends BaseRvAdapter<String> {
    private List<WeatherInfoResponse.Forecast15Bean> mForecast15DataList;

    public void setForecast15(List<WeatherInfoResponse.Forecast15Bean> forecast15) {
        mForecast15DataList = forecast15;
        notifyDataSetChanged();
    }

    public DayWeatherAdapter(Context context, List<String> dataList, int layoutId) {
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
        if (mForecast15DataList != null && !mForecast15DataList.isEmpty()) {
            DayWeatherView dayWeatherView = holder.getView(R.id.day_weather_view);
            if (dayWeatherView.getForecast15DataList() == null) {
            }
            L.e("setForecast15");
            dayWeatherView.setForecast15(mForecast15DataList);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
