package com.iot12369.easylifeandroid.mvp;


import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.mvp.contract.LoginContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

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

    public void login(String version, String appkey, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().login(version, appkey, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<LoginData>>(BaseBean.class) {
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
     * @param version
     * @param appkey
     * @param token
     */
    public void verificationCode(String version, String appkey, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().verificationCode(version, appkey, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>(BaseBean.class) {
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


}
