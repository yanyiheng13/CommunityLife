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
public class WeChatLoginContract {

    public interface View extends MvpView {
        void onSuccessWeChatLogin(LoginData loginData);
        void onFailureWeChatLogin(String code, String msg);

        void onSuccessWeChatRegister(LoginData loginData);
        void onFailureWeChatRegister(String code, String msg);
    }
}
