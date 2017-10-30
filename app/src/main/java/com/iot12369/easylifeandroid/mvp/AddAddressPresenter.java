package com.iot12369.easylifeandroid.mvp;


import com.google.gson.Gson;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.mvp.contract.HomeContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import java.io.Serializable;

import okhttp3.RequestBody;

/**
 * 功能说明： 账号认证【添加物业地址】
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/10/30
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class AddAddressPresenter extends BasePresenter<Repository, HomeContract.View> {

    public void addAddress(String openid, String memberid, String phone, String memberName, String memberIdCard, String communityName, String communityRawAddress) {
        RequestData data = new RequestData();
        data.openid = openid;
        data.memberid = memberid;
        data.phone = phone;
        data.memberName = memberName;
        data.memberIdCard = memberIdCard;
        data.communityName = communityName;
        data.communityName = communityName;
        data.communityRawAddress = communityRawAddress;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(data));
        new RxHelper().view(getRootView()).load(getModel().getRemote().addAddress(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessLock(result.data);
            }
//
            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureLock(error, error);
            }
        }).start();
    }

    public class RequestData implements Serializable {
        public String openid;
        public String memberid;
        public String phone;
        public String memberName;
        public String memberIdCard;
        public String communityName;
        public String communityRawAddress;
    }
}
