package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.PayResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author YiHeng Yan
 * @Description
 * @Email yanyiheng86@163.com
 * @date 2017/11/7 20:34
 */

public class PayDetailActivity extends BaseActivity {
    @BindView(R.id.rl_num)
    RelativeLayout mRlNum;
    @BindView(R.id.rl_goods_name)
    RelativeLayout mRlName;
    @BindView(R.id.rl_address)
    RelativeLayout mRlAddress;
    @BindView(R.id.rl_time)
    RelativeLayout mRlTime;

    @BindView(R.id.pay_detail_status_icon)
    ImageView mImageStatus;
    @BindView(R.id.pay_detail_status_tv)
    TextView mTvStatus;
    @BindView(R.id.pay_detail_amount_tv)
    TextView mTvAmount;
    @BindView(R.id.tv_error_msg)
    TextView mTvErrorMsg;

    @BindView(R.id.transaction_item_num)
    TextView mTvNum;
    @BindView(R.id.transaction_item_goods_name)
    TextView mTvGoodsNmae;
    @BindView(R.id.transaction_item_address)
    TextView mTvAddress;
    @BindView(R.id.transaction_item_time)
    TextView mTvTime;

    public PayResult mPayResult;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mPayResult = (PayResult) getIntent().getSerializableExtra("result");
        } else {
            mPayResult = (PayResult) savedInstanceState.getSerializable("result");
        }
        setContentView(R.layout.activity_layout_paydetail);
        ButterKnife.bind(this);
        if ("1".equals(mPayResult.result)) {//支付成功
            mTvErrorMsg.setVisibility(View.GONE);
            mTvStatus.setText("该订单已支付成功");
            mTvNum.setText(mPayResult.orderNum);
            mTvGoodsNmae.setText(mPayResult.orderName);
            mTvAddress.setText(mPayResult.orderAddress);
            mTvTime.setText(mPayResult.orderTime);
            mTvAmount.setText("￥" + mPayResult.orderAmount);
        } else {
            mRlNum.setVisibility(View.GONE);
            mRlName.setVisibility(View.GONE);
            mRlAddress.setVisibility(View.INVISIBLE);
            mRlTime.setVisibility(View.INVISIBLE);
            mTvStatus.setText("抱歉，支付失败");
            mTvStatus.setTextColor(0xFFEC4800);
            mTvErrorMsg.setText(mPayResult.message);
            mImageStatus.setImageResource(R.mipmap.pay_failure);
        }
    }

    public static void newIntent(Context context, PayResult payResult) {
        Intent intent = new Intent(context, PayDetailActivity.class);
        intent.putExtra("result", payResult);
        context.startActivity(intent);
    }

    @OnClick({R.id.bt_left, R.id.bt_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_left:
                finish();
                break;
            case R.id.bt_right:
                TransactionRecordsActivity.newIntent(this);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("result", mPayResult);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
