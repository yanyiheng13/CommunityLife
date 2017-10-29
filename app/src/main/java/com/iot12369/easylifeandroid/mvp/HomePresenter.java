package com.iot12369.easylifeandroid.mvp;


import com.google.gson.Gson;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.HomeData;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.mvp.contract.HomeContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import java.io.Serializable;

import okhttp3.RequestBody;

/**
 * 功能说明： 首页即开锁页面
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class HomePresenter extends BasePresenter<Repository, HomeContract.View> {

    public void lock(String memberid, String phone, String _comment) {
        LockData data = new LockData();
        data.memberid = memberid;
        data.phone = phone;
        data._comment = _comment;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(data));
        
        new RxHelper().view(getRootView()).load(getModel().getRemote().lock(body)).callBack(new RxHelper
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

    public class LockData implements Serializable {
        public String memberid;
        public String phone;
        public String _comment;
    }
}
