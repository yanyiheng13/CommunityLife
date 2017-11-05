package com.iot12369.easylifeandroid.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.ui.behavior.OnPayTypeEventListener;
import com.swwx.paymax.PaymaxSDK;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by qingqingli on 2017/10/10.
 */

public class PayTypeFragment extends LePayFragment {
    private OnPayTypeEventListener mListener;
    @BindView(R.id.ibWechat)
    ImageButton ibWechat;
    @BindView(R.id.ibAlipay)
    ImageButton ibAlipay;
    @BindView(R.id.ibLKL)
    ImageButton ibLKL;

    protected double amount = 0.0;
    protected String userid = "";
    protected long time_expire ;

    private int channel = PaymaxSDK.CHANNEL_ALIPAY;

    @Override
    public int inflateId() {
        return R.layout.fragment_pay_type;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnPayTypeEventListener)activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            channel = getArguments().getInt("channel");
        } else {
            channel = savedInstanceState.getInt("channel");
        }
        syncContentViewHeight();
//        channel = PaymaxSDK.CHANNEL_ALIPAY;
        if (channel == PaymaxSDK.CHANNEL_ALIPAY) {
            ibAlipay.setBackgroundResource(R.drawable.selected);
            ibWechat.setBackgroundResource(R.drawable.unselected);
            ibLKL.setBackgroundResource(R.drawable.unselected);
        } else if (channel == PaymaxSDK.CHANNEL_WX) {
            ibAlipay.setBackgroundResource(R.drawable.unselected);
            ibWechat.setBackgroundResource(R.drawable.selected);
            ibLKL.setBackgroundResource(R.drawable.unselected);
        } else {
            ibAlipay.setBackgroundResource(R.drawable.unselected);
            ibWechat.setBackgroundResource(R.drawable.unselected);
            ibLKL.setBackgroundResource(R.drawable.selected);
        }

    }

    public static Fragment onNewIntent(int channel) {
        Fragment fragment = new PayTypeFragment();
        Bundle b = new Bundle();
        b.putInt("channel", channel);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("channel", channel);
    }

    @OnClick({R.id.linearLayoutCenter3, R.id.linearLayoutCenter2, R.id.linearLayoutCenter4, R.id.pay_card_rl_back})
    public void onChannelClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayoutCenter3:
                channel = PaymaxSDK.CHANNEL_ALIPAY;
                ibAlipay.setBackgroundResource(R.drawable.selected);
                ibWechat.setBackgroundResource(R.drawable.unselected);
                ibLKL.setBackgroundResource(R.drawable.unselected);
                if (mListener != null) {
                    mListener.OnPayTypeSelected(channel);
                }
                break;

            case R.id.linearLayoutCenter2:
                channel = PaymaxSDK.CHANNEL_WX;
                ibAlipay.setBackgroundResource(R.drawable.unselected);
                ibWechat.setBackgroundResource(R.drawable.selected);
                ibLKL.setBackgroundResource(R.drawable.unselected);
                if (mListener != null) {
                    mListener.OnPayTypeSelected(channel);
                }
                break;

            case R.id.linearLayoutCenter4:
                channel = PaymaxSDK.CHANNEL_LKL;
                ibAlipay.setBackgroundResource(R.drawable.unselected);
                ibWechat.setBackgroundResource(R.drawable.unselected);
                ibLKL.setBackgroundResource(R.drawable.selected);
                if (mListener != null) {
                    mListener.OnPayTypeSelected(channel);
                }
                break;
            case R.id.pay_card_rl_back:
                if(mListener != null) {
                    mListener.OnPaySelectBack();
                }
                break;
            default:
                break;
        }

    }
}
