package com.community.life.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.community.life.R;
import com.community.life.ui.view.PropertyAddressView;
import com.community.life.ui.view.WithBackTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 账号认证
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-28
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class CertificationActivity extends BaseActivity {

    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;

    @BindView(R.id.certification_head_img)
    ImageView mImageHead;
    //微信绑定状态
    @BindView(R.id.certification_wechat_status_img)
    ImageView mImgStatus;
    //账号等级
    @BindView(R.id.certification_level_img)
    ImageView mImgLever;
    @BindView(R.id.certification_name_tv)
    TextView mTvName;
    @BindView(R.id.certification_address_tv)
    TextView mTvCommunity;
    @BindView(R.id.certification_phone_num_tv)
    TextView mPhoneNum;
    //我的地址控件
    @BindView(R.id.certification_address_view)
    PropertyAddressView mProperView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
        mTitleView.setText(R.string.account_certification).setImageResource(R.mipmap.icon_account_certification);
        mProperView.updateData(true);
        mProperView.goneTxt();
    }


    @OnClick({R.id.certification_add_address_tv, R.id.certification_head_img})
    public void onClick(View view) {
        switch (view.getId()) {
            //添加地址
            case R.id.certification_add_address_tv:
                AddAddressActivity.newIntent(this);
                break;
            //头像
            case R.id.certification_head_img:
                break;
            default:
                break;
        }
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, CertificationActivity.class);
        context.startActivity(intent);
    }
}