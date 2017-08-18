package com.ppyy.ppweatherplus.interfaces;

import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;

/**
 * Created by NeuroAndroid on 2017/2/14.
 */

public interface OnItemClickListener<T> {
    void onItemClick(BaseViewHolder holder, int position, T item);
}
