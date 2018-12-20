package com.iot12369.easylifeandroid;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;
import com.tencent.android.otherPush.StubAppUtils;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
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

    public static int TAG_HOME = 2;
    public static int TAG_MINE = 4;
    public static int TAG_PAY = 0;
    public static int TAG_MAINTAIN = 1;
    public static int TAG_COMPLAIN = 3;

    public static int mCurrentTag;
    public static AddressVo mAddressVo;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        api = WXAPIFactory.createWXAPI(this, BuildConfig.app_id);
        api.registerApp(BuildConfig.app_id);
        String str = SharePrefrenceUtil.getString("config", "loginType");
        String json = SharePrefrenceUtil.getString("config", "user");
        if (!TextUtils.isEmpty(json) || json.length() > 10) {
            mUserInfo = new Gson().fromJson(json, new TypeToken<LoginData>() {}.getType());
        }
        XGPushConfig.enableDebug(this, true);
        XGPushConfig.enableOtherPush(getApplicationContext(), true);
        XGPushConfig.setHuaweiDebug(true);
        XGPushConfig.setMiPushAppId(getApplicationContext(), "2882303761517681516");
        XGPushConfig.setMiPushAppKey(getApplicationContext(), "5951768191516");
        XGPushConfig.setMzPushAppId(this, "6fa9f2d0210a42dc95cbc46597316b1f");
        XGPushConfig.setMzPushAppKey(this, "9218b5d379fd455796197c57af1b237f");
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                Log.d("TPush", "注册成功，设备token为：" + data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
        XGPushManager.bindAccount(getApplicationContext(), "XINGE");
        XGPushManager.setTag(this, "XINGE");
        JZProxyConfig.getInstance().init(this);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(context);
        StubAppUtils.attachBaseContext(context);
    }

    public static boolean isLogin() {
        return mUserInfo != null && !TextUtils.isEmpty(mUserInfo.phone);
    }

}
