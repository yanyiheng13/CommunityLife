package com.community.life.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.community.life.MainActivity;
import com.community.life.R;
import com.community.life.ui.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 登录界面
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 下午11:04
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.title_view)
    TitleView mTitleView;

    @BindView(R.id.login_phone_edit)
    EditText mEditPhone;

    @BindView(R.id.login_code_edit)
    EditText mEditCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTitleView.setText(R.string.login);
    }


    @OnClick({R.id.login_get_code_tv, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_get_code_tv:
                break;
            case R.id.btn_login:
                MainActivity.newIntent(this);
                break;
            default:
                break;
        }
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
