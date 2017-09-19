package com.iot12369.easylifeandroid.mvp;


import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.HomeData;
import com.iot12369.easylifeandroid.mvp.contract.HomeContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

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

    public void lock() {
        new RxHelper().view(getRootView()).load(getModel().getRemote().lock()).callBack(new RxHelper
                .CallBackAdapter<BaseBean<HomeData>>() {
            @Override
            public void onSuccess(String response, BaseBean<HomeData> result) {
                getRootView().onSuccessWork(result.data);
            }
//
            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onFailureWork(error, error);
            }
        }).start();
    }
}
