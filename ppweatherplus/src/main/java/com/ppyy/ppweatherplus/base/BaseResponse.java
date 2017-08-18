package com.ppyy.ppweatherplus.base;

import java.io.Serializable;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class BaseResponse implements Serializable {
    private BaseMeta meta;

    public BaseMeta getMeta() {
        return meta;
    }

    public void setMeta(BaseMeta meta) {
        this.meta = meta;
    }
}
