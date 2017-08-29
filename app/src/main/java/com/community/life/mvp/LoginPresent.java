package com.community.life.mvp;


import com.community.life.LeApplication;
import com.community.life.model.BaseBean;
import com.community.life.model.LoginData;
import com.community.life.mvp.contract.LoginContract;
import com.community.life.net.Repository;
import com.community.life.net.rx.RxHelper;
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
}
