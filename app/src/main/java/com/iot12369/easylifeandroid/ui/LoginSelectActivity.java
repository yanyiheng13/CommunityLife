package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.iot12369.easylifeandroid.BuildConfig;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
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

public class LoginSelectActivity extends BaseActivity {
    public static String uuid = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiTitleBarUtil uiTitleBarUtil = new UiTitleBarUtil(this);
        uiTitleBarUtil.setTransparentBar(Color.BLACK, 30);
        setContentView(R.layout.activity_login_selsect);
        ButterKnife.bind(this);
        uuid = UUID.randomUUID().toString();

    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, LoginSelectActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean isTitleBarSetting() {
        return false;
    }

    @OnClick({R.id.ll_login_phone, R.id.ll_login_wechat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_login_wechat:
                if (!LeApplication.api.isWXAppInstalled()) {
                    Toast.makeText(LoginSelectActivity.this, "未安装微信客户端，请先下载", Toast.LENGTH_LONG).show();
                    return;
                }
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
}
