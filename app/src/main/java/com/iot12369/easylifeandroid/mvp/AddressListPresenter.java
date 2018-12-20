package com.iot12369.easylifeandroid.mvp;


import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.RequestData;
import com.iot12369.easylifeandroid.mvp.contract.AddressListContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import okhttp3.RequestBody;

/**
 * 功能说明： 地址列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class AddressListPresenter extends BasePresenter<Repository, AddressListContract.View> {
//
//    public void addressList(String version, String appkey, String token) {
//        new RxHelper().view(getRootView()).load(getModel().getRemote().addressList(version, appkey, token)).callBack(new RxHelper
//                .CallBackAdapter<BaseBean<AddressVo>>() {
//            @Override
//            public void onSuccess(String response, BaseBean<AddressVo> result) {
//                getRootView().onSuccessAddress(result.data);
//            }
////
//            @Override
//            public void onFailure(String error) {
//                super.onFailure(error);
//                getRootView().onFailureAddress(error, error);
//            }
//        }).start();
//    }

    /**
     * 设置物业列表接口
     */
    public void setDefaultAdress(String memerdId, String phone) {
        RequestData requestData = new RequestData();
        requestData.memberid = memerdId;
        requestData.phone = phone;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(requestData));
        new RxHelper().view(getRootView()).load(getModel().getRemote().setDefaultAdress(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<AddressVo>>() {
            @Override
            public void onSuccess(String response, BaseBean<AddressVo> result) {
                getRootView().onSuccessAddress(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureAddress(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }
}
