package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;

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
public class AddAddressActivity extends BaseActivity {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        mTitleView.setText(R.string.add_address_no).setImageResource(R.mipmap.icon_account_certification);
    }

    @OnClick(R.id.add_address_tv)
    public void onClick() {

    }


    public static void newIntent(Context context) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        context.startActivity(intent);
    }
}
