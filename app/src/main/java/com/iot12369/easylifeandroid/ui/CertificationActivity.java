package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.PersonData;
import com.iot12369.easylifeandroid.model.UserInfo;
import com.iot12369.easylifeandroid.mvp.PersonInfoPresenter;
import com.iot12369.easylifeandroid.mvp.contract.PersonInfoContract;
import com.iot12369.easylifeandroid.ui.view.PropertyAddressView;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;

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
public class CertificationActivity extends BaseActivity<PersonInfoPresenter> implements PersonInfoContract.View {

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
    ImageView mImgCerStatus;
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
        mPhoneNum.setText(LeApplication.mUserInfo.phone);
        mTitleView.setText(R.string.account_certification).setImageResource(R.mipmap.icon_account_certification);
//        mProperView.updateData(true);
        mProperView.setText(R.string.my_pro);
        mProperView.setGoneTxt();
        LoginData userInfo = LeApplication.mUserInfo;
        String openid = SharePrefrenceUtil.getString("config", "openid");
        if (TextUtils.isEmpty(openid)) {
            mImgStatus.setImageResource(R.mipmap.icon_not_bind);
        } else {
            mImgStatus.setImageResource(R.mipmap.icon_binded);
        }
        String str = SharePrefrenceUtil.getString("config", "loginType");
        if ("wechat".equals(str)) {
            mTvCommunity.setText(userInfo.nickName);
        } else {
            mTvCommunity.setText(userInfo.phone);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().addressList(LeApplication.mUserInfo.phone);
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

    @Override
    public void onSuccessPerson(PersonData data) {

    }

    @Override
    public void onFailurePerson(String code, String msg) {

    }

    @Override
    public void onSuccessAddressList(AddressData addressData) {
        if (mProperView.isAlreadyCertification(addressData)) {
            mImgCerStatus.setImageResource(R.mipmap.icon_certification);
        } else {
            mImgCerStatus.setImageResource(R.mipmap.a_no_cer);
        }
        mProperView.updateData(addressData, true);
    }

    @Override
    public void onFailureAddressList(String code, String msg) {

    }

    @Override
    public void onSuccessAddress(AddressVo addressVo) {

    }

    @Override
    public void onFailureAddress(String code, String msg) {

    }
}
