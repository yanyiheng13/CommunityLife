package com.iot12369.easylifeandroid.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.PayInfoData;
import com.iot12369.easylifeandroid.model.PayOtherInfo;
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
import com.iot12369.easylifeandroid.util.BigDecimalBuilder;
import com.iot12369.easylifeandroid.util.CommonUtil;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;
import com.iot12369.easylifeandroid.util.ToastUtil;

import java.io.Serializable;
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

    // 物业费三个按钮
    @BindView(R.id.pay_time_month_tv)
    TextView mTvByMonth;
    @BindView(R.id.pay_time_quarter_tv)
    TextView mTvByQuarter;
    @BindView(R.id.pay_time_year_tv)
    TextView mTvByYear;

    // 停车费三个按钮
    @BindView(R.id.pay_car_month_tv)
    TextView mTvCarByMonth;
    @BindView(R.id.pay_car_quarter_tv)
    TextView mTvCarByQuarter;
    @BindView(R.id.pay_car_year_tv)
    TextView mTvCarByYear;

    // 物业费是否选中
    @BindView(R.id.pay_wuye_checkBox)
    CheckBox mCheckBox;

    // 停车费是否选中
    @BindView(R.id.pay_car_checkBox)
    CheckBox mCheckBoxCar;

    // 物业缴费数据
    @BindView(R.id.rlWuyeCount)
    RelativeLayout mRlWuyeCount;
    @BindView(R.id.llWuyeCount)
    LinearLayout mLlWuyeCount;

    // 停车费缴费数据
    @BindView(R.id.rLCarCount)
    RelativeLayout mRlCarCount;
    @BindView(R.id.lLCarCount)
    LinearLayout mLlCarCount;

    //业主姓名
    @BindView(R.id.pay_name_tv)
    TextView mTvName;
    //建筑面积
    @BindView(R.id.pay_square_meters_tv)
    TextView mTvSquareMeters;
    //单价
    @BindView(R.id.pay_money_unit_tv)
    TextView mTvUnitMoney;
    @BindView(R.id.pay_money_car_unitTv)
    TextView mTvCarUnitMoney;
    //最后支付日期
    @BindView(R.id.pay_money_date_tv)
    TextView mTvTime;
    @BindView(R.id.pay_money_car_dateTv)
    TextView mTvCarTime;
    @BindView(R.id.rlCarMoney)
    RelativeLayout mRlCarMoney;
    @BindView(R.id.rlCar)
    RelativeLayout mRlCarDate;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_moneyCar)
    TextView mTvMoneyCar;
    @BindView(R.id.tvTotalAmount)
    TextView mTvTotalAmount;
    /**
     * 当前已经认证的地址，如果地址不变 不请求缴费信息
     */
    private AddressVo mAddressVo;

    private PayInfoData mPayInfo;
    public AddressData mAddressData;
    public String time = "物业费-12个月";
    public String time1 = "停车费-12个月";

    public String monthWuye = "12";
    public String monthCar = "12";

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
        mTvCarByYear.setSelected(true);
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
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setTotalAmount();
            }
        });
        mCheckBoxCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setTotalAmount();
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

    @OnClick({R.id.pay_next_tv, R.id.pay_time_month_tv, R.id.pay_time_quarter_tv, R.id.pay_time_year_tv,
                R.id.pay_car_month_tv, R.id.pay_car_quarter_tv, R.id.pay_car_year_tv})
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
                if (!mCheckBox.isChecked() && !mCheckBoxCar.isChecked()) {
                    ToastUtil.toastLong(getContext(), "物业费和停车费最少需要选择一个");
                    return;
                }
                String totalMoney = mTvTotalAmount.getText().toString();
                String wuyeMoney = mTvMoney.getText().toString();
                String carMoney = mTvMoneyCar.getText().toString();
                PayRequest payVo = new PayRequest();

                payVo.amount = totalMoney.replace(",", "");
                payVo.order_no = mPayInfo.orderno;

                String room = "";
                if (LeApplication.mAddressVo != null) {
                    room = LeApplication.mAddressVo.communityRawAddress;
                }
                String subject = "";
                String type = "";
                if (mCheckBox.isChecked() && mCheckBoxCar.isChecked()) {
                    subject = room + "|" + time + "," + time1;
                    type = "1";
                } else if (mCheckBox.isChecked() && !mCheckBoxCar.isChecked()) {
                    subject = room + "|" + room + time;
                    carMoney = "0";
                    monthCar = "0";
                    type = "2";
                } else if (!mCheckBox.isChecked() && mCheckBoxCar.isChecked()) {
                    subject = room + "|" + time1;
                    wuyeMoney = "0";
                    monthWuye = "0";
                    type = "3";
                }
                payVo.subject = subject;
                payVo.description = mPayInfo.description;

                PayBody paybody = new PayBody();
                EstateItem e = new EstateItem();

                ParkingPlaceItem p = new ParkingPlaceItem();
                e.n = monthWuye;
                e.m = wuyeMoney.replace(",", "");

                p.n = monthCar;
                p.m = carMoney.replace(",", "");
                paybody.e = e;
                paybody.p = p;
                paybody.i = LeApplication.mUserInfo.memberId;
                paybody.t = totalMoney.replace(",", "");
                payVo.body = new Gson().toJson(paybody);

                PayOtherInfo payOtherInfo = new PayOtherInfo();
                payOtherInfo.time = time;
                payOtherInfo.time1 = time1;
                payOtherInfo.type = type;
                payOtherInfo.amountShow = totalMoney;//这个是支付非传字段  在结果页显示使用
                payOtherInfo.communityRawAddress = mPayInfo.communityRawAddress;//这个是支付非传字段  在结果页显示使用

                PayManngeActivity.newIntent(getContext(), payVo, payOtherInfo);
                break;
            case R.id.pay_time_month_tv:
                time = "物业费-1个月";
                mTvByMonth.setSelected(true);
                mTvByQuarter.setSelected(false);
                mTvByYear.setSelected(false);
                if (mPayInfo != null && !TextUtils.isEmpty(mPayInfo.money)) {
                    double month = Long.valueOf(mPayInfo.money) / 12.00;
                    mTvMoney.setText(CommonUtil.formatAmountByAutomation(Math.ceil(month) + ""));
                }
                monthWuye = "1";
                setTotalAmount();
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
                monthWuye = "3";
                setTotalAmount();
                break;
            case R.id.pay_time_year_tv:
                time = "物业费-12个月";
                mTvByYear.setSelected(true);
                mTvByQuarter.setSelected(false);
                mTvByMonth.setSelected(false);
                if (mPayInfo != null && !TextUtils.isEmpty(mPayInfo.money)) {
                    mTvMoney.setText(CommonUtil.formatAmountByAutomation(mPayInfo.money));
                }
                monthWuye = "12";
                setTotalAmount();
                break;
            case R.id.pay_car_month_tv: //停车费 1个月
                time1 = "停车费-1个月";
                mTvCarByYear.setSelected(false);
                mTvCarByQuarter.setSelected(false);
                mTvCarByMonth.setSelected(true);
                if (mPayInfo != null && !TextUtils.isEmpty(mPayInfo.moneyCar)) {
                    double quarter = Long.valueOf(mPayInfo.moneyCar) / 12.00;
                    mTvMoneyCar.setText(CommonUtil.formatAmountByAutomation(Math.ceil(quarter) + ""));
                }
                monthCar = "1";
                setTotalAmount();
                break;
            case R.id.pay_car_quarter_tv: //停车费 3个月
                time1 = "停车费-3个月";
                mTvCarByYear.setSelected(false);
                mTvCarByQuarter.setSelected(true);
                mTvCarByMonth.setSelected(false);
                if (mPayInfo != null && !TextUtils.isEmpty(mPayInfo.moneyCar)) {
                    double quarter = Long.valueOf(mPayInfo.moneyCar) / 4.00;
                    mTvMoneyCar.setText(CommonUtil.formatAmountByAutomation(Math.ceil(quarter) + ""));
                }
                monthCar = "3";
                setTotalAmount();
                break;
            case R.id.pay_car_year_tv: //停车费 1年
                time1 = "停车费-12个月";
                mTvCarByYear.setSelected(true);
                mTvCarByQuarter.setSelected(false);
                mTvCarByMonth.setSelected(false);
                if (mPayInfo != null && !TextUtils.isEmpty(mPayInfo.moneyCar)) {
                    mTvMoneyCar.setText(CommonUtil.formatAmountByAutomation(mPayInfo.moneyCar));
                }
                monthCar = "12";
                setTotalAmount();
                break;
            default:
                break;
        }
    }

    public class PayBody implements Serializable {
        public EstateItem e;
        public ParkingPlaceItem p;
        public String i;
        public String t;
    }

    public class EstateItem implements Serializable{
        public String n;
        public String m;
    }

    public class ParkingPlaceItem implements Serializable {
        public String n;
        public String m;
    }

    private void setTotalAmount() {
        if (mPayInfo == null) {
            return;
        }
        String amount = mTvMoney.getText().toString().trim();
        String amountCar = mTvMoneyCar.getText().toString().trim();
        if (TextUtils.isEmpty(amount)) {
            amount = "0";
        }
        if (TextUtils.isEmpty(amountCar)) {
            amountCar = "0";
        }
        String amountTotal = "0";
        if (mCheckBox.isChecked() && mCheckBoxCar.isChecked()) {
            amountTotal = new BigDecimalBuilder(amount.replace(",", "")).add(amountCar.replace(",", "")).getValue().toString();
        } else if (mCheckBox.isChecked() && !mCheckBoxCar.isChecked()) {
            amountTotal = amount.replace(",", "");
        } else if (!mCheckBox.isChecked() && mCheckBoxCar.isChecked()) {
            amountTotal = amountCar.replace(",", "");
        }
        mTvTotalAmount.setText(CommonUtil.formatAmountByAutomation(CommonUtil.yuanToCent(amountTotal)));
    }


    public Dialog getPopupWindow() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_certi, null);
        TextView txtCer = contentView.findViewById(R.id.cer_tv);
        TextView close = contentView.findViewById(R.id.close);
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
        if (!TextUtils.isEmpty(mPayInfo.parkingplaceCutoffdate)) {
            mTvCarTime.setText(mPayInfo.parkingplaceCutoffdate);
            mRlCarDate.setVisibility(View.VISIBLE);
        } else {
            mRlCarDate.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mPayInfo.communityHouseArea)) {
            mTvSquareMeters.setText(String.format(getString(R.string.square_meters), CommonUtil.formatAmountByAutomation(mPayInfo.communityHouseArea)));
        }
        if (!TextUtils.isEmpty(mPayInfo.estateServiceUnitprice)) {
            mTvUnitMoney.setText(String.format(getString(R.string.by_square_meters), CommonUtil.formatAmountByAutomation(mPayInfo.estateServiceUnitprice)));
        }
        if (!TextUtils.isEmpty(mPayInfo.communityParkingPlacePrice) && !"0".equals(mPayInfo.communityParkingPlacePrice)) {
            mTvCarUnitMoney.setText(String.format(getString(R.string.square_meters_unit), CommonUtil.formatAmountByAutomation(mPayInfo.communityParkingPlacePrice)));
            mRlCarMoney.setVisibility(View.VISIBLE);
        } else {
            mRlCarMoney.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mPayInfo.moneyCar) && !"0".equals(mPayInfo.moneyCar)) {
            mCheckBoxCar.setChecked(true);
            mRlCarCount.setVisibility(View.VISIBLE);
            mLlCarCount.setVisibility(View.VISIBLE);
            mTvMoneyCar.setText(CommonUtil.formatAmountByAutomation(mPayInfo.moneyCar));
            onClick(mTvMoneyCar);
        } else {
            mCheckBoxCar.setChecked(false);
            mRlCarCount.setVisibility(View.GONE);
            mLlCarCount.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mPayInfo.money) && !"0".equals(mPayInfo.money)) {
            mCheckBox.setChecked(true);
            mLlWuyeCount.setVisibility(View.VISIBLE);
            mRlWuyeCount.setVisibility(View.VISIBLE);
            mTvMoney.setText(CommonUtil.formatAmountByAutomation(mPayInfo.money));
            onClick(mTvByYear);
        } else {
            // 停车费缴费数据
            mLlWuyeCount.setVisibility(View.GONE);
            mRlWuyeCount.setVisibility(View.GONE);
            mCheckBox.setChecked(false);
        }
        mTvByYear.setSelected(true);
        mTvCarByYear.setSelected(true);
        monthCar = "12";
        monthWuye = "12";
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
            if (mAddressVo == null) {
                mPayInfo = null;
                getPresenter().home(data.phone, addressVo.memberId);
            } else {
                if (mPayInfo == null || TextUtils.isEmpty(mAddressVo.memberId) || TextUtils.isEmpty(addressVo.memberId) || !addressVo.memberId.equals(mAddressVo.memberId)) {
                    mPayInfo = null;
                    getPresenter().home(data.phone, addressVo.memberId);
                }
            }
            mAddressVo = addressVo;
        }
        LeApplication.mAddressVo = mPropertyView.getCurrentAddress(addressData);
//        if (LeApplication.mAddressVo != null && !TextUtils.isEmpty(LeApplication.mAddressVo.communityId)) {
//            SharePrefrenceUtil.setString("config", "communityId", LeApplication.mAddressVo.communityId);
//        }
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
                mPayInfo = null;
                getPresenter().home(LeApplication.mUserInfo.phone, currentVo.memberId);
            }
        }
    }

    @Override
    public void onFailureAddress(String code, String msg) {
        LoadingDialog.hide();
    }
}
