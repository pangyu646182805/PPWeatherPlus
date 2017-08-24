package com.ppyy.ppweatherplus.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.ppyy.colorful.Colorful;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.utils.L;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class BaseApplication extends Application {
    private static Context sContext;
    private static Handler sHandler;

    public static Handler getHandler() {
        return sHandler;
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 全局设置是否需要Log打印
         */
        L.setGlobalToggle(true);
        sContext = getApplicationContext();
        sHandler = new Handler();

        Colorful.defaults()
                .primaryColor(Colorful.ThemeColor.WHITE)
                .accentColor(Colorful.ThemeColor.NIGHT)
                .translucent(true)
                .night(false);
        Colorful.init(this);

        EasyHttp.init(this);

        EasyHttp.getInstance()
                .debug("RxEasyHttp", true)
                .setReadTimeOut(60 * 1000)
                .setWriteTimeOut(60 * 1000)
                .setConnectTimeout(60 * 1000)
                .setRetryCount(3)  // 默认网络不好自动重试3次
                .setRetryDelay(500)  // 每次延时500ms重试
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                .setBaseUrl(Constant.BASE_URL)
                .setCacheDiskConverter(new SerializableDiskConverter())  // 默认缓存使用序列化转化
                .setCacheMaxSize(50 * 1024 * 1024)  // 设置缓存大小为50M
                .setCacheVersion(1)  // 缓存版本为1
                .setCertificates();  // 信任所有证书
                // .addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
                // .addInterceptor(new CustomSignInterceptor());//添加参数签名拦截器
    }
}
