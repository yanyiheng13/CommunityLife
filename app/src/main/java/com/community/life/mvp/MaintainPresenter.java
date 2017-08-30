package com.community.life.mvp;


import com.community.life.model.BaseBean;
import com.community.life.model.MaintainData;
import com.community.life.mvp.contract.MaintainContract;
import com.community.life.net.Repository;
import com.community.life.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

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

    public void maintain(String version, String appkey, String token) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().maintain(version, appkey, token, "", "")).callBack(new RxHelper
                .CallBackAdapter<BaseBean<MaintainData>>(BaseBean.class) {
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

}
