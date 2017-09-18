package com.iot12369.easylifeandroid.mvp;

import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.TransactionData;
import com.iot12369.easylifeandroid.mvp.contract.TransactionContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

/**
 * 功能说明： 缴费记录
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class TransactionPresenter extends BasePresenter<Repository, TransactionContract.View> {

    public void transaction(String version, String appkey, String token, String leparID, String currentPageNo, String pageSize) {
//        new RxHelper().view(getRootView()).load(getModel().getRemote().transaction(version, appkey, token, leparID, currentPageNo, pageSize)).callBack(new RxHelper
//                .CallBackAdapter<BaseBean<TransactionData>>(BaseBean.class) {
//            @Override
//            public void onSuccess(String response, BaseBean<TransactionData> result) {
//                getRootView().onSuccess(result.data);
//            }
//
//            @Override
//            public void onFailure(String error) {
//                super.onFailure(error);
//                getRootView().onFailure(error, error);
//            }
//        }).start();
    }

}
