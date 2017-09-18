package com.ppyy.ppweatherplus.event;

/**
 * Created by NeuroAndroid on 2017/5/19.
 */

public class BaseEvent {
    public static final int EVENT_SELECT_CITY = 222;
    public static final int EVENT_STATUS_BAR_COLORING = 333;
    public static final int EVENT_LINE_TYPE = 444;
    public static final int EVENT_WEATHER_SERVICE = 555;
    public static final int EVENT_APP_WIDGET = 666;

    private int eventFlag;

    public int getEventFlag() {
        return eventFlag;
    }

    public BaseEvent setEventFlag(int eventFlag) {
        this.eventFlag = eventFlag;
        return this;
    }
}
