package com.iot12369.easylifeandroid.mvp;


import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.PayInfoData;
import com.iot12369.easylifeandroid.mvp.contract.PayContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

/**
 * 功能说明： 首页即开锁页面
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class PayPresenter extends BasePresenter<Repository, PayContract.View> {

    public void home(String version, String appkey, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().home(version, appkey, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<PayInfoData>>() {
            @Override
            public void onSuccess(String response, BaseBean<PayInfoData> result) {
                getRootView().onSuccessPay(result.data);
            }
//
            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailurePay(error, error);
            }
        }).start();
    }
}
