package com.ppyy.ppweatherplus.appwidgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.ppyy.ppweatherplus.utils.WeatherIconAndDescUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/9/4.
 */

public class WeatherVerticalAppWidget extends AppWidgetProvider {
    private static WeatherVerticalAppWidget sInstance;

    public static WeatherVerticalAppWidget getInstance() {
        if (sInstance == null) {
            sInstance = new WeatherVerticalAppWidget();
            return sInstance;
        }
        return sInstance;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        updateTime(context, appWidgetIds);
        updateWeatherInfo(context, appWidgetIds, null);
    }

    /**
     * 更新时间
     */
    public void updateTime(Context context, final int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_weather_vertical_widget);
        String[] timeArr = TimeUtils.millis2String(System.currentTimeMillis(), "HH:mm").split(":");
        remoteViews.setTextViewText(R.id.tv_hour, timeArr[0]);
        remoteViews.setTextViewText(R.id.tv_minute, timeArr[1]);
        pushUpdate(context, appWidgetIds, remoteViews);
    }

    public void updateWeatherInfo(Context context, final int[] appWidgetIds, WeatherInfoResponse weatherInfo) {
        boolean isDay = TimeUtils.judgeDayOrNight();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_weather_vertical_widget);
        if (weatherInfo != null) {
            StringBuilder sb = new StringBuilder();
            WeatherInfoResponse.MetaBean metaBean = weatherInfo.getMeta();
            if (metaBean != null) {
                sb.append(metaBean.getCity()).append(" | ");
            }
            List<WeatherInfoResponse.Forecast15Bean> forecast15 = weatherInfo.getForecast15();
            if (forecast15 != null && !forecast15.isEmpty()) {
                WeatherInfoResponse.Forecast15Bean forecast15Bean = forecast15.get(1);
                int type;
                if (isDay) {
                    type = forecast15Bean.getDay().getType();
                } else {
                    type = forecast15Bean.getNight().getType();
                }
                remoteViews.setImageViewResource(R.id.iv_weather_icon, WeatherIconAndDescUtils.getWeatherIconResByType(type, !isDay));
            }
            WeatherInfoResponse.ObserveBean observeBean = weatherInfo.getObserve();
            if (observeBean != null) {
                sb.append(observeBean.getWthr()).append(" ").append(observeBean.getTemp()).append("°");
            }
            remoteViews.setTextViewText(R.id.tv_weather_desc, sb);
        }
        remoteViews.setTextViewText(R.id.tv_time_desc, TimeUtils.millis2String(System.currentTimeMillis(), "MM/dd EEEE"));

        pushUpdate(context, appWidgetIds, remoteViews);
    }

    private void pushUpdate(final Context context, final int[] appWidgetIds, final RemoteViews views) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if (appWidgetIds != null) {
            appWidgetManager.updateAppWidget(appWidgetIds, views);
        } else {
            appWidgetManager.updateAppWidget(new ComponentName(context, getClass()), views);
        }
    }
}