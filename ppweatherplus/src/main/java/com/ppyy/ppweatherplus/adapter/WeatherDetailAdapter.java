package com.ppyy.ppweatherplus.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.base.BaseRvAdapter;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.adapter.base.IMultiItemViewType;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.utils.ImageLoader;
import com.ppyy.ppweatherplus.utils.LifeIndexUtils;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.ppyy.ppweatherplus.utils.WeatherIconAndDescUtils;
import com.ppyy.ppweatherplus.widget.AirQualityView;
import com.ppyy.ppweatherplus.widget.AqiWidget;
import com.ppyy.ppweatherplus.widget.SunriseAndSunsetView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/8/24.
 */

public class WeatherDetailAdapter extends BaseRvAdapter<WeatherInfoResponse> {
    public static final int ITEM_HEADER_VIEW_TYPE = 0;
    public static final int ITEM_HOUR_WEATHER_VIEW_TYPE = 1;
    public static final int ITEM_DAY_WEATHER_VIEW_TYPE = 2;
    public static final int ITEM_AIR_QUALITY_VIEW_TYPE = 3;
    public static final int ITEM_LIFE_INDEX_VIEW_TYPE = 4;
    public static final int ITEM_SUNRISE_SUNSET_VIEW_TYPE = 5;

    private int mRlHeaderHeight;
    private int mNormalIndexesSize = 6;
    /**
     * 生活指数是否展开
     */
    private boolean mLifeIndexExpand;

    private List<WeatherInfoResponse.IndexesBean> mExpandIndexes;

    public int getRlHeaderHeight() {
        return mRlHeaderHeight;
    }

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
            List<WeatherInfoResponse.HourfcBean> hourfc = item.getHourfc();
            switch (viewType) {
                case ITEM_HEADER_VIEW_TYPE:
                    if (forecast15 != null && !forecast15.isEmpty()) {
                        RelativeLayout rlHeader = holder.getView(R.id.rl_header);
                        rlHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                rlHeader.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                mRlHeaderHeight = rlHeader.getHeight() - holder.getView(R.id.tv_weather_desc).getBottom();
                            }
                        });

                        AqiWidget aqiWidget0 = holder.getView(R.id.aqi_0);
                        AqiWidget aqiWidget1 = holder.getView(R.id.aqi_1);

                        WeatherInfoResponse.Forecast15Bean forecast15Bean = forecast15.get(1);
                        if (observe != null) {
                            int currentTemp = observe.getTemp();
                            if (currentTemp < forecast15Bean.getLow())
                                currentTemp = forecast15Bean.getLow();
                            if (currentTemp > forecast15Bean.getHigh())
                                currentTemp = forecast15Bean.getHigh();
                            holder.setText(R.id.tv_current_temp, String.valueOf(currentTemp))
                                    .setText(R.id.tv_weather_desc, observe.getWthr())
                                    .setText(R.id.tv_wind, observe.getWd() + observe.getWp())
                                    .setText(R.id.tv_humidity, observe.getShidu());
                        }

                        aqiWidget0.setAqi(forecast15Bean.getAqi());

                        ImageView ivWeatherIcon0 = holder.getView(R.id.iv_weather_icon_0);
                        ImageView ivWeatherIcon1 = holder.getView(R.id.iv_weather_icon_1);

                        ImageLoader.getInstance().displayImage(mContext,
                                WeatherIconAndDescUtils.getWeatherIconResByType(forecast15Bean.getDay().getType(), !isDay), ivWeatherIcon0);
                        holder.setText(R.id.tv_weather_desc_0, WeatherIconAndDescUtils.getWeatherDescByType(forecast15Bean.getDay().getType()))
                                .setText(R.id.tv_temp_0, forecast15Bean.getHigh() + "~" + forecast15Bean.getLow() + "℃");

                        forecast15Bean = forecast15.get(2);

                        aqiWidget1.setAqi(forecast15Bean.getAqi());

                        ImageLoader.getInstance().displayImage(mContext,
                                WeatherIconAndDescUtils.getWeatherIconResByType(forecast15Bean.getDay().getType(), !isDay), ivWeatherIcon1);
                        holder.setText(R.id.tv_weather_desc_1, WeatherIconAndDescUtils.getWeatherDescByType(forecast15Bean.getDay().getType()))
                                .setText(R.id.tv_temp_1, forecast15Bean.getHigh() + "~" + forecast15Bean.getLow() + "℃");
                    }
                    break;
                case ITEM_HOUR_WEATHER_VIEW_TYPE:
                    if (hourfc != null && !hourfc.isEmpty()) {
                        RecyclerView rvHourWeather = holder.getView(R.id.rv_hour_weather);
                        rvHourWeather.setFocusableInTouchMode(false);
                        rvHourWeather.requestFocus();

                        rvHourWeather.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        HourWeatherAdapter hourWeatherAdapter = new HourWeatherAdapter(mContext, null, R.layout.item_hour_weather);
                        rvHourWeather.setAdapter(hourWeatherAdapter);
                        hourWeatherAdapter.setHourWeatherDataList(hourfc);
                    } else {
                        holder.getItemView().getLayoutParams().height = 0;
                        holder.getItemView().requestLayout();
                        holder.getItemView().setVisibility(View.GONE);
                    }
                    break;
                case ITEM_DAY_WEATHER_VIEW_TYPE:
                    if (forecast15 != null && !forecast15.isEmpty()) {
                        RecyclerView rvDayWeather = holder.getView(R.id.rv_day_weather);
                        rvDayWeather.setFocusableInTouchMode(false);
                        rvDayWeather.requestFocus();

                        rvDayWeather.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        DayWeatherAdapter dayWeatherAdapter = new DayWeatherAdapter(mContext, null, R.layout.item_day_weather);
                        rvDayWeather.setAdapter(dayWeatherAdapter);
                        dayWeatherAdapter.setForecast15(forecast15);
                    }
                    break;
                case ITEM_AIR_QUALITY_VIEW_TYPE:
                    WeatherInfoResponse.EvnBean evnBean = item.getEvn();
                    if (evnBean != null) {
                        AirQualityView airQualityView = holder.getView(R.id.air_quality_view);
                        airQualityView.setAqiBean(evnBean);
                        holder.setText(R.id.tv_pm25, String.valueOf(evnBean.getPm25()))
                                .setText(R.id.tv_pm10, String.valueOf(evnBean.getPm10()))
                                .setText(R.id.tv_o3, String.valueOf(evnBean.getO3()))
                                .setText(R.id.tv_so2, String.valueOf(evnBean.getSo2()))
                                .setText(R.id.tv_no2, String.valueOf(evnBean.getNo2()))
                                .setText(R.id.tv_co, String.valueOf(evnBean.getCo()));
                    } else {
                        holder.getItemView().getLayoutParams().height = 0;
                        holder.getItemView().requestLayout();
                        holder.getItemView().setVisibility(View.GONE);
                    }
                    break;
                case ITEM_LIFE_INDEX_VIEW_TYPE:
                    List<WeatherInfoResponse.IndexesBean> indexes = item.getIndexes();
                    if (indexes != null && !indexes.isEmpty()) {
                        if (mExpandIndexes == null) {
                            mExpandIndexes = new ArrayList<>();
                            mExpandIndexes = LifeIndexUtils.generate(indexes, mNormalIndexesSize, indexes.size());
                        }

                        RecyclerView rvLifeIndex = holder.getView(R.id.rv_life_index);
                        rvLifeIndex.setFocusableInTouchMode(false);
                        rvLifeIndex.requestFocus();

                        rvLifeIndex.setLayoutManager(new LinearLayoutManager(mContext));
                        LifeIndexAdapter lifeIndexAdapter = new LifeIndexAdapter(mContext,
                                LifeIndexUtils.generate(indexes, 0, mLifeIndexExpand ?
                                        indexes.size() : mNormalIndexesSize), R.layout.item_life_index);
                        rvLifeIndex.setAdapter(lifeIndexAdapter);

                        holder.setOnClickListener(R.id.tv_expand, view -> {
                            if (mLifeIndexExpand) {
                                // 收起
                                holder.setText(R.id.tv_expand, "展开");
                                lifeIndexAdapter.removeAll(mExpandIndexes);
                            } else {
                                // 展开
                                holder.setText(R.id.tv_expand, "收起");
                                lifeIndexAdapter.addAll(mExpandIndexes);
                            }
                            mLifeIndexExpand = !mLifeIndexExpand;
                        });
                    } else {
                        holder.getItemView().getLayoutParams().height = 0;
                        holder.getItemView().requestLayout();
                        holder.getItemView().setVisibility(View.GONE);
                    }
                    break;
                case ITEM_SUNRISE_SUNSET_VIEW_TYPE:
                    if (forecast15 != null && !forecast15.isEmpty()) {
                        SunriseAndSunsetView sunriseAndSunsetView = holder.getView(R.id.sunrise_and_sunset_view);
                        sunriseAndSunsetView.setForecast15Bean(forecast15.get(1));
                    }
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (getDataList() == null || getDataList().isEmpty())
            return 0;
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemViewType != null) {
            int newPosition = position - getHeaderCounts();
            return mMultiItemViewType.getItemViewType(newPosition, null);
        } else {
            return super.getItemViewType(position);
        }
    }

    public void refreshItem(int position) {
        notifyItemChanged(position);
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
                    case ITEM_HOUR_WEATHER_VIEW_TYPE:
                        return R.layout.item_weather_detail_hour_weather;
                    case ITEM_DAY_WEATHER_VIEW_TYPE:
                        return R.layout.item_weather_detail_day_weather;
                    case ITEM_AIR_QUALITY_VIEW_TYPE:
                        return R.layout.item_weather_detail_air_quality;
                    case ITEM_LIFE_INDEX_VIEW_TYPE:
                        return R.layout.item_weather_detail_life_index;
                    case ITEM_SUNRISE_SUNSET_VIEW_TYPE:
                        return R.layout.item_weather_detail_sunrise_sunset;
                }
            }
        };
    }
}
