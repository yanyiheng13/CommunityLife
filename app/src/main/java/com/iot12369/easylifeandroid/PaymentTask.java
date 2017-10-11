package com.iot12369.easylifeandroid;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.iot12369.easylifeandroid.ui.BaseActivity;
import com.swwx.paymax.PaymaxCallback;
import com.swwx.paymax.PaymaxSDK;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PaymentTask extends AsyncTask<PaymentRequest, Void, String> {

    private static final String URL_CHAGE_URL = "https://www.paymax.cc/mock_merchant_server/v1/chargeOrders/product";

    BaseActivity mActivity;

    PaymaxCallback mPaymaxCallback;
    int type;

    public PaymentTask(BaseActivity activity, PaymaxCallback paymaxCallback, int type) {
        this.mActivity = activity;
        this.mPaymaxCallback = paymaxCallback;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(PaymentRequest... pr) {
        PaymentRequest paymentRequest = pr[0];
        String data = null;
        String json = new Gson().toJson(paymentRequest);
        Log.d("PaymaxSDK", "json=" + json);
        try {

            // 向 PaymaxSDK Server SDK请求数据
//            data = postJson(URL_CHAGE_URL, json);
            switch (type) {
                case PaymaxSDK.CHANNEL_ALIPAY:
                    data = "{\"amount\":0.01,\"amount_refunded\":0,\"amount_settle\":0,\"app\":\"app_7hqF2S6GYXET457i\",\"body\":\"测试商品Body\",\"channel\":\"alipay_app\",\"client_ip\":\"127.0.0.1\",\"credential\":{\"alipay_app\":{\"orderInfo\":\"_input_charset=\\\"utf-8\\\"&it_b_pay=\\\"2017-10-10 21:15:22\\\"&notify_url=\\\"https://www.paymax.cc/webservice/alipay_app\\\"&out_trade_no=\\\"ch_dcc7c48d5beca0f7a78741e5\\\"&partner=\\\"2088221494146238\\\"&payment_type=\\\"1\\\"&seller_id=\\\"471332824@qq.com\\\"&service=\\\"mobile.securitypay.pay\\\"&subject=\\\"测试商品007\\\"&total_fee=\\\"0.01\\\"&sign=\\\"yoKQCJqzmBe%2BtzWFKYCg98DfUQNDy6b1Hq8nP%2Fd4uESHSXiPr9q70WbOglxoG%2BjdS%2FbeBL0W%2BvfRLT7shqXZZKYBVszuwO56te05oAEyziEK7F%2BVg7%2FfvQxbfG8bpejyf%2B%2BjbhZT4lDBBqCi0ixFAMQFiJqjMFCjWT1uabP3UHk%3D\\\"&sign_type=\\\"RSA\\\"\"}},\"currency\":\"cny\",\"description\":\"\",\"extra\":{\"user_id\":\"100\"},\"id\":\"ch_dcc7c48d5beca0f7a78741e5\",\"liveMode\":false,\"order_no\":\"18cb9f0d305a414b9a864a026b0b6d21\",\"refunds\":[],\"reqSuccessFlag\":true,\"status\":\"PROCESSING\",\"subject\":\"测试商品007\",\"time_created\":1507637725000,\"time_expire\":1507641322206}";
                    break;
                case PaymaxSDK.CHANNEL_WX:
                    data = "{\"amount\":0.01,\"amount_refunded\":0,\"amount_settle\":0,\"app\":\"app_7hqF2S6GYXET457i\",\"body\":\"测试商品Body\",\"channel\":\"wechat_app\",\"client_ip\":\"127.0.0.1\",\"credential\":{\"wechat_app\":{\"sign\":\"1280B0753C456D2091EA5E112D94DC54\",\"timestamp\":1507643422,\"noncestr\":\"9k7f9cn01ktfhwldn1q5wtab8i3994rq\",\"partnerid\":\"1324016301\",\"prepayid\":\"wx2017101021502215b2c808860078957778\",\"package\":\"Sign=WXPay\",\"appid\":\"wx5269eef08886e3d5\"}},\"currency\":\"cny\",\"description\":\"\",\"extra\":{\"user_id\":\"100\"},\"id\":\"ch_810cb49faa0016e4545cf260\",\"liveMode\":false,\"order_no\":\"37ce94cf351b47b39e943c9ee4569b4f\",\"refunds\":[],\"reqSuccessFlag\":true,\"status\":\"PROCESSING\",\"subject\":\"测试商品007\",\"time_created\":1507643422000,\"time_expire\":1507647004883}";
                    break;
                case PaymaxSDK.CHANNEL_LKL:
                    data = "{\"amount\":0.01,\"amount_refunded\":0,\"amount_settle\":0,\"app\":\"app_7hqF2S6GYXET457i\"," +
                            "\"body\":\"测试商品Body\",\"channel\":\"lakala_app\",\"client_ip\":\"127.0.0.1\",\"credential\":" +
                            "{\"lakala_app\":{\"token\":\"20171010201710100067638019\",\"merchantOrderNo\":\"ch_533c6d8826961672f549121d\"," +
                            "\"totalAmount\":\"1\",\"merchantName\":\"Paymax 联调演示账号\",\"merchantId\":\"888055015200001\"," +
                            "\"orderTime\":\"20171010\",\"merchantUserNo\":\"100\"}},\"currency\":\"cny\",\"description\":\"\"," +
                            "\"extra\":{\"user_id\":\"100\"},\"id\":\"ch_533c6d8826961672f549121d\",\"liveMode\":false," +
                            "\"order_no\":\"c17d129b702d415394632a0cac148a02\",\"refunds\":[],\"reqSuccessFlag\":true,\"status\":\"PROCESSING\"," +
                            "\"subject\":\"测试商品007\",\"time_created\":1507614033000,\"time_expire\":1507617628634}";
                    break;
            }

            Log.d("PaymaxSDK", "data=" + data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获得服务端的charge，调用 sdk。
     */
    @Override
    protected void onPostExecute(String data) {
        if (null == data || data.length() == 0) {
            Snackbar.make(mActivity.findViewById(android.R.id.content), "no data", Snackbar.LENGTH_LONG)
                    .setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
            return;
        }


        PaymaxSDK.pay(mActivity, data, mPaymaxCallback);
    }

    private String postJson(String url, String json) throws IOException {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);
        Request request = new Request.Builder().url(url).post(body).build();
        OkHttpClient client = new OkHttpClient();
//        client.setConnectTimeout(5, TimeUnit.SECONDS);
//        client.setReadTimeout(5, TimeUnit.SECONDS);
        Response response = client.newCall(request).execute();
        Log.d("PaymaxSDK", "response code = " + response.code());
        return response.code() == 200 ? response.body().string() : null;

    }

}



