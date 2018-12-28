package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iot12369.easylifeandroid.BuildConfig;
import com.iot12369.easylifeandroid.JZProxyConfig;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.MainActivity;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AdData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.WeChatUser;
import com.iot12369.easylifeandroid.mvp.WechatLoginPresent;
import com.iot12369.easylifeandroid.mvp.contract.WeChatLoginContract;
import com.iot12369.easylifeandroid.ui.view.CustomSurfaceView;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;
import com.iot12369.easylifeandroid.util.UiTitleBarUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
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
    @BindView(R.id.surfaceView)
    CustomSurfaceView mSurfaceView;
    private MediaPlayer player;
    private SurfaceHolder holder;

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
        mSurfaceView = findViewById(R.id.surfaceView);
        player = new MediaPlayer();

        AdData mAdData = null;
        String ad = SharePrefrenceUtil.getString("config", "adData");
        if (TextUtils.isEmpty(ad) && ad.length() <= 10) {
            return;
        }
        try {
            mAdData = new Gson().fromJson(ad, new TypeToken<AdData>(){}.getType());
        } catch (Exception e) {

        }

        if (mAdData == null || TextUtils.isEmpty(mAdData.index_2)) {
            return;
        }
        try {
            mSurfaceView.setZOrderOnTop(true);
            mSurfaceView.setZOrderMediaOverlay(true);
            mSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);   // 设置画布  背景透明
            player.setDataSource(this, Uri.parse(JZProxyConfig.getInstance().getProxy().getProxyUrl(mAdData.index_2)));
            player.setLooping(true);
            holder = mSurfaceView.getHolder();
            holder.addCallback(new MyCallBack());
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                }
            });
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class MyCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
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
                finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player.isPlaying()) {
            player.release();
            player = null;
            mSurfaceView = null;
        }
    }
}
