package com.iot12369.easylifeandroid.mvp;

import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.PayData;
import com.iot12369.easylifeandroid.model.PayRequest;
import com.iot12369.easylifeandroid.mvp.contract.PayContract;
import com.iot12369.easylifeandroid.mvp.contract.ToPayContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import okhttp3.RequestBody;

/**
 * @author YiHeng Yan
 * @Description
 * @Email yanyiheng86@163.com
 * @date 2017/11/5 18:11
 */

public class ToPayPresenter extends BasePresenter<Repository, ToPayContract.View>  {
    /**
     * 物业列表接口
     */
    public void pay(PayRequest payRequest) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(payRequest));
        new RxHelper().view(getRootView()).load(getModel().getRemote().pay(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<PayData>>() {
            @Override
            public void onSuccess(String response, BaseBean<PayData> result) {
                getRootView().onSuccessPay(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailurePay(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }
}
