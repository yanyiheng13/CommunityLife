package com.iot12369.easylifeandroid.mvp;

import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.PersonData;
import com.iot12369.easylifeandroid.model.RequestData;
import com.iot12369.easylifeandroid.mvp.contract.PersonInfoContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import okhttp3.RequestBody;

/**
 * 功能说明： 个人信息
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class PersonInfoPresenter extends BasePresenter<Repository, PersonInfoContract.View> {

   public void personInfo(String version, String appkey, String token) {
       new RxHelper().view(getRootView()).load(getModel().getRemote().personInfo(version, appkey, token)).callBack(new RxHelper
               .CallBackAdapter<BaseBean<PersonData>>() {

           @Override
           public void onSuccess(String response, BaseBean<PersonData> data) {
               getRootView().onSuccessPerson(data.data);
           }

           @Override
           public void onFailure(String error) {
               super.onFailure(error);
               getRootView().onFailurePerson(error, error);
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
     * 设置物业列表接口
     */
    public void setDefaultAdress(String memerdId, String phone) {
        RequestData requestData = new RequestData();
        requestData.memberid = memerdId;
        requestData.phone = phone;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(requestData));
        new RxHelper().view(getRootView()).load(getModel().getRemote().setDefaultAdress(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<AddressVo>>() {
            @Override
            public void onSuccess(String response, BaseBean<AddressVo> result) {
                getRootView().onSuccessAddress(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureAddress(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }
}
