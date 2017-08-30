package com.ppyy.ppweatherplus.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ppyy.ppweatherplus.interfaces.NetworkCallBack;
import com.ppyy.ppweatherplus.utils.NetworkUtils;

/**
 * Created by NeuroAndroid on 2017/8/22.
 */

public class NetworkReceiver extends BroadcastReceiver {
    private NetworkCallBack mNetworkCallback;

    public NetworkReceiver(NetworkCallBack networkCallback) {
        mNetworkCallback = networkCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mNetworkCallback != null) {
            if (NetworkUtils.isAvailable(context)) {
                // 当前网络状态可用
                mNetworkCallback.onNetworkChange(true);
            } else {
                // 当前网络不可用
                mNetworkCallback.onNetworkChange(false);
            }
        }
    }
}
