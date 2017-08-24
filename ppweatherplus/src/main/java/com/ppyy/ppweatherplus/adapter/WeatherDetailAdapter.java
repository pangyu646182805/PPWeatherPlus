package com.ppyy.ppweatherplus.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.base.BaseRvAdapter;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.adapter.base.IMultiItemViewType;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.utils.ImageLoader;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.ppyy.ppweatherplus.utils.WeatherIconAndDescUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/8/24.
 */

public class WeatherDetailAdapter extends BaseRvAdapter<WeatherInfoResponse> {
    private static final int ITEM_HEADER_VIEW_TYPE = 0;

    public WeatherDetailAdapter(Context context, List<WeatherInfoResponse> dataList, IMultiItemViewType<WeatherInfoResponse> multiItemViewType) {
        super(context, dataList, multiItemViewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder, getDataListSize() == 0 ? null : getItem(0), position, getItemViewType(position));
    }

    @Override
    public void convert(BaseViewHolder holder, WeatherInfoResponse item, int position, int viewType) {
        if (item != null) {
            boolean isDay = TimeUtils.judgeDayOrNight();
            WeatherInfoResponse.ObserveBean observe = item.getObserve();
            List<WeatherInfoResponse.Forecast15Bean> forecast15 = item.getForecast15();
            switch (viewType) {
                case ITEM_HEADER_VIEW_TYPE:
                    if (forecast15 != null && !forecast15.isEmpty()) {
                        WeatherInfoResponse.Forecast15Bean forecast15Bean = forecast15.get(1);
                        if (observe != null) {
                            int currentTemp = observe.getTemp();
                            if (currentTemp < forecast15Bean.getLow()) currentTemp = forecast15Bean.getLow();
                            if (currentTemp > forecast15Bean.getHigh()) currentTemp = forecast15Bean.getHigh();
                            holder.setText(R.id.tv_current_temp, String.valueOf(currentTemp));
                        }

                        ImageView ivWeatherIcon0 = holder.getView(R.id.iv_weather_icon_0);
                        ImageView ivWeatherIcon1 = holder.getView(R.id.iv_weather_icon_1);

                        ImageLoader.getInstance().displayImage(mContext,
                                WeatherIconAndDescUtils.getWeatherIconResByType(forecast15Bean.getDay().getType(), !isDay), ivWeatherIcon0);
                        holder.setText(R.id.tv_weather_desc_0, WeatherIconAndDescUtils.getWeatherDescByType(forecast15Bean.getDay().getType()))
                                .setText(R.id.tv_temp_0, forecast15Bean.getHigh() + "/" + forecast15Bean.getLow() + "℃");

                        forecast15Bean = forecast15.get(2);
                        ImageLoader.getInstance().displayImage(mContext,
                                WeatherIconAndDescUtils.getWeatherIconResByType(forecast15Bean.getDay().getType(), !isDay), ivWeatherIcon1);
                        holder.setText(R.id.tv_weather_desc_1, WeatherIconAndDescUtils.getWeatherDescByType(forecast15Bean.getDay().getType()))
                                .setText(R.id.tv_temp_1, forecast15Bean.getHigh() + "/" + forecast15Bean.getLow() + "℃");
                    }
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (getDataList() == null || getDataList().isEmpty()) return 0;
        return 1;
    }

    @Override
    protected IMultiItemViewType<WeatherInfoResponse> provideMultiItemViewType() {
        return new IMultiItemViewType<WeatherInfoResponse>() {
            @Override
            public int getItemViewType(int position, WeatherInfoResponse weatherInfoResponse) {
                return position;
            }

            @Override
            public int getLayoutId(int viewType) {
                switch (viewType) {
                    case ITEM_HEADER_VIEW_TYPE:
                    default:
                        return R.layout.item_weather_detail_header;
                }
            }
        };
    }
}
