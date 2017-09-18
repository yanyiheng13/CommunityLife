package com.iot12369.easylifeandroid.mvp;


import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.mvp.contract.LoginContract;
import com.iot12369.easylifeandroid.mvp.contract.WeChatLoginContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

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
        new RxHelper().view(getRootView()).load(getModel().getRemote().wechatLogin(openid)).callBack(new RxHelper
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

    public void wechatRegister(String openid, String nickname,
                            String sex, String province,
                            String city, String country,
                            String headimgurl, String unionid,
                            String appid) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().wechatRegister(openid, nickname,
                sex, province, city, country, headimgurl, unionid, appid, "android")).callBack(new RxHelper
                .CallBackAdapter<BaseBean<LoginData>>() {
            @Override
            public void onSuccess(String response, BaseBean<LoginData> result) {
                getRootView().onSuccessWeChatLogin(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureWeChatRegister(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }


}
