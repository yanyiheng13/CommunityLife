package com.community.life.mvp;


import com.community.life.model.BaseBean;
import com.community.life.model.ComplainProgressData;
import com.community.life.model.IsOkData;
import com.community.life.model.MaintainProgressData;
import com.community.life.mvp.contract.ProgressContract;
import com.community.life.net.Repository;
import com.community.life.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

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

    public void maintainProgress(String version) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().maintainProgress(version, null)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<MaintainProgressData>>(BaseBean.class) {
            @Override
            public void onSuccess(String response, BaseBean<MaintainProgressData> result) {
                getRootView().onSuccessMaintain(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureMaintain(error, error);
            }
        }).start();
    }

    public void complainProgress(String version) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().complainProgress(version, null)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<ComplainProgressData>>(BaseBean.class) {
            @Override
            public void onSuccess(String response, BaseBean<ComplainProgressData> result) {
                getRootView().onSuccessComplain(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureComplain(error, error);
            }
        }).start();
    }

    public void noSolve(String version) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().noSolve(version)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>(BaseBean.class) {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessNoSolve(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureNoSolve(error, error);
            }
        }).start();
    }

    public void statistic(String version) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().statistic(version, "", "")).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>(BaseBean.class) {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onSuccessSatisfaction(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureSatisfaction(error, error);
            }
        }).start();
    }

}
