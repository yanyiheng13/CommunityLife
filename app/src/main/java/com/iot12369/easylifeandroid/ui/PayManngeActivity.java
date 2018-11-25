package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.PaymentRequest;
import com.iot12369.easylifeandroid.PaymentTask;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.PayData;
import com.iot12369.easylifeandroid.model.PayRequest;
import com.iot12369.easylifeandroid.mvp.ToPayPresenter;
import com.iot12369.easylifeandroid.mvp.contract.ToPayContract;
import com.iot12369.easylifeandroid.ui.behavior.OnPayDetailEventListener;
import com.iot12369.easylifeandroid.ui.behavior.OnPayToDetailEventListener;
import com.iot12369.easylifeandroid.ui.behavior.OnPayTypeEventListener;
import com.iot12369.easylifeandroid.ui.fragment.PayDetailFragment;
import com.iot12369.easylifeandroid.ui.fragment.PayTypeFragment;
import com.iot12369.easylifeandroid.util.ToastUtil;
import com.swwx.paymax.PayResult;
import com.swwx.paymax.PaymaxCallback;
import com.swwx.paymax.PaymaxSDK;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by qingqingli on 2017/10/10.
 */

public class PayManngeActivity extends BaseActivity<ToPayPresenter> implements ToPayContract.View, OnPayDetailEventListener, OnPayTypeEventListener, PaymaxCallback  {

    private Fragment mOldFragment;
    private String userid = "100";
    private long time_expire = 3600;
    private double amount = 0.01;

    private OnPayToDetailEventListener mListener;
    /**
     * 支付宝支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay_app";

    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wechat_app";

    /**
     * 微信支付渠道
     */
    protected static final String CHANNEL_LKL = "lakala_app";

    private PayRequest mPayRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mPayRequest = (PayRequest) getIntent().getSerializableExtra("payVo");
        } else {
            mPayRequest = (PayRequest) savedInstanceState.getSerializable("payVo");
        }
        setContentView(R.layout.activity_lepay_manager);
        setCurrentTab("1", PayDetailFragment.newIntent(this, mPayRequest.amountShow, mPayRequest.subject));
    }

    public static void newIntent(Context context, PayRequest payVo) {
        Intent intent = new Intent(context, PayManngeActivity.class);
        intent.putExtra("payVo", payVo);
        context.startActivity(intent);
    }

    @Override
    public void OnPayDetailCardClick(int type) {
        setCurrentTab("2", PayTypeFragment.onNewIntent(type));
    }

    @Override
    public void OnPayClose() {
//        setCurrentTab("2", PayTypeFragment.onNewIntent());
        OnPaySelectBack();
    }

    @Override
    public void OnPayTypeSelected(int type) {
//        setCurrentTab("2", PayTypeFragment.onNewIntent());
        onBackPressed();
        if (mListener != null) {
            mListener.OnPayTypeUpdate(type);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof PayDetailFragment) {
            mListener = (OnPayToDetailEventListener)fragment;
        }
    }

    @Override
    public void onPay(int channel) {
//        public String subject;
//        public String body;
//        public String description;
//        public String channel;
//        public String client_ip;
        mPayRequest.client_ip = "127.0.0.1";
        switch (channel) {
            case PaymaxSDK.CHANNEL_WX:
                mPayRequest.channel = CHANNEL_WECHAT;
//                new PaymentTask(PayManngeActivity.this, PayManngeActivity.this, PaymaxSDK.CHANNEL_WX).execute(new PaymentRequest(CHANNEL_WECHAT, amount, "测试商品007", "测试商品Body", userid,time_expire));
                break;

            case PaymaxSDK.CHANNEL_ALIPAY:
                mPayRequest.channel = CHANNEL_ALIPAY;
//                new PaymentTask(PayManngeActivity.this, PayManngeActivity.this, PaymaxSDK.CHANNEL_ALIPAY).execute(new PaymentRequest(CHANNEL_ALIPAY, amount, "测试商品007", "测试商品Body", userid,time_expire));
                break;

            case PaymaxSDK.CHANNEL_LKL: {
                mPayRequest.channel = CHANNEL_LKL;
//                new PaymentTask(PayManngeActivity.this, PayManngeActivity.this, PaymaxSDK.CHANNEL_LKL).execute(new PaymentRequest(CHANNEL_LKL, amount, "测试商品007", "测试商品Body", userid,time_expire));
            }
            break;
        }
        getPresenter().pay(mPayRequest);

    }

    @Override
    public void onPayFinished(final PayResult result) {
        String msg = "未知错误";
        final com.iot12369.easylifeandroid.model.PayResult payResult = new com.iot12369.easylifeandroid.model.PayResult();
        payResult.result = "2";
        switch (result.getCode()) {
            case PaymaxSDK.CODE_SUCCESS:
                msg = "支付成功";
                payResult.result = "1";
                break;
            case PaymaxSDK.CODE_ERROR_CHARGE_JSON:
                msg = "Json error.";
                break;

            case PaymaxSDK.CODE_FAIL_CANCEL:
                msg = "支付取消";
                break;

            case PaymaxSDK.CODE_ERROR_CHARGE_PARAMETER:
                msg = "appid error.";
                break;

            case PaymaxSDK.CODE_ERROR_WX_NOT_INSTALL:
                msg = "wx not install.";
                break;

            case PaymaxSDK.CODE_ERROR_WX_NOT_SUPPORT_PAY:
                msg = "ex not support pay.";
                break;

            case PaymaxSDK.CODE_ERROR_WX_UNKNOW:
                msg = "wechat failed.";
                break;

            case PaymaxSDK.CODE_ERROR_ALI_DEAL:
                msg = "alipay dealing.";
                break;

            case PaymaxSDK.CODE_ERROR_ALI_CONNECT:
                msg = "alipay network connection failed.";
                break;

            case PaymaxSDK.CODE_ERROR_CHANNEL:
                msg = "channel error.";
                break;

            case PaymaxSDK.CODE_ERROR_LAK_USER_NO_NULL:
                msg = "lklpay user no is null.";
                break;

        }

        final String message = msg;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result.getCode() == PaymaxSDK.CODE_FAIL_CANCEL) {
                    ToastUtil.toast(PayManngeActivity.this, message);
                    finish();
                } else {
                    Calendar c = Calendar.getInstance();//
                    int mYear = c.get(Calendar.YEAR); // 获取当前年份
                    int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
                    int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
                    int mHour = c.get(Calendar.HOUR_OF_DAY);//时
                    int mMinute = c.get(Calendar.MINUTE);//分
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(mYear);
                    stringBuilder.append("-");
                    stringBuilder.append(mMonth);
                    stringBuilder.append("-");
                    stringBuilder.append(mDay);
                    stringBuilder.append(" ");
                    stringBuilder.append(mHour);
                    stringBuilder.append(":");
                    stringBuilder.append(mMinute);
                    payResult.message = message;
                    payResult.orderAddress = mPayRequest.communityRawAddress;
                    payResult.orderAmount = mPayRequest.amountShow;
                    payResult.orderName = mPayRequest.subject;
                    payResult.orderNum = mPayRequest.order_no;
                    payResult.orderTime = stringBuilder.toString();
                    PayDetailActivity.newIntent(PayManngeActivity.this, payResult);
                    finish();
                }

            }
        });

    }

    @Override
    public void OnPaySelectBack() {
//        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
//        if (backStackCount == 3) {
//            getSupportFragmentManager().popBackStackImmediate();
//            getSupportFragmentManager().popBackStack();
//        } else if (backStackCount == 2) {
//            getSupportFragmentManager().popBackStack();
//            mOldFragment = getSupportFragmentManager().findFragmentByTag("1");
//        } else if (backStackCount == 1) {
//            getSupportFragmentManager().popBackStack();
//            finish();
//        }
        onBackPressed();

    }

    /**
     * Fragment的切换libammsdk.jar
     */
    private void setCurrentTab(String tag, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment instanceof PayDetailFragment) {
            ft.setCustomAnimations(
                    R.anim.push_bottom_in, R.anim.push_bottom_out,
                    R.anim.pay_slide_left_in, R.anim.pay_slide_left_out);
        } else {
            ft.setCustomAnimations (
                    R.anim.pay_slide_right_in, R.anim.pay_slide_left_out,
                    R.anim.pay_slide_left_in, R.anim.pay_slide_right_out);
        }
        if (mOldFragment != null) {
            ft.hide(mOldFragment);
        }
        ft.add(R.id.pay_fragment_contain, fragment, tag);
        mOldFragment = fragment;
        ft.addToBackStack(tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
//        OnPaySelectBack();
//        super.onBackPressed();
//        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
//        if (backStackCount == 0) {
//            finish();
//        }
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCount > 0) {
            if (backStackCount == 1) {
                finish();
                return;
                }
            }
            getSupportFragmentManager().popBackStack();
            if (backStackCount != 1) {
                mOldFragment = getSupportFragmentManager().getFragments().get(backStackCount - 2);
            }
        }

    @Override
    public void onSuccessPay(PayData payData) {
        PaymaxSDK.pay(this, new Gson().toJson(payData), this);
    }

    @Override
    public void onFailurePay(String code, String msg) {

    }

    class FaceTask extends AsyncTask<FaceRequest, Void, String> {


        public FaceTask() {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(FaceRequest... pr) {
            FaceRequest faceRequest = pr[0];
            String data = null;
            String json = new Gson().toJson(faceRequest);
            Log.d("FaceRecoSDK", "json=" + json);
            try {

                // 向 PaymaxSDK Server SDK请求数据
                String url = String.format("https://www.paymax.cc/mock_merchant_server/v1/face/auth/%s/product", pr[0].uId);
                data = postJson(url, json);
                Log.d("FaceRecoSDK", "data=" + data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }


        @Override
        protected void onPostExecute(String data) {
            if (null == data || data.length() == 0) {
                Snackbar.make(findViewById(android.R.id.content), "no face data", Snackbar.LENGTH_LONG)
                        .setAction("Close", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
            } else if (getRtn(data)) {
                new PaymentTask(PayManngeActivity.this, PayManngeActivity.this, PaymaxSDK.CHANNEL_LKL).execute(new PaymentRequest(CHANNEL_LKL, amount, "测试商品007", "测试商品Body", userid,time_expire));
            } else {
//                Intent intent = new Intent(MainActivity.this, InputActivity.class);
//                intent.putExtra("amount", amount);
//                intent.putExtra("userid", userid);
//                intent.putExtra("time_expire", time_expire);
//
//                startActivity(intent);
            }
        }
    }

    /**
     * 获得返回结果码,来判断此用户是否通过人脸识别
     *
     * @param data
     * @return
     */
    private boolean getRtn(String data) {
        boolean flag = false;
        try {
            JSONObject jsonObject = new JSONObject(data);
            flag = jsonObject.optBoolean("authValid");
        } catch (Exception e) {
        }
        return flag;
    }


    class FaceRequest {
        String key;//商户key
        String merchantNo;//商户号
        String uId;//商户用户id， 在商户系统能唯一标示某个用户商户用户名

        public FaceRequest(String key, String merchantNo, String uId) {
            this.key = key;
            this.merchantNo = merchantNo;
            this.uId = uId;
        }
    }

    private String postJson(String url, String json) throws IOException {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);
        Request request = new Request.Builder().url(url).get().build();
        OkHttpClient client = new OkHttpClient();
//        client.setConnectTimeout(5, TimeUnit.SECONDS);
//        client.setReadTimeout(5, TimeUnit.SECONDS);
        Response response = client.newCall(request).execute();
        Log.d("PaymaxSDK", "response code = " + response.code());
        return response.body().string();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("payVo", mPayRequest);
    }
}
