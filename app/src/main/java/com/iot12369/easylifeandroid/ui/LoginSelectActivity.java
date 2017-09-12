package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.util.UiTitleBarUtil;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiTitleBarUtil uiTitleBarUtil = new UiTitleBarUtil(this);
        uiTitleBarUtil.setTransparentBar(Color.BLACK, 30);
        setContentView(R.layout.activity_login_selsect);
        ButterKnife.bind(this);
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
                break;
            case R.id.ll_login_phone:
                LoginActivity.newIntent(this);
                break;
            default:
                break;
        }
    }
}
