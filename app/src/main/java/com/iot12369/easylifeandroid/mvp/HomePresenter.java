package com.iot12369.easylifeandroid.mvp;


import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.NoticeData;
import com.iot12369.easylifeandroid.model.UpdateData;
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

    public void lock(String memberid, String phone, String _comment, final String kind) {
        LockData data = new LockData();
        data.memberid = memberid;
        data.phone = phone;
        data._comment = _comment;
        data.kind = kind;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(data));
        new RxHelper().view(getRootView()).load(getModel().getRemote().lock(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessLock(result.data, kind);
            }
            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureLock(error, error, kind);
            }
        }).start();
    }

    /**
     * 物业列表接口
     */
    public void addressList(String phone) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().addressList(phone)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<AddressData>>() {
            @Override
            public void onSuccess(String response, BaseBean<AddressData> result) {
                getRootView().onSuccessAddressList(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureAddressList(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }

    /**
     * 首页三条公告请求
     */
    public void homeThreeNotice() {
        new RxHelper().view(getRootView()).load(getModel().getRemote().homeThreeNotice()).callBack(new RxHelper
                .CallBackAdapter<BaseBean<NoticeData>>() {
            @Override
            public void onSuccess(String response, BaseBean<NoticeData> result) {
                getRootView().onSuccessNoticeData(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureNoticeData(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }

    public void update() {
        new RxHelper().view(getRootView()).load(getModel().getRemote().update()).callBack(new RxHelper
                .CallBackAdapter<BaseBean<UpdateData>>() {
            @Override
            public void onSuccess(String response, BaseBean<UpdateData> result) {
                getRootView().onSuccessUpdateData(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureUpdateData(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }

    public class LockData implements Serializable {
        public String memberid;
        public String phone;
        public String _comment;
        public String kind;//1 是小区锁  2是单元门锁
    }
}
