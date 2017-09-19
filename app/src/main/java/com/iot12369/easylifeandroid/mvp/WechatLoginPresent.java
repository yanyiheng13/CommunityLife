package com.iot12369.easylifeandroid.mvp;


import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.WeChatUser;
import com.iot12369.easylifeandroid.mvp.contract.LoginContract;
import com.iot12369.easylifeandroid.mvp.contract.WeChatLoginContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import okhttp3.RequestBody;
import retrofit2.http.Field;

/**
 * 功能说明： 登录
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class WechatLoginPresent extends BasePresenter<Repository, WeChatLoginContract.View> {

    public void wechatLogin(String openid) {
//        String json = " {\"result\":}"
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "{\"openid\":\"" + openid + "\"}");
        new RxHelper().view(getRootView()).load(getModel().getRemote().wechatLogin(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<LoginData>>() {
            @Override
            public void onSuccess(String response, BaseBean<LoginData> result) {
                getRootView().onSuccessWeChatLogin(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureWeChatLogin(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }

    public void wechatRegister(WeChatUser weChatUser) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(weChatUser));
        new RxHelper().view(getRootView()).load(getModel().getRemote().wechatRegister(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<LoginData>>() {
            @Override
            public void onSuccess(String response, BaseBean<LoginData> result) {
                getRootView().onSuccessWeChatRegister(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureWeChatRegister(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }

    public void lock() {
//        String json = " {\"result\":}"
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "");
        new RxHelper().view(getRootView()).load(getModel().getRemote().lock()).callBack(new RxHelper
                .CallBackAdapter<BaseBean<LoginData>>() {
            @Override
            public void onSuccess(String response, BaseBean<LoginData> result) {
                getRootView().onSuccessWeChatLogin(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureWeChatLogin(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }



}
