package com.iot12369.easylifeandroid.mvp.contract;


import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.sai.framework.mvp.MvpView;

/**
 * 功能说明：登录
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoginContract {

    public interface View extends MvpView {
        void onSuccessLogin(LoginData loginData);
        void onFailureLogin(String code, String msg);

        void onSuccessCode(IsOkData isOkData);
        void onFailureCode(String code, String msg);

        void onSuccessVoice(IsOkData isOkData);
        void onFailureVoice(String code, String msg);
    }
}
