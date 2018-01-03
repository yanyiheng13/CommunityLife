package com.iot12369.easylifeandroid.mvp;

import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.TransactionData;
import com.iot12369.easylifeandroid.mvp.contract.TransactionContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import okhttp3.RequestBody;

/**
 * 功能说明： 缴费记录
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class TransactionPresenter extends BasePresenter<Repository, TransactionContract.View> {

    public void transaction() {
        RequestData data = new RequestData();
        data.pageIndex = "1";
        data.pageSize = "30";
        data.payer = LeApplication.mUserInfo.phone;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(data));
        new RxHelper().view(getRootView()).load(getModel().getRemote().transaction(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<TransactionData>>() {
            @Override
            public void onSuccess(String response, BaseBean<TransactionData> result) {
                getRootView().onSuccess(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailure(error, error);
            }
        }).start();
    }

    public class RequestData {
        public String pageIndex;
        public String pageSize;
        public String payer;
    }

}
