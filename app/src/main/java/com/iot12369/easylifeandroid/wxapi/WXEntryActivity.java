package com.iot12369.easylifeandroid.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iot12369.easylifeandroid.BuildConfig;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.WeChatToken;
import com.iot12369.easylifeandroid.model.WeChatUser;
import com.iot12369.easylifeandroid.ui.BaseActivity;
import com.iot12369.easylifeandroid.ui.LoginActivity;
import com.iot12369.easylifeandroid.ui.LoginSelectActivity;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.util.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author YiHeng Yan
 * @Description
 * @Email yanyiheng86@163.com
 * @date 2017/9/14 23:35
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果没回调onResp，八成是这句没有写
        LeApplication.api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        LeApplication.api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType())
                    ToastUtil.toast(this, "分享失败");
                else ToastUtil.toast(this, "登录失败");
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        getInfo(code);
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求

                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        ToastUtil.toast(this, "微信分享成功");
                        finish();
                        break;
                }
                break;
        }
    }

    public void getInfo(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&appid=";
        //***********&secret=***********&result=***********
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append(BuildConfig.app_id);
        builder.append("&secret=");
        builder.append(BuildConfig.app_secret);
        builder.append("&result=");
        builder.append(code);
        OkHttpClient okHttpClient = new OkHttpClient();

        // 创建请求参数
        Request request = new Request.Builder().url(builder.toString()).build();

        // 创建请求对象
        Call call = okHttpClient.newCall(request);

        // 发起异步的请求
        call.enqueue(new Callback() {
            @Override
            // 请求发生异常
            public void onFailure(Call call, IOException e) {
                finish();
            }

            @Override
            // 获取到服务器数据。注意：即使是 404 等错误状态也是获取到服务器数据
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String result = response.body().string();
                    WeChatToken token = new Gson().fromJson(result, new TypeToken<WeChatToken>(){}.getType());
                    if (token != null && !TextUtils.isEmpty(token.access_token) && !TextUtils.isEmpty(token.openid)) {
                        Message message = handler.obtainMessage();
                        message.what = 2;
                        handler.sendMessage(message);
                        getUserInfo(token.access_token, token.openid);
                    } else {
                        Message message = handler.obtainMessage();
                        message.what = 0;
                        handler.sendMessage(message);
                    }
                }
            }
        });
    }

    public void getUserInfo(String token, String openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=";
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        builder.append(token);
        builder.append("&openid=");
        builder.append(openid);
        OkHttpClient okHttpClient = new OkHttpClient();

        // 创建请求参数
        Request request = new Request.Builder().url(builder.toString()).build();

        // 创建请求对象
        Call call = okHttpClient.newCall(request);

        // 发起异步的请求
        call.enqueue(new Callback() {
            @Override
            // 请求发生异常
            public void onFailure(Call call, IOException e) {
                finish();
            }

            @Override
            // 获取到服务器数据。注意：即使是 404 等错误状态也是获取到服务器数据
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String result = response.body().string();
                    WeChatUser user = new Gson().fromJson(result, new TypeToken<WeChatUser>(){}.getType());
                    Message msg = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putSerializable("user", user);
                    msg.what = 1;
                    msg.setData(b);
                    handler.sendMessage(msg);
                } else {
                    Message message = handler.obtainMessage();
                    message.what = 0;
                    handler.sendMessage(message);
                }

            }
        });
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    LoadingDialog.hide();
                    finish();
                    break;
                case 1:
                    LoadingDialog.hide();
                    Bundle bundle = msg.getData();
                    if (bundle != null) {
                        WeChatUser user = (WeChatUser) bundle.getSerializable("user");
                        LoginSelectActivity.newIntent(WXEntryActivity.this, user);
                    }
                    finish();
                    break;
                case 2:
                    LoadingDialog.show(WXEntryActivity.this, false);
                    break;
                default:
                    break;
            }
        }
    };

}