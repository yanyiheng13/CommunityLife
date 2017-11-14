package com.iot12369.easylifeandroid.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.PayInfoData;
import com.iot12369.easylifeandroid.model.PayRequest;
import com.iot12369.easylifeandroid.mvp.PayPresenter;
import com.iot12369.easylifeandroid.mvp.contract.PayContract;
import com.iot12369.easylifeandroid.ui.AddAddressActivity;
import com.iot12369.easylifeandroid.ui.BaseFragment;
import com.iot12369.easylifeandroid.ui.PayManngeActivity;
import com.iot12369.easylifeandroid.ui.view.IconTitleView;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.ui.view.MyDialog;
import com.iot12369.easylifeandroid.ui.view.PropertyAddressView;
import com.iot12369.easylifeandroid.util.CommonUtil;

import java.util.List;

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
    public AddressData mAddressData;
    public String time = "物业费-12个月";

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
        mPropertyView.setOnItemClickListener(new PropertyAddressView.OnItemClickListener() {
            @Override
            public void onItemClick(String memberid) {
                LoadingDialog.show(PayFragment.this.getContext(), false);
                getPresenter().setDefaultAdress(memberid, LeApplication.mUserInfo.phone);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isResumed() && LeApplication.mCurrentTag == LeApplication.TAG_PAY) {
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
                if (!isAlreadyCertification(mAddressData != null ? mAddressData.list : null)) {
                    getPopupWindow().show();
                    break;
                }
                if (mPayInfo == null) {
                    break;
                }
                String money = mTvMoney.getText().toString();
                PayRequest payVo = new PayRequest();
                payVo.amountShow = money;//这个是支付飞传字段  在结果页显示使用
                payVo.communityRawAddress = mPayInfo.communityRawAddress;//这个是支付飞传字段  在结果页显示使用

                payVo.amount = money.replace(",", "");
                payVo.order_no = mPayInfo.orderno;
                payVo.body = mPayInfo.body;
                payVo.subject = time;
                payVo.description = mPayInfo.description;

                PayManngeActivity.newIntent(getContext(), payVo);
                break;
            case R.id.pay_time_month_tv:
                time = "物业费一个月";
                mTvByMonth.setSelected(true);
                mTvByQuarter.setSelected(false);
                mTvByYear.setSelected(false);
                if (mPayInfo != null && !TextUtils.isEmpty(mPayInfo.money)) {
                    double month = Long.valueOf(mPayInfo.money) / 12.00;
                    mTvMoney.setText(CommonUtil.formatAmountByAutomation(Math.ceil(month) + ""));
                }
                break;
            case R.id.pay_time_quarter_tv:
                time = "物业费-3个月";
                mTvByQuarter.setSelected(true);
                mTvByMonth.setSelected(false);
                mTvByYear.setSelected(false);
                if (mPayInfo != null && !TextUtils.isEmpty(mPayInfo.money)) {
                    double quarter = Long.valueOf(mPayInfo.money) / 4.00;
                    mTvMoney.setText(CommonUtil.formatAmountByAutomation(Math.ceil(quarter) + ""));
                }
                break;
            case R.id.pay_time_year_tv:
                time = "物业费-12个月";
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


    public Dialog getPopupWindow() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_certi, null);
        TextView txtCer = (TextView) contentView.findViewById(R.id.cer_tv);
        TextView close = (TextView) contentView.findViewById(R.id.close);
        final MyDialog popWnd = new MyDialog(getContext());
//        popWnd.set
        popWnd.setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popWnd.setCancelable(true);
        popWnd.setCanceledOnTouchOutside(true);
        txtCer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
                AddAddressActivity.newIntent(getContext());
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
            }
        });
        return popWnd;
    }

    public boolean isAlreadyCertification(List<AddressVo> addressData) {
        if (addressData == null || addressData.size() == 0) {
            return false;
        }
        boolean isAlready = false;
        int size = addressData.size();
        for (int i = 0; i < size; i++) {
            AddressVo addressVo = addressData.get(i);
            if ("2".equals(addressVo.estateAuditStatus)) {
                isAlready = true;
                break;
            }
        }
        return isAlready;
    }

    @Override
    public void onSuccessPayPre(PayInfoData payInfoData) {
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
    public void onFailurePayPre(String code, String msg) {

    }

    @Override
    public void onSuccessAddressList(AddressData addressData) {
        mAddressData = addressData;
        mPropertyView.updateData(addressData, false);
        if (mPropertyView.isAlreadyCertification(addressData)) {
            LoginData data = LeApplication.mUserInfo;
            AddressVo addressVo = mPropertyView.getCurrentAddress(addressData);
            if (addressVo != null) {
                getPresenter().home(data.phone, addressVo.memberId);
            }
        }
        LeApplication.mAddressVo = mPropertyView.getCurrentAddress(addressData);
    }

    @Override
    public void onFailureAddressList(String code, String msg) {
    }

    @Override
    public void onSuccessAddress(AddressVo addressVo) {
        LoadingDialog.hide();
        AddressVo currentVo = null;
        if (mAddressData != null && mAddressData.list != null && mAddressData.list.size() != 0) {
            List<AddressVo> list = mAddressData.list;
            int size = list.size();
            for(int i = 0; i < size; i++) {
                AddressVo vo = list.get(i);
                if (vo != null && addressVo != null && addressVo.memberId.equals(vo.memberId)) {
                    vo.currentEstate = "1";
                    currentVo = vo;
                } else {
                    vo.currentEstate = "0";
                }
            }
            mPropertyView.updateData(mAddressData, false);
            if (currentVo != null) {
                getPresenter().home(LeApplication.mUserInfo.phone, currentVo.memberId);
            }
        }
    }

    @Override
    public void onFailureAddress(String code, String msg) {
        LoadingDialog.hide();
    }
}
