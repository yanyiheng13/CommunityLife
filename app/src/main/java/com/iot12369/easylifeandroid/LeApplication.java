package com.iot12369.easylifeandroid;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;
import com.lkl.pay.app.application.ApplicationController;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class LeApplication extends Application {
    /**
     * 平台标示,测试使用,发布时要换成自己的plat
     */
    public static LeApplication mApplication;
    public static LoginData mUserInfo;
    public static IWXAPI api;
    public static boolean isExit;

    public static int TAG_HOME =1;
    public static int TAG_MINE = 2;
    public static int TAG_PAY = 0;

    public static int mCurrentTag;


    @Override
    public void onCreate() {
        super.onCreate();
//        Log.LOG = true;
//        QueuedWork.isUseThreadPool = false;
        ApplicationController.initData(this);
        mApplication = this;
        api = WXAPIFactory.createWXAPI(this, BuildConfig.app_id);
        api.registerApp(BuildConfig.app_id);
        String json = SharePrefrenceUtil.getString("config", "user");
        if (!TextUtils.isEmpty(json)) {
            mUserInfo = new Gson().fromJson(json, new TypeToken<LoginData>(){}.getType());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(base);
    }

    public static boolean isLogin() {
        return mUserInfo != null && !TextUtils.isEmpty(mUserInfo.phone);
    }

}
