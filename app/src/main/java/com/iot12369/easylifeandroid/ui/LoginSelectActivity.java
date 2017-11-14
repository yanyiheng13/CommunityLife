package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iot12369.easylifeandroid.BuildConfig;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.MainActivity;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.WeChatUser;
import com.iot12369.easylifeandroid.mvp.WechatLoginPresent;
import com.iot12369.easylifeandroid.mvp.contract.WeChatLoginContract;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;
import com.iot12369.easylifeandroid.util.UiTitleBarUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 选择威信登录还是手机账号登录
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 下午9:38
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class LoginSelectActivity extends BaseActivity<WechatLoginPresent> implements WeChatLoginContract.View {
    public static String uuid = null;
    private WeChatUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mUser = (WeChatUser) getIntent().getSerializableExtra("user");
        } else {
            mUser = (WeChatUser) savedInstanceState.getSerializable("user");
        }
        UiTitleBarUtil uiTitleBarUtil = new UiTitleBarUtil(this);
        uiTitleBarUtil.setTransparentBar(Color.BLACK, 30);
        setContentView(R.layout.activity_login_selsect);
        ButterKnife.bind(this);
        uuid = UUID.randomUUID().toString();
        askWechat();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LeApplication.isExit) {
            LeApplication.isExit = false;
            finish();
        }
    }

    private void askWechat() {
        if (mUser != null) {
            LoadingDialog.show(this, false);
            mUser.os = "android";
            mUser.appid = BuildConfig.app_id;
            getPresenter().wechatRegister(mUser);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            mUser = (WeChatUser) intent.getSerializableExtra("user");
            askWechat();
        }
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, LoginSelectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void newIntent(Context context, WeChatUser user) {
        Intent intent = new Intent(context, LoginSelectActivity.class);
        intent.putExtra("user", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("user", mUser);
    }

    @Override
    protected boolean isTitleBarSetting() {
        return false;
    }

    @OnClick({R.id.ll_login_phone, R.id.ll_login_wechat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_login_wechat:
                String openid = SharePrefrenceUtil.getString("config", "openid");
                if (!LeApplication.api.isWXAppInstalled()) {
                    Toast.makeText(LoginSelectActivity.this, "未安装微信客户端，请先下载", Toast.LENGTH_LONG).show();
                    return;
                }
//                if (!TextUtils.isEmpty(openid)) {
//                    LoadingDialog.show(this, false);
//                    getPresenter().wechatLogin(openid);
//                    break;
//                }
                LoadingDialog.show(this, false);
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "diandi_wx_login";
                LeApplication.api.sendReq(req);
                break;
            case R.id.ll_login_phone:
                LoginActivity.newIntent(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessWeChatLogin(LoginData loginData) {
        LoadingDialog.hide();
        if (loginData != null && !TextUtils.isEmpty(loginData.opopenId) && !TextUtils.isEmpty(loginData.memberId)) {
            LeApplication.mUserInfo = loginData;
            if (TextUtils.isEmpty(loginData.phone)) {
                LoginActivity.newIntent(LoginSelectActivity.this, LoginActivity.TYPE_BIND, loginData);
                finish();
            } else {
                LeApplication.mUserInfo = loginData;
                MainActivity.newIntent(this);
                finish();
                SharePrefrenceUtil.setString("config", "user", new Gson().toJson(loginData));
            }
        }
    }

    @Override
    public void onFailureWeChatLogin(String code, String msg) {
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessWeChatRegister(LoginData loginData) {
        LoadingDialog.hide();
        if (loginData != null && !TextUtils.isEmpty(loginData.opopenId) && !TextUtils.isEmpty(loginData.memberId)) {
            if (mUser != null && !TextUtils.isEmpty(mUser.openid)) {
                SharePrefrenceUtil.setString("config", "openid", mUser.openid);
                loginData.nickName = mUser.nickname;
//                loginData.headimgurl = mUser.headimgurl;
                if (TextUtils.isEmpty(loginData.phone)) {
                    LoginActivity.newIntent(LoginSelectActivity.this, LoginActivity.TYPE_BIND, loginData);
                    finish();
                } else {
                    LeApplication.mUserInfo = loginData;
                    MainActivity.newIntent(this);
                    finish();
                    SharePrefrenceUtil.setString("config", "loginType", "wechat");
                    SharePrefrenceUtil.setString("config", "user", new Gson().toJson(loginData));
                }
            }
        }
        mUser = null;
    }


    @Override
    public void onFailureWeChatRegister(String code, String msg) {
        LoadingDialog.hide();
        mUser = null;
    }
}
