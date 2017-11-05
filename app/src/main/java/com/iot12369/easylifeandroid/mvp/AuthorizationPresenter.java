package com.iot12369.easylifeandroid.mvp;

import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.FamilyData;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.RequestData;
import com.iot12369.easylifeandroid.mvp.contract.AnnouncementDetailContract;
import com.iot12369.easylifeandroid.mvp.contract.AuthorizationContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import okhttp3.RequestBody;

/**
 * @author YiHeng Yan
 * @Description
 * @Email yanyiheng86@163.com
 * @date 2017/11/5 19:42
 */

public class AuthorizationPresenter extends BasePresenter<Repository, AuthorizationContract.View> {
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
     * 家庭成员及租客列表
     */
    public void familyList(String phone, String memberid) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().familyList(phone, memberid)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<FamilyData>>() {
            @Override
            public void onSuccess(String response, BaseBean<FamilyData> result) {
                getRootView().onSuccessFamilyList(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureFamilyList(error, error);
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

    /**
     * 设置物业列表接口
     */
    public void removePeople(String origMemberid, String cancelAuthorizedMemberid) {
        RemoveData removeData = new RemoveData();
        removeData.phone = LeApplication.mUserInfo.phone;
        removeData.origMemberid = origMemberid;
        removeData.cancelAuthorizedMemberid = cancelAuthorizedMemberid;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(removeData));
        new RxHelper().view(getRootView()).load(getModel().getRemote().removePeople(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessRemove(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureRemove(error, error);
            }
        }).application(LeApplication.mApplication).start();
    }

    public class  RemoveData {
        public String phone;
        public String origMemberid;
        public String cancelAuthorizedMemberid;
    }
}
