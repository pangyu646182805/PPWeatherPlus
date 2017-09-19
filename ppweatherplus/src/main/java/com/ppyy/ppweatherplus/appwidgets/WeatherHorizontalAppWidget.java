package com.ppyy.ppweatherplus.appwidgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.service.AppWidgetService;
import com.ppyy.ppweatherplus.ui.activity.MainActivity;
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
    public static boolean sAppWidgetAvailable;  // AppWidget是否可用

    public static WeatherHorizontalAppWidget getInstance() {
        if (sInstance == null) {
            synchronized (WeatherHorizontalAppWidget.class) {
                if (sInstance == null) {
                    sInstance = new WeatherHorizontalAppWidget();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        L.e("WeatherHorizontalAppWidget onUpdate");
        updatePPAppWidget(context, appWidgetIds, null);
        Intent intent = new Intent();
        intent.setAction(AppWidgetService.ACTION_REQUEST_WEATHER_INFO);
        context.sendBroadcast(intent);
    }

    /**
     * 更新AppWidget
     *
     * @param context     上下文
     * @param weatherInfo 天气数据
     */
    public void updatePPAppWidget(Context context, final int[] appWidgetIds, WeatherInfoResponse weatherInfo) {
        if (sAppWidgetAvailable) {
            // 如果AppWidget可用才去更新
            boolean isDay = TimeUtils.judgeDayOrNight();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_weather_horizontal_widget);
            linkAppWidget(context, remoteViews);
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
        } else {
            L.e("WeatherHorizontalAppWidget 不可用");
        }
    }

    private void pushUpdate(final Context context, final int[] appWidgetIds, final RemoteViews views) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if (appWidgetIds != null) {
            appWidgetManager.updateAppWidget(appWidgetIds, views);
        } else {
            appWidgetManager.updateAppWidget(new ComponentName(context, getClass()), views);
        }
    }

    /**
     * 点击事件
     */
    private void linkAppWidget(final Context context, final RemoteViews views) {
        Intent action;
        PendingIntent pendingIntent;

        action = new Intent(context, MainActivity.class);
        action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(context, 0, action, 0);
        views.setOnClickPendingIntent(R.id.rl_root, pendingIntent);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        sAppWidgetAvailable = true;
        L.e("WeatherHorizontalAppWidget onEnable");
        AppWidgetService.sShouldStopService = false;
        AppWidgetService.sUpdateAppWidget = true;
        DaemonEnv.startServiceMayBind(AppWidgetService.class);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        sAppWidgetAvailable = false;
        L.e("WeatherHorizontalAppWidget onDisabled");
        AppWidgetService.stopService();
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        L.e("WeatherHorizontalAppWidget onDeleted");
    }
}
