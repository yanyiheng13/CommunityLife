package com.community.life.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.community.life.R;
import com.community.life.ui.view.PropertyAddressView;
import com.community.life.ui.view.WithBackTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.authorization_family_title_tv)
    TextView mTvFamily;
    @BindView(R.id.authorization_tenants_title_tv)
    TextView mTvTenants;

    @BindView(R.id.property_address_view)
    PropertyAddressView mAddressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);
        //设置账号授权界面标题icon和文字
        mTitleView.setText(R.string.account_authorise).setImageResource(R.mipmap.icon_account_authorise);
        mAddressView.goneIcon().updateData("1");

    }

    @OnClick({R.id.authorization_family_title_tv, R.id.authorization_tenants_title_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.authorization_family_title_tv:
                mTvFamily.setSelected(true);
                mTvTenants.setSelected(false);
                break;
            case R.id.authorization_tenants_title_tv:
                mTvFamily.setSelected(false);
                mTvTenants.setSelected(true);
                break;
            default:
                break;
        }
    }
}
