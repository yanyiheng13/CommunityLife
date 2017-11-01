package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.mvp.AddAddressPresenter;
import com.iot12369.easylifeandroid.mvp.contract.AddAddressContract;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;
import com.iot12369.easylifeandroid.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 添加地址
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-28
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class AddAddressActivity extends BaseActivity<AddAddressPresenter> implements AddAddressContract.View {
    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;

    @BindView(R.id.add_address_name_et)
    EditText mEtName;
    @BindView(R.id.add_address_num_et)
    EditText mEtCertificationNum;
    @BindView(R.id.add_address_tel_tv)
    TextView mTvPhoneNum;
    @BindView(R.id.add_address_my_et)
    EditText mEtAddress;
    //所在小区
    @BindView(R.id.add_address_location_et)
    EditText mEtLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        mTitleView.setText(R.string.add_address_no).setImageResource(R.mipmap.icon_account_certification);
        mTvPhoneNum.setText(LeApplication.mUserInfo.phone);
    }

    @OnClick(R.id.add_address_tv)
    public void onClick() {
        if (TextUtils.isEmpty(mEtName.getText().toString()) || TextUtils.isEmpty(mEtCertificationNum.getText().toString())
                || TextUtils.isEmpty(mEtAddress.getText().toString())
                || TextUtils.isEmpty(mTvPhoneNum.getText().toString())
                || TextUtils.isEmpty(mEtLocation.getText().toString())) {
            return;
        }
        LoadingDialog.show(this, false);
        LoginData data = LeApplication.mUserInfo;
        getPresenter().addAddress(data.opopenId, data.memberId, data.phone, mEtName.getText().toString(),
                mEtCertificationNum.getText().toString(), mEtLocation.getText().toString(), mEtAddress.getText().toString());
    }


    public static void newIntent(Context context) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onSuccessAddress(AddressVo addressData) {
        LoadingDialog.hide();
        if (!TextUtils.isEmpty(addressData.memberId)) {
            ToastUtil.toast(this, "认证成功");
            finish();
        }
    }

    @Override
    public void onFailureAddress(String code, String msg) {
        LoadingDialog.hide();
    }
}
