package com.iot12369.easylifeandroid.mvp;

import com.iot12369.easylifeandroid.model.AdData;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.mvp.contract.AdContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

/**
 * 功能说明： 个人信息
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class AdPresenter extends BasePresenter<Repository, AdContract.View> {

   public void getAdlist(String communityId) {
       new RxHelper().view(getRootView()).load(getModel().getRemote().getAdlist(communityId)).callBack(new RxHelper
               .CallBackAdapter<BaseBean<AdData>>() {

           @Override
           public void onSuccess(String response, BaseBean<AdData> data) {
               getRootView().onSuccessAd(data.data);
           }

           @Override
           public void onFailure(String error) {
               super.onFailure(error);
               getRootView().onFailureAd(error, error);
           }
       }).start();
   }

}
