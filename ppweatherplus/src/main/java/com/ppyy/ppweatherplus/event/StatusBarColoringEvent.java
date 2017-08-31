package com.ppyy.ppweatherplus.event;

/**
 * Created by NeuroAndroid on 2017/8/31.
 */

public class StatusBarColoringEvent extends BaseEvent {
    private boolean statusBarColoring;

    public StatusBarColoringEvent() {
        setEventFlag(EVENT_STATUS_BAR_COLORING);
    }

    public boolean isStatusBarColoring() {
        return statusBarColoring;
    }

    public StatusBarColoringEvent setStatusBarColoring(boolean statusBarColoring) {
        this.statusBarColoring = statusBarColoring;
        return this;
    }
}
