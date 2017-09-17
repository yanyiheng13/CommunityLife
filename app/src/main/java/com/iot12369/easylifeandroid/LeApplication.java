package com.iot12369.easylifeandroid;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.iot12369.easylifeandroid.model.UserInfo;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class LeApplication extends Application {
    /**
     * 平台标示,测试使用,发布时要换成自己的plat
     */
    public static LeApplication mApplication;
    public static UserInfo mUserInfo;
    public static IWXAPI api;


    @Override
    public void onCreate() {
        super.onCreate();
//        Log.LOG = true;
//        QueuedWork.isUseThreadPool = false;
        mApplication = this;
        api = WXAPIFactory.createWXAPI(this, BuildConfig.app_id);
        api.registerApp(BuildConfig.app_id);
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
