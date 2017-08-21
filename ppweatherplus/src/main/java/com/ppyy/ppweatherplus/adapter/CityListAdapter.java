package com.ppyy.ppweatherplus.adapter;

import android.content.Context;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.base.BaseRvAdapter;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.utils.ImageLoader;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/8/21.
 */

public class CityListAdapter extends BaseRvAdapter<WeatherInfoResponse> {
    public CityListAdapter(Context context, List<WeatherInfoResponse> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, WeatherInfoResponse item, int position, int viewType) {
        FrameLayout flWeatherBackground = holder.getView(R.id.fl_weather_background);
        LinearLayout llWeatherBackground = holder.getView(R.id.ll_weather_background);
        ImageView ivWeatherBackground = holder.getView(R.id.iv_weather_background);
        if (item != null) {
            List<WeatherInfoResponse.Forecast15Bean> forecast15 = item.getForecast15();
            if (forecast15 != null && !forecast15.isEmpty()) {
                ImageLoader.getInstance().displayImage(mContext, forecast15.get(1).getDay().getSmPic(), 0, ivWeatherBackground);
                llWeatherBackground.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        llWeatherBackground.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        flWeatherBackground.getLayoutParams().height = llWeatherBackground.getHeight();
                        flWeatherBackground.requestLayout();
                    }
                });
            }

            WeatherInfoResponse.MetaBean meta = item.getMeta();
            if (meta != null) {
                holder.setText(R.id.tv_city_name, meta.getCity());
            }
        }
    }
}
