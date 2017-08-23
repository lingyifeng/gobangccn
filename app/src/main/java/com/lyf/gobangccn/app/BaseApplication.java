package com.lyf.gobangccn.app;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.jiang.common.base.CommonApplication;
import com.jiang.common.utils.LogUtils;
import com.lyf.gobangccn.easemob.DemoHelper;


public class BaseApplication extends CommonApplication {

    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler.getInstance().init(this);
//        if (!BuildConfig.DEBUG)
        LogUtils.logInit(true);
        DemoHelper.getInstance().init(this);
        SDKInitializer.initialize(this);
    }

    @Override

    protected void attachBaseContext(Context base) {

        super.attachBaseContext(base);

        MultiDex.install(this);

    }


    /**
     * 防止由于系统原因字体大小变化
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();

            res.updateConfiguration(newConfig, res.getDisplayMetrics());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                createConfigurationContext(newConfig);
            } else {
                res.updateConfiguration(newConfig, res.getDisplayMetrics());
            }
        }
        return res;
    }
}



