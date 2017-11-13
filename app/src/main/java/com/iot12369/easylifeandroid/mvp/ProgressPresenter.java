package com.iot12369.easylifeandroid.mvp;


import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.RepairOrderDetailData;
import com.iot12369.easylifeandroid.mvp.contract.ProgressContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

import okhttp3.RequestBody;

/**
 * 功能说明： 维修 投诉进度
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class ProgressPresenter extends BasePresenter<Repository, ProgressContract.View> {

    public void repairOrderDetail(String workorder_sn) {
        RequestData data = new RequestData();
        data.workorder_sn = workorder_sn;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(data));
        new RxHelper().view(getRootView()).load(getModel().getRemote().repairOrderDetail(body)).application(LeApplication.mApplication).callBack(new RxHelper
                .CallBackAdapter<BaseBean<RepairOrderDetailData>>() {
            @Override
            public void onSuccess(String response, BaseBean<RepairOrderDetailData> result) {
                getRootView().onSuccessRepairOrderDetail(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureRepairOrderDetail(error, error);
            }
        }).start();
    }

    public void setRepairOrderStateMaintain(String workorder_sn, String workorder_state) {
        RequestData data = new RequestData();
        data.workorder_sn = workorder_sn;
        data.workorder_state = workorder_state;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(data));
        new RxHelper().view(getRootView()).load(getModel().getRemote().setMaintainState(body)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessRepairOrderMaintain(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureRepairOrderMaintain(error, error);
            }
        }).start();
    }

    public void setRepairOrderComplainState(String workorder_sn, String workorder_state) {
        RequestData data = new RequestData();
        data.workorder_sn = workorder_sn;
        data.workorder_state = workorder_state;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(data));
        new RxHelper().view(getRootView()).load(getModel().getRemote().setComplainState(body)).application(LeApplication.mApplication).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessRepairOrderComplain(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureRepairOrderComplain(error, error);
            }
        }).start();
    }

    public void repairOrderMaintainBack(String workorder_sn, String feedback_state) {
        RequestDataFeed data = new RequestDataFeed();
        data.workorder_sn = workorder_sn;
        data.feedback_state = feedback_state;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(data));
        new RxHelper().view(getRootView()).load(getModel().getRemote().repairOrderMaintainBack(body)).application(LeApplication.mApplication).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessRepairOrderMaintainBack(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureRepairOrderMaintainBack(error, error);
            }
        }).start();
    }

    public void repairOrderComplainBack(String workorder_sn, String feedback_state) {
        RequestDataFeed data = new RequestDataFeed();
        data.workorder_sn = workorder_sn;
        data.feedback_state = feedback_state;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(data));
        new RxHelper().view(getRootView()).load(getModel().getRemote().repairOrderComplainBack(body)).application(LeApplication.mApplication).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessRepairOrderComplainBack(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureRepairOrderComplainBack(error, error);
            }
        }).start();
    }

    public class RequestData {
        public String workorder_sn;
        public String workorder_state;
    }

    public class RequestDataFeed {
        public String workorder_sn;
        public String feedback_state;
    }

}
