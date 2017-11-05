package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.FamilyData;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.PersonData;
import com.iot12369.easylifeandroid.mvp.AuthorizationPresenter;
import com.iot12369.easylifeandroid.mvp.contract.AuthorizationContract;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.ui.view.PropertyAddressView;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;
import com.iot12369.easylifeandroid.util.ToastUtil;

import java.util.List;

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

public class AuthorizationActivity extends BaseActivity<AuthorizationPresenter> implements AuthorizationContract.View {

    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;

    @BindView(R.id.authorization_family_title_tv)
    TextView mTvFamily;
    @BindView(R.id.authorization_tenants_title_tv)
    TextView mTvTenants;
    @BindView(R.id.authorization_identity_tv)
    TextView mTvGroupName;

    @BindView(R.id.property_address_view)
    PropertyAddressView mPeopleView;
    @BindView(R.id.property_address_view_two)
    PropertyAddressView mAddressView;

    private AddressData addressData;
    private AddressVo addressVo;
    public FamilyData familyData;

    private String level = "2" ;// 2 家庭成员   3 租客

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);
        //设置账号授权界面标题icon和文字
        mTitleView.setText(R.string.account_authorise).setImageResource(R.mipmap.icon_account_authorise);
        mPeopleView.goneGroup();
        mAddressView.goneIcon().updateData().setText(R.string.property_address);
        mTvFamily.setSelected(true);
        getPresenter().addressList(LeApplication.mUserInfo.phone);
        mAddressView.setOnItemClickListener(new PropertyAddressView.OnItemClickListener() {
            @Override
            public void onItemClick(String memberid) {
                LoadingDialog.show(AuthorizationActivity.this, false);
                getPresenter().setDefaultAdress(memberid, LeApplication.mUserInfo.phone);
            }
        });
        mPeopleView.setOnPeopleItemClickListener(new PropertyAddressView.OnPeopleItemClickListener() {
            @Override
            public void onItemClick(String memberid) {
                LoadingDialog.show(AuthorizationActivity.this, false);
                getPresenter().removePeople(addressVo.memberId, memberid);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (addressVo == null) {
            return;
        }
        LoginData loginData = LeApplication.mUserInfo;
        getPresenter().familyList(loginData.phone, addressVo.memberId);
    }

    @OnClick({R.id.authorization_family_title_tv, R.id.authorization_tenants_title_tv, R.id.authorization_add_people_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.authorization_family_title_tv:
                mTvFamily.setSelected(true);
                mTvTenants.setSelected(false);
                mTvGroupName.setText(R.string.families);
                level = "2";
                mPeopleView.updateData(level, familyData);
                break;
            case R.id.authorization_tenants_title_tv:
                mTvFamily.setSelected(false);
                mTvTenants.setSelected(true);
                mTvGroupName.setText(R.string.tenant);
                level = "3";
                mPeopleView.updateData(level, familyData);
                break;
            case R.id.authorization_add_people_tv:
                AddPeopleActivity.newIntent(this, addressVo, level);
                break;
            default:
                break;
        }
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, AuthorizationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onSuccessRemove(IsOkData data) {
        LoadingDialog.hide();
        if (data.isOk()) {
            ToastUtil.toast(this, "移除成功");
            LoginData loginData = LeApplication.mUserInfo;
            getPresenter().familyList(loginData.phone, addressVo.memberId);
        }
    }

    @Override
    public void onFailureRemove(String code, String msg) {
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessAddressList(AddressData addressData) {
        this.addressData = addressData;
        mAddressView.updateData(addressData, false);
        addressVo = mAddressView.getCurrentAddress(addressData);
        if (addressVo != null) {
            LoginData loginData = LeApplication.mUserInfo;
            getPresenter().familyList(loginData.phone, addressVo.memberId);
        }
    }

    @Override
    public void onFailureAddressList(String code, String msg) {

    }

    @Override
    public void onSuccessAddress(AddressVo addressVo) {
        LoadingDialog.hide();
        if (addressData != null && addressData.list != null && addressData.list.size() != 0) {
            List<AddressVo> list = addressData.list;
            int size = list.size();
            for(int i = 0; i < size; i++) {
                AddressVo vo = list.get(i);
                if (vo != null && addressVo != null && addressVo.memberId.equals(vo.memberId)) {
                    vo.currentEstate = "1";
                    this.addressVo = vo;
                } else {
                    vo.currentEstate = "0";
                }
            }
            mAddressView.updateData(addressData, false);
            LoginData loginData = LeApplication.mUserInfo;
            getPresenter().familyList(loginData.phone, this.addressVo.memberId);
        }
    }

    @Override
    public void onFailureAddress(String code, String msg) {
        LoadingDialog.hide();

    }

    @Override
    public void onSuccessFamilyList(FamilyData familyData) {
        this.familyData = familyData;
        mPeopleView.updateData(level, familyData);
    }

    @Override
    public void onFailureFamilyList(String code, String msg) {

    }
}
