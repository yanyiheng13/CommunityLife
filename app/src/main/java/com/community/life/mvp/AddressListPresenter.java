package com.community.life.mvp;


import com.community.life.model.AddressData;
import com.community.life.model.BaseBean;
import com.community.life.mvp.contract.AddressListContract;
import com.community.life.net.Repository;
import com.community.life.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

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

    public void addressList(String version, String appkey, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().addressList(version, appkey, token)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<AddressData>>(BaseBean.class) {
            @Override
            public void onSuccess(String response, BaseBean<AddressData> result) {
                getRootView().onSuccessAddress(result.data);
            }
//
            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureAddress(error, error);
            }
        }).start();
    }
}
