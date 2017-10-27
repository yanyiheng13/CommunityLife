package com.iot12369.easylifeandroid.mvp;


import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.mvp.contract.LoginContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import java.io.Serializable;

import okhttp3.RequestBody;

/**
 * 功能说明： 登录
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoginPresent extends BasePresenter<Repository, LoginContract.View> {

    public void bindPhone(String phone, String code, String openid) {
        BindData bindData = new BindData();
        bindData.openid = openid;
        bindData.phone = phone;
        bindData.code = code;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(bindData));
        new RxHelper().view(getRootView()).load(getModel().getRemote().bindPhone(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<LoginData>>() {
            @Override
            public void onSuccess(String response, BaseBean<LoginData> result) {
                getRootView().onSuccessLogin(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureLogin(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }

    public void loginPhone(String phone, String code) {
        LoginPhone loginPhone = new LoginPhone();
        loginPhone.phone = phone;
        loginPhone.code = code;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(loginPhone));
        new RxHelper().view(getRootView()).load(getModel().getRemote().loginPhone(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<LoginData>>() {
            @Override
            public void onSuccess(String response, BaseBean<LoginData> result) {
                getRootView().onSuccessLogin(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureLogin(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }

    /**
     * 短信验证码
     */
    public void verificationCode(String phone) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "{\"phone\":\"" + phone + "\"}");
        new RxHelper().view(getRootView()).load(getModel().getRemote().verificationCode(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessCode(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureCode(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }

    /**
     * 语音短信验证码
     */
    public void verificationVoiceCode(String phone) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), "{\"phone\":\"" + phone + "\"}");
        new RxHelper().view(getRootView()).load(getModel().getRemote().verificationVoiceCode(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessVoice(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureCode(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }

    public class BindData implements Serializable {
        public String phone;
        public String code;
        public String openid;
    }

    public class LoginPhone implements Serializable {
        public String phone;
        public String code;
        public String os = "android";
    }


}
