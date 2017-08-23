package com.community.life;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


public class LeApplication extends Application {
    /**
     * 平台标示,测试使用,发布时要换成自己的plat
     */
    public static LeApplication mApplication;


    @Override
    public void onCreate() {
        super.onCreate();
//        Log.LOG = true;
//        QueuedWork.isUseThreadPool = false;
        mApplication = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
