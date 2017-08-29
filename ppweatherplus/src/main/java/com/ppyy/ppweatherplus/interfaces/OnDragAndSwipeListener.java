package com.ppyy.ppweatherplus.interfaces;

import android.support.v7.widget.RecyclerView;

/**
 * Created by NeuroAndroid on 2017/8/29.
 */

public interface OnDragAndSwipeListener {
    boolean onItemDrag(int fromPosition, int toPosition);

    void onItemSwipe(int position);

    void onItemSelected(RecyclerView.ViewHolder viewHolder);

    void onItemClear(RecyclerView.ViewHolder viewHolder);

    /**
     * @param dX Side slip distance
     */
    void onItemSwipeAlpha(RecyclerView.ViewHolder viewHolder, float dX);
}
