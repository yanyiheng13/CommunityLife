package com.community.life;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.community.life.model.UserInfo;


public class LeApplication extends Application {
    /**
     * 平台标示,测试使用,发布时要换成自己的plat
     */
    public static LeApplication mApplication;
    public static UserInfo mUserInfo;


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
//        MultiDex.install(base);
    }

    public static boolean isLogin() {
        return mUserInfo != null && !TextUtils.isEmpty(mUserInfo.token);
    }
}
