package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.FamilVo;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.mvp.AddPeoplePresenter;
import com.iot12369.easylifeandroid.mvp.contract.AddPeopleContract;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;
import com.iot12369.easylifeandroid.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 添加人员
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-29
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class    AddPeopleActivity extends BaseActivity<AddPeoplePresenter> implements AddPeopleContract.View {
    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;
    @BindView(R.id.add_people_name_et)
    TextView mTvName;
    @BindView(R.id.add_people_phone_tv)
    TextView mTvPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;

    private AddressVo mAddressVo;
    private String mLever;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mAddressVo = (AddressVo) getIntent().getSerializableExtra("addressVo");
            mLever = getIntent().getStringExtra("lever");
        } else {
            mAddressVo = (AddressVo) savedInstanceState.getSerializable("addressVo");
            mLever = (String) savedInstanceState.getSerializable("lever");
        }
        setContentView(R.layout.activity_add_people);
        ButterKnife.bind(this);
        mTitleView.setText(R.string.add_people);
        if (mAddressVo != null) {
            mTvAddress.setText(mAddressVo.communityRawAddress + "\n" + mAddressVo.communityName);
        }
    }

    @OnClick(R.id.add_people_tv)
    public void onClick() {
        String name = mTvName.getText().toString();
        String phone = mTvPhone.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            return;
        }
        if (!phone.startsWith("1") || phone.length() != 11) {
            ToastUtil.toast(this, "请输入正确手机号");
            return;
        }
        LoadingDialog.show(this, false);
        FamilVo familVo = new FamilVo();
        familVo.level = mLever;
        familVo.memberid = mAddressVo.memberId;
        familVo.authorizedName = name;
        familVo.phone = LeApplication.mUserInfo.phone;
        familVo.authorizedPhone = phone;
        getPresenter().addPeople(familVo);
    }

    public static void newIntent(Context context, AddressVo addressVo, String lever) {
        Intent intent = new Intent(context, AddPeopleActivity.class);
        intent.putExtra("lever", lever);
        intent.putExtra("addressVo", addressVo);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("addressVo", mAddressVo);
        outState.putString("lever", mLever);
    }

    @Override
    public void onSuccess(IsOkData isOkData) {
        LoadingDialog.hide();
        ToastUtil.toast(this, "添加成功");
        finish();
    }

    @Override
    public void onFailure(String code, String msg) {
        LoadingDialog.hide();
    }
}
