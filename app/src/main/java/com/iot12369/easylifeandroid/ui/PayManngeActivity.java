package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.iot12369.easylifeandroid.PaymentRequest;
import com.iot12369.easylifeandroid.PaymentTask;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.PayVo;
import com.iot12369.easylifeandroid.ui.behavior.OnPayDetailEventListener;
import com.iot12369.easylifeandroid.ui.behavior.OnPayToDetailEventListener;
import com.iot12369.easylifeandroid.ui.behavior.OnPayTypeEventListener;
import com.iot12369.easylifeandroid.ui.fragment.PayDetailFragment;
import com.iot12369.easylifeandroid.ui.fragment.PayTypeFragment;
import com.swwx.paymax.PayResult;
import com.swwx.paymax.PaymaxCallback;
import com.swwx.paymax.PaymaxSDK;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by qingqingli on 2017/10/10.
 */

public class PayManngeActivity extends BaseActivity implements OnPayDetailEventListener, OnPayTypeEventListener, PaymaxCallback  {

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

    private PayVo mPayVo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mPayVo = (PayVo) getIntent().getSerializableExtra("payVo");
        } else {
            mPayVo = (PayVo) savedInstanceState.getSerializable("payVo");
        }
        setContentView(R.layout.activity_lepay_manager);
        setCurrentTab("1", PayDetailFragment.newIntent(this));
    }

    public static void newIntent(Context context, PayVo payVo) {
        Intent intent = new Intent(context, PayManngeActivity.class);
        intent.putExtra("payVo", payVo);
        context.startActivity(intent);
    }

    @Override
    public void OnPayDetailCardClick(int type) {
        setCurrentTab("2", PayTypeFragment.onNewIntent());
    }

    @Override
    public void OnPayClose() {
//        setCurrentTab("2", PayTypeFragment.onNewIntent());
        OnPaySelectBack();
    }

    @Override
    public void OnPayTypeSelected(int type) {
//        setCurrentTab("2", PayTypeFragment.onNewIntent());
        getSupportFragmentManager().popBackStack();
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
        switch (channel) {
            case PaymaxSDK.CHANNEL_WX:

                new PaymentTask(PayManngeActivity.this, PayManngeActivity.this, PaymaxSDK.CHANNEL_WX).execute(new PaymentRequest(CHANNEL_WECHAT, amount, "测试商品007", "测试商品Body", userid,time_expire));
                break;

            case PaymaxSDK.CHANNEL_ALIPAY:
                new PaymentTask(PayManngeActivity.this, PayManngeActivity.this, PaymaxSDK.CHANNEL_ALIPAY).execute(new PaymentRequest(CHANNEL_ALIPAY, amount, "测试商品007", "测试商品Body", userid,time_expire));
                break;

            case PaymaxSDK.CHANNEL_LKL: {
//                new FaceTask().execute(new FaceRequest("123", "123", userid));
                new PaymentTask(PayManngeActivity.this, PayManngeActivity.this, PaymaxSDK.CHANNEL_LKL).execute(new PaymentRequest(CHANNEL_LKL, amount, "测试商品007", "测试商品Body", userid,time_expire));
            }
            break;
        }

    }

    @Override
    public void onPayFinished(PayResult result) {
        String msg = "Unknow";
        switch (result.getCode()) {
            case PaymaxSDK.CODE_SUCCESS:
                msg = "Complete, Success!.";
                break;
            case PaymaxSDK.CODE_ERROR_CHARGE_JSON:
                msg = "Json error.";
                break;

            case PaymaxSDK.CODE_FAIL_CANCEL:
                msg = "cancel pay.";
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
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
        finish();
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
        super.onBackPressed();
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCount == 0) {
            finish();
        }
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
        outState.putSerializable("payVo", mPayVo);
    }
}
