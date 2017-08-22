package com.ppyy.ppweatherplus.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.base.BaseRvAdapter;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.ui.activity.MainActivity;
import com.ppyy.ppweatherplus.utils.ImageLoader;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.ppyy.ppweatherplus.utils.WeatherIconAndDescUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/8/21.
 */

public class CityListAdapter extends BaseRvAdapter<WeatherInfoResponse> {
    private final boolean mIsDay;
    private ArrayList<CityBean> mCityBeanList;

    public void setCityBeanList(ArrayList<CityBean> cityBeanList) {
        mCityBeanList = cityBeanList;
    }

    public CityListAdapter(Context context, List<WeatherInfoResponse> dataList, int layoutId) {
        super(context, dataList, layoutId);
        mIsDay = TimeUtils.judgeDayOrNight();
    }

    @Override
    public void convert(BaseViewHolder holder, WeatherInfoResponse item, int position, int viewType) {
        FrameLayout flWeatherBackground = holder.getView(R.id.fl_weather_background);
        LinearLayout llWeatherBackground = holder.getView(R.id.ll_weather_background);
        llWeatherBackground.setOnClickListener(view -> {
            ((MainActivity) mContext).openWeatherInfoFragment(item);
        });
        ImageView ivWeatherBackground = holder.getView(R.id.iv_weather_background);
        llWeatherBackground.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llWeatherBackground.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                flWeatherBackground.getLayoutParams().height = llWeatherBackground.getHeight();
                flWeatherBackground.requestLayout();
            }
        });
        if (item != null) {
            List<WeatherInfoResponse.Forecast15Bean> forecast15 = item.getForecast15();
            if (forecast15 != null && !forecast15.isEmpty()) {
                WeatherInfoResponse.Forecast15Bean forecast15Bean = forecast15.get(1);
                ImageLoader.getInstance().displayImage(mContext,
                        mIsDay ? forecast15Bean.getDay().getSmPic() : forecast15Bean.getNight().getSmPic(), 0, ivWeatherBackground);
                holder.setText(R.id.tv_temp, forecast15Bean.getHigh() + "/" + forecast15Bean.getLow() + "℃");

                ImageView ivWeatherIcon0 = holder.getView(R.id.iv_weather_icon_0);
                ImageView ivWeatherIcon1 = holder.getView(R.id.iv_weather_icon_1);

                holder.setText(R.id.tv_weather_desc,
                        WeatherIconAndDescUtils.getWeatherDescByType(mIsDay ?
                                forecast15Bean.getDay().getType() : forecast15Bean.getNight().getType()));

                forecast15Bean = forecast15.get(2);
                holder.setText(R.id.tv_weather_desc_0, WeatherIconAndDescUtils.getWeatherDescByType(forecast15Bean.getDay().getType()))
                        .setText(R.id.tv_temp_0, forecast15Bean.getHigh() + "/" + forecast15Bean.getLow() + "℃");
                ImageLoader.getInstance().displayImage(mContext,
                        WeatherIconAndDescUtils.getWeatherIconResByType(forecast15Bean.getDay().getType(), !mIsDay), ivWeatherIcon0);

                forecast15Bean = forecast15.get(3);
                holder.setText(R.id.tv_weather_desc_1, WeatherIconAndDescUtils.getWeatherDescByType(forecast15Bean.getDay().getType()))
                        .setText(R.id.tv_temp_1, forecast15Bean.getHigh() + "/" + forecast15Bean.getLow() + "℃");
                ImageLoader.getInstance().displayImage(mContext,
                        WeatherIconAndDescUtils.getWeatherIconResByType(forecast15Bean.getDay().getType(), !mIsDay), ivWeatherIcon1);
            }

            WeatherInfoResponse.MetaBean meta = item.getMeta();
            if (meta != null) {
                holder.setText(R.id.tv_city_name, meta.getCity());
                if (mCityBeanList != null && !mCityBeanList.isEmpty()) {
                    boolean location = mCityBeanList.get(position).getLocation() == 1;
                    holder.setVisibility(R.id.iv_location, location ? View.VISIBLE : View.INVISIBLE);
                }
            }

            List<WeatherInfoResponse.HourfcBean> hourfc = item.getHourfc();
            if (hourfc != null && !hourfc.isEmpty()) {
                WeatherInfoResponse.HourfcBean hourfcBean = hourfc.get(0);
                holder.setText(R.id.tv_current_temp, String.valueOf(hourfcBean.getWthr()));

                ImageView ivWeatherIcon = holder.getView(R.id.iv_weather_icon);
                ImageLoader.getInstance().displayImage(mContext,
                        WeatherIconAndDescUtils.getWeatherIconResByType(hourfcBean.getType(), !mIsDay), ivWeatherIcon);
            }

            List<WeatherInfoResponse.XianhaoBean> xianhao = item.getXianhao();
            if (xianhao != null && !xianhao.isEmpty()) {
                holder.setVisibility(R.id.tv_limit_line, View.VISIBLE);
                WeatherInfoResponse.XianhaoBean xianhaoBean = xianhao.get(0);
                holder.setText(R.id.tv_limit_line, "限行：" + xianhaoBean.getF_number());
            } else {
                holder.setVisibility(R.id.tv_limit_line, View.INVISIBLE);
            }

            WeatherInfoResponse.EvnBean evn = item.getEvn();
            if (evn != null) {
                holder.setText(R.id.tv_aqi, "AQI " + evn.getAqi());
            } else {
                holder.setText(R.id.tv_aqi, "AQI 无");
            }
        }
    }
}
