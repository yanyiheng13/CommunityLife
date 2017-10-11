package com.iot12369.easylifeandroid.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.iot12369.easylifeandroid.R;
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

    private int channel = PaymaxSDK.CHANNEL_ALIPAY;

    @Override
    public int inflateId() {
        return R.layout.fragment_detail;
    }

    public static Fragment newIntent(Context context) {
        Fragment fragment = new PayDetailFragment();
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

    @OnClick({R.id.pay_detail_rl_bank, R.id.pay_detail_iv_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.pay_detail_rl_bank:
                if (mListener != null) {
                    mListener.OnPayDetailCardClick(1);
                }
                break;
            case R.id.pay_detail_iv_back:
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
