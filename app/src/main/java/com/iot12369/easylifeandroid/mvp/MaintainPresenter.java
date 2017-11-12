package com.iot12369.easylifeandroid.mvp;


import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.MaintainData;
import com.iot12369.easylifeandroid.model.MaintainVo;
import com.iot12369.easylifeandroid.mvp.contract.MaintainContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import okhttp3.RequestBody;

/**
 * 功能说明： 维修列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class MaintainPresenter extends BasePresenter<Repository, MaintainContract.View> {

    public void maintain(String phone, String pageIndex) {
        MaintainRequest data = new MaintainRequest();
        data.member_phone = phone;
        data.pageIndex = pageIndex;
        data.pageSize = "20";
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(data));
        new RxHelper().view(getRootView()).load(getModel().getRemote().maintain(body)).application(LeApplication.mApplication).callBack(new RxHelper
                .CallBackAdapter<BaseBean<MaintainData>>() {
            @Override
            public void onSuccess(String response, BaseBean<MaintainData> result) {
                getRootView().onSuccessMaintain(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureMaintain(error, error);
            }
        }).start();
    }

    public class MaintainRequest{
        //         "member_phone":"13836552100",
//                 "pageIndex":1,
//                 "pageSize":10
        public String pageSize;
        public String pageIndex;
        public String member_phone;
    }

}
