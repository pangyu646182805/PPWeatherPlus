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
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.utils.ImageLoader;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.ppyy.ppweatherplus.utils.WeatherIconAndDescUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/8/21.
 */

public class CityListAdapter extends BaseRvAdapter<WeatherInfoResponse> {
    private final boolean mIsDay;

    public CityListAdapter(Context context, List<WeatherInfoResponse> dataList, int layoutId) {
        super(context, dataList, layoutId);
        mIsDay = TimeUtils.judgeDayOrNight();
    }

    @Override
    public void convert(BaseViewHolder holder, WeatherInfoResponse item, int position, int viewType) {
        FrameLayout flWeatherBackground = holder.getView(R.id.fl_weather_background);
        LinearLayout llWeatherBackground = holder.getView(R.id.ll_weather_background);
        ImageView ivWeatherBackground = holder.getView(R.id.iv_weather_background);
        if (item != null) {
            List<WeatherInfoResponse.Forecast15Bean> forecast15 = item.getForecast15();
            if (forecast15 != null && !forecast15.isEmpty()) {
                WeatherInfoResponse.Forecast15Bean forecast15Bean = forecast15.get(1);
                ImageLoader.getInstance().displayImage(mContext,
                        mIsDay ? forecast15Bean.getDay().getSmPic() : forecast15Bean.getNight().getSmPic(), 0, ivWeatherBackground);
                llWeatherBackground.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        llWeatherBackground.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        flWeatherBackground.getLayoutParams().height = llWeatherBackground.getHeight();
                        flWeatherBackground.requestLayout();
                    }
                });
                holder.setText(R.id.tv_temp, forecast15Bean.getHigh() + "/" + forecast15Bean.getLow() + "℃");
            }

            WeatherInfoResponse.MetaBean meta = item.getMeta();
            if (meta != null) {
                holder.setText(R.id.tv_city_name, meta.getCity());
            }

            List<WeatherInfoResponse.HourfcBean> hourfc = item.getHourfc();
            if (hourfc != null && !hourfc.isEmpty()) {
                holder.setText(R.id.tv_weather_desc,
                        WeatherIconAndDescUtils.getWeatherDescByType(hourfc.get(0).getType()));

                ImageView ivWeatherIcon0 = holder.getView(R.id.iv_weather_icon_0);
                ImageView ivWeatherIcon1 = holder.getView(R.id.iv_weather_icon_1);
                ImageLoader.getInstance().displayImage(mContext,
                        WeatherIconAndDescUtils.getWeatherIconResByType(hourfc.get(0).getType(), !mIsDay), ivWeatherIcon0);
                ImageLoader.getInstance().displayImage(mContext,
                        WeatherIconAndDescUtils.getWeatherIconResByType(hourfc.get(0).getType(), !mIsDay), ivWeatherIcon1);
            }

            holder.setText(R.id.tv_weather_desc_0, "雷阵雨")
                    .setText(R.id.tv_weather_desc_1, "多云")
                    .setText(R.id.tv_temp_0, "34/26℃")
                    .setText(R.id.tv_temp_1, "35/27℃");

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
            }
        }
    }
}
