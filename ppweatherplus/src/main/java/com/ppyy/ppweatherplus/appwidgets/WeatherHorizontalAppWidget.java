package com.ppyy.ppweatherplus.appwidgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.service.AppWidgetService;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;
import com.ppyy.ppweatherplus.utils.WeatherIconAndDescUtils;
import com.xdandroid.hellodaemon.DaemonEnv;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/9/4.
 * 三星天气横向排版的天气Widget
 */
public class WeatherHorizontalAppWidget extends AppWidgetProvider {
    private static WeatherHorizontalAppWidget sInstance;

    public static WeatherHorizontalAppWidget getInstance() {
        if (sInstance == null) {
            sInstance = new WeatherHorizontalAppWidget();
            return sInstance;
        }
        return sInstance;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        updatePPAppWidget(context, appWidgetIds, null);
    }

    /**
     * 更新AppWidget
     *
     * @param context     上下文
     * @param weatherInfo 天气数据
     */
    public void updatePPAppWidget(Context context, final int[] appWidgetIds, WeatherInfoResponse weatherInfo) {
        boolean isDay = TimeUtils.judgeDayOrNight();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_weather_horizontal_widget);
        remoteViews.setTextViewText(R.id.tv_time, TimeUtils.millis2String(System.currentTimeMillis(), "HH:mm"));
        StringBuilder metaDesc = new StringBuilder();
        metaDesc.append(TimeUtils.millis2String(System.currentTimeMillis(), "M月d日 E"));
        if (weatherInfo != null) {
            WeatherInfoResponse.MetaBean metaBean = weatherInfo.getMeta();
            if (metaBean != null) {
                metaDesc.append(" ").append(metaBean.getCity());
                String updateTime = metaBean.getUp_time();
                if (!UIUtils.isEmpty(updateTime)) {
                    remoteViews.setTextViewText(R.id.tv_update_time, "已更新：" + updateTime);
                }
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
            if (weatherInfo.getObserve() != null)
                remoteViews.setTextViewText(R.id.tv_weather_desc, WeatherIconAndDescUtils.getWeatherDesc(weatherInfo));
        }
        remoteViews.setTextViewText(R.id.tv_meta_desc, metaDesc);

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

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        L.e("WeatherHorizontalAppWidget onEnable");
        AppWidgetService.sShouldStopService = false;
        AppWidgetService.sIsWorkRunning = true;
        AppWidgetService.sUpdateAppWidget = true;
        DaemonEnv.startServiceMayBind(AppWidgetService.class);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        L.e("WeatherHorizontalAppWidget onDisabled");
        AppWidgetService.stopService();
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        L.e("WeatherHorizontalAppWidget onDeleted");
    }
}
