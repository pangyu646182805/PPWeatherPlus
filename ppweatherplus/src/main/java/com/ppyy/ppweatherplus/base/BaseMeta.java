package com.ppyy.ppweatherplus.base;

/**
 * Created by NeuroAndroid on 2017/8/18.
 */

public class BaseMeta {
    private int status;  // 返回状态码
    private String desc;  // 返回描述

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
