package com.ppyy.ppweatherplus.event;

/**
 * Created by NeuroAndroid on 2017/9/1.
 */

public class WeatherServiceEvent extends BaseEvent {
    private boolean openNotificationOperate;  // 是否是打开通知栏操作
    private boolean showNotification;
    private boolean notificationNumberIcon;
    private boolean notificationShowIcon;
    private boolean notificationShowLockScreen;
    private int notificationBackgroundColor;
    private int notificationTextColor;

    public WeatherServiceEvent() {
        setEventFlag(EVENT_WEATHER_SERVICE);
    }

    public boolean isOpenNotificationOperate() {
        return openNotificationOperate;
    }

    public WeatherServiceEvent setOpenNotificationOperate(boolean openNotificationOperate) {
        this.openNotificationOperate = openNotificationOperate;
        return this;
    }

    public boolean isShowNotification() {
        return showNotification;
    }

    public WeatherServiceEvent setShowNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    public boolean isNotificationNumberIcon() {
        return notificationNumberIcon;
    }

    public WeatherServiceEvent setNotificationNumberIcon(boolean notificationNumberIcon) {
        this.notificationNumberIcon = notificationNumberIcon;
        return this;
    }

    public boolean isNotificationShowIcon() {
        return notificationShowIcon;
    }

    public WeatherServiceEvent setNotificationShowIcon(boolean notificationShowIcon) {
        this.notificationShowIcon = notificationShowIcon;
        return this;
    }

    public boolean isNotificationShowLockScreen() {
        return notificationShowLockScreen;
    }

    public WeatherServiceEvent setNotificationShowLockScreen(boolean notificationShowLockScreen) {
        this.notificationShowLockScreen = notificationShowLockScreen;
        return this;
    }

    public int getNotificationBackgroundColor() {
        return notificationBackgroundColor;
    }

    public WeatherServiceEvent setNotificationBackgroundColor(int notificationBackgroundColor) {
        this.notificationBackgroundColor = notificationBackgroundColor;
        return this;
    }

    public int getNotificationTextColor() {
        return notificationTextColor;
    }

    public WeatherServiceEvent setNotificationTextColor(int notificationTextColor) {
        this.notificationTextColor = notificationTextColor;
        return this;
    }
}
