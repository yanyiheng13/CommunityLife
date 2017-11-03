package com.iot12369.easylifeandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.PayInfoData;
import com.iot12369.easylifeandroid.mvp.PayPresenter;
import com.iot12369.easylifeandroid.mvp.contract.PayContract;
import com.iot12369.easylifeandroid.ui.BaseFragment;
import com.iot12369.easylifeandroid.ui.PayManngeActivity;
import com.iot12369.easylifeandroid.ui.view.IconTitleView;
import com.iot12369.easylifeandroid.ui.view.PropertyAddressView;
import com.iot12369.easylifeandroid.util.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能说明： 支付页面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-24
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class PayFragment extends BaseFragment<PayPresenter> implements PayContract.View {

    @BindView(R.id.title_view)
    IconTitleView mTitleView;
    @BindView(R.id.pay_property_address)
    PropertyAddressView mPropertyView;

    //按什么支付三个按钮
    @BindView(R.id.pay_time_month_tv)
    TextView mTvByMonth;
    @BindView(R.id.pay_time_quarter_tv)
    TextView mTvByQuarter;
    @BindView(R.id.pay_time_year_tv)
    TextView mTvByYear;

    //业主姓名
    @BindView(R.id.pay_name_tv)
    TextView mTvName;
    //建筑面积
    @BindView(R.id.pay_square_meters_tv)
    TextView mTvSquareMeters;
    //单价
    @BindView(R.id.pay_money_unit_tv)
    TextView mTvUnitMoney;
    //最后支付日期
    @BindView(R.id.pay_money_date_tv)
    TextView mTvTime;

    @BindView(R.id.tv_money)
    TextView mTvMoney;

    private PayInfoData mPayInfo;

    @Override
    public int inflateId() {
        return R.layout.fragment_pay;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleView.setText(R.string.title_home).setImageResource(R.mipmap.nav_home);
        mPropertyView.goneIcon();
        mTvByYear.setSelected(true);
        mTvName.setText("~~");
        mTvSquareMeters.setText(String.format(getString(R.string.square_meters), "~~"));
        mTvUnitMoney.setText(String.format(getString(R.string.by_square_meters), "~~"));
        mTvTime.setText("~~~~-~~-~~");
        mPropertyView.setLeftTextColor(R.color.colorLoginTxt);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isResumed()) {
            getPresenter().addressList(LeApplication.mUserInfo.phone);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getPresenter().addressList(LeApplication.mUserInfo.phone);
        }
    }

    @OnClick({R.id.pay_next_tv, R.id.pay_time_month_tv, R.id.pay_time_quarter_tv, R.id.pay_time_year_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            //下一步按钮
            case R.id.pay_next_tv:
                PayManngeActivity.newIntent(getContext(), PayVo);
                break;
            case R.id.pay_time_month_tv:
                mTvByMonth.setSelected(true);
                mTvByQuarter.setSelected(false);
                mTvByYear.setSelected(false);
                if (mPayInfo != null && !TextUtils.isEmpty(mPayInfo.money)) {
                    double month = Long.valueOf(mPayInfo.money) / 12.00;
                    mTvMoney.setText(CommonUtil.formatAmountByAutomation(Math.ceil(month) + ""));
                }
                break;
            case R.id.pay_time_quarter_tv:
                mTvByQuarter.setSelected(true);
                mTvByMonth.setSelected(false);
                mTvByYear.setSelected(false);
                if (mPayInfo != null && !TextUtils.isEmpty(mPayInfo.money)) {
                    double quarter = Long.valueOf(mPayInfo.money) / 4.00;
                    mTvMoney.setText(CommonUtil.formatAmountByAutomation(Math.ceil(quarter) + ""));
                }
                break;
            case R.id.pay_time_year_tv:
                mTvByYear.setSelected(true);
                mTvByQuarter.setSelected(false);
                mTvByMonth.setSelected(false);
                if (mPayInfo != null && !TextUtils.isEmpty(mPayInfo.money)) {
                    mTvMoney.setText(CommonUtil.formatAmountByAutomation(mPayInfo.money));
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onSuccessPay(PayInfoData payInfoData) {
        mPayInfo = payInfoData;
        if (mPayInfo == null) {
           return;
        }
        if (!TextUtils.isEmpty(mPayInfo.name)) {
            mTvName.setText(mPayInfo.name);
        }
        if (!TextUtils.isEmpty(mPayInfo.cutoffdate)) {
            mTvTime.setText(mPayInfo.cutoffdate);
        }
        if (!TextUtils.isEmpty(mPayInfo.communityHouseArea)) {
            mTvSquareMeters.setText(String.format(getString(R.string.square_meters), CommonUtil.formatAmountByAutomation(mPayInfo.communityHouseArea)));
        }
        if (!TextUtils.isEmpty(mPayInfo.estateServiceUnitprice)) {
            mTvUnitMoney.setText(String.format(getString(R.string.by_square_meters), CommonUtil.formatAmountByAutomation(mPayInfo.estateServiceUnitprice)));
        }
        if (!TextUtils.isEmpty(mPayInfo.money)) {
            onClick(mTvByYear);
            mTvMoney.setText(CommonUtil.formatAmountByAutomation(mPayInfo.money));
        }
    }

    @Override
    public void onFailurePay(String code, String msg) {

    }

    @Override
    public void onSuccessAddressList(AddressData addressData) {
        mPropertyView.updateData(addressData, false);
        if (mPropertyView.isAlreadyCertification(addressData)) {
            LoginData data = LeApplication.mUserInfo;
            getPresenter().home(data.phone, data.memberId);
        }
    }

    @Override
    public void onFailureAddressList(String code, String msg) {

    }
}
