package com.iot12369.easylifeandroid.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.PayOtherInfo;
import com.iot12369.easylifeandroid.ui.BaseFragment;
import com.iot12369.easylifeandroid.ui.behavior.OnPayDetailEventListener;
import com.iot12369.easylifeandroid.ui.behavior.OnPayToDetailEventListener;
import com.iot12369.easylifeandroid.ui.view.ProgressButton;
import com.swwx.paymax.PaymaxSDK;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by qingqingli on 2017/10/10.
 */

public class PayDetailFragment extends LePayFragment implements OnPayToDetailEventListener {
    private OnPayDetailEventListener mListener;
    @BindView(R.id.pay_detail_btn_pay)
    ProgressButton mBtnGo;
    @BindView(R.id.pay_detail_tv_bank)
    TextView mTvPayType;
    @BindView(R.id.pay_detail_tv_amount)
    TextView mTvPayMoney;

    @BindView(R.id.tvDesWuye)
    TextView mTvDesWuye;
    @BindView(R.id.tvDesCar)
    TextView mTvDesCar;
    @BindView(R.id.view_line_money1)
    View mLineOne;
    @BindView(R.id.view_line_money2)
    View mLineTwo;

    private int channel = PaymaxSDK.CHANNEL_ALIPAY;
    public PayOtherInfo mPayOtherInfo;

    @Override
    public int inflateId() {
        return R.layout.fragment_detail;
    }

    public static Fragment newIntent(Context context, PayOtherInfo payOtherInfo) {
        Fragment fragment = new PayDetailFragment();
        Bundle b = new Bundle();
        b.putSerializable("payOtherInfo", payOtherInfo);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnPayDetailEventListener)activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            mPayOtherInfo = (PayOtherInfo) getArguments().getSerializable("payOtherInfo");
        } else {
            mPayOtherInfo = (PayOtherInfo) savedInstanceState.getSerializable("payOtherInfo");
        }
        if ("1".equals(mPayOtherInfo.type)) {
            mTvDesWuye.setText(mPayOtherInfo.time);
            mTvDesCar.setText(mPayOtherInfo.time1);
        } else if ("2".equals(mPayOtherInfo.type)) {
            mTvDesWuye.setText(mPayOtherInfo.time);
            mTvDesCar.setVisibility(View.GONE);
            mLineTwo.setVisibility(View.GONE);
        } else if ("3".equals(mPayOtherInfo.type)) {
            mTvDesCar.setText(mPayOtherInfo.time1);
            mTvDesWuye.setVisibility(View.GONE);
            mLineOne.setVisibility(View.GONE);
        }
        mTvPayMoney.setText(mPayOtherInfo.amountShow + "元");
        mBtnGo.setCanClick();
        mBtnGo.setOnSubmitClickListener(new ProgressButton.OnSubmitClickListener() {
            @Override
            public void onSubmitClickListener() {
                if(mListener != null) {
                    mListener.onPay(channel);
                }
            }
        });
        switch (channel) {
            case PaymaxSDK.CHANNEL_ALIPAY:
                mTvPayType.setText("支付宝支付");
                break;
            case PaymaxSDK.CHANNEL_LKL:
                mTvPayType.setText("拉卡拉支付");
                break;
            case PaymaxSDK.CHANNEL_WX:
                mTvPayType.setText("微信支付");
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       outState.putSerializable("payOtherInfo", mPayOtherInfo);
    }

    @OnClick({R.id.pay_detail_rl_bank, R.id.close_rl})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.pay_detail_rl_bank:
                if (mListener != null) {
                    mListener.OnPayDetailCardClick(channel);
                }
                break;
            case R.id.close_rl:
                if (mListener != null) {
                    mListener.OnPayClose();
                }
                break;
        }
    }

    @Override
    public void OnPayTypeUpdate(int channel) {
        this.channel = channel;
        switch (channel) {
            case PaymaxSDK.CHANNEL_ALIPAY:
                mTvPayType.setText("支付宝支付");
                break;
            case PaymaxSDK.CHANNEL_LKL:
                mTvPayType.setText("拉卡拉支付");
                break;
            case PaymaxSDK.CHANNEL_WX:
                mTvPayType.setText("微信支付");
                break;
        }
    }
}
