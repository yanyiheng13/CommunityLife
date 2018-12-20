package com.iot12369.easylifeandroid.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xiaomi.mipush.sdk.PushMessageReceiver;

public class XiaoMiPushReceiver extends PushMessageReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("yanyiheng", "小米推送来了");
    }

    @Override
    public void onReceivePassThroughMessage(Context context) {
        super.onReceivePassThroughMessage(context);
        Log.d("yanyiheng", "小米推送来了");
    }
}
