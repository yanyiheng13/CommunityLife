package com.community.life.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.community.life.R;
import com.community.life.ui.view.WithBackTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明： 账号授权界面
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-27 下午3:34
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class AuthorizationActivity extends BaseActivity {
    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);
        //设置账号授权界面标题icon和文字
        mTitleView.setText(R.string.account_authorise).setImageResource(R.mipmap.icon_account_authorise);
    }
}
