package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

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
    @BindView(R.id.jzVideo)
    JZVideoPlayerStandard mJzVideo;

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
        final String url = "http://xuanyiapi2.iot12369.com:8989/images/2018/12/88000692.mp4";
        mJzVideo.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN , "");
        Glide.with(this).load(R.drawable.login_bg).into(mJzVideo.getBgImg());
        mJzVideo.setOnCompleteListener(new JZVideoPlayerStandard.OnCompleteListener() {
            @Override
            public void onPlayComplete() {
                mJzVideo.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN , "");
                mJzVideo.startVideo();
            }

            @Override
            public void onPlayError() {
                mJzVideo.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, "");
                mJzVideo.startVideo();
            }
        });
        mJzVideo.startVideo();
        mJzVideo.setOnLoginListener(new JZVideoPlayerStandard.OnLoginListener() {
            @Override
            public void onLogin(int type) {
                if (type == 1) {
                    String openid = SharePrefrenceUtil.getString("config", "openid");
                    if (!LeApplication.api.isWXAppInstalled()) {
                        Toast.makeText(LoginSelectActivity.this, "未安装微信客户端，请先下载", Toast.LENGTH_LONG).show();
                        return;
                    }
                    LoadingDialog.show(LoginSelectActivity.this, false);
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "diandi_wx_login";
                    LeApplication.api.sendReq(req);
                } else {
                    LoginActivity.newIntent(LoginSelectActivity.this);
                }
            }
        });
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
