package com.community.life.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.community.life.R;
import com.community.life.ui.BaseFragment;
import com.community.life.ui.view.IconTitleView;
import com.community.life.ui.view.PropertyAddressView;

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
public class PayFragment extends BaseFragment {

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

    @Override
    public int inflateId() {
        return R.layout.fragment_pay;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitleView.setText(R.string.title_home).setImageResource(R.mipmap.nav_home);
        mPropertyView.goneIcon();
        mPropertyView.updateData();
        mTvByYear.setSelected(true);
        mTvName.setText("大卫");
        mTvSquareMeters.setText(String.format(getString(R.string.square_meters), "100"));
        mTvUnitMoney.setText(String.format(getString(R.string.by_square_meters), "2.0"));
        mTvTime.setText("2016-08-16");
    }


    @OnClick({R.id.pay_next_tv, R.id.pay_time_month_tv, R.id.pay_time_quarter_tv, R.id.pay_time_year_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            //下一步按钮
            case R.id.pay_next_tv:
                break;
            case R.id.pay_time_month_tv:
                mTvByMonth.setSelected(true);
                mTvByQuarter.setSelected(false);
                mTvByYear.setSelected(false);
                break;
            case R.id.pay_time_quarter_tv:
                mTvByQuarter.setSelected(true);
                mTvByMonth.setSelected(false);
                mTvByYear.setSelected(false);
                break;
            case R.id.pay_time_year_tv:
                mTvByYear.setSelected(true);
                mTvByQuarter.setSelected(false);
                mTvByMonth.setSelected(false);
                break;
            default:
                break;
        }
    }

}
