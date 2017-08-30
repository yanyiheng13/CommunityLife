package com.community.life.mvp;

import com.community.life.model.BaseBean;
import com.community.life.model.ComplainData;
import com.community.life.mvp.contract.ComplainContract;
import com.community.life.net.Repository;
import com.community.life.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

/**
 * 功能说明： 投诉列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class ComplainPresenter extends BasePresenter<Repository, ComplainContract.View> {

   public void personWorkList(String version, String appkey, String token, String month, String pageSize, String currentPageNo) {
       new RxHelper().view(getRootView()).load(getModel().getRemote().personWorkList(version, appkey, token, month, pageSize, currentPageNo)).callBack(new RxHelper
               .CallBackAdapter<BaseBean<ComplainData>>(BaseBean.class) {

           @Override
           public void onSuccess(String response, BaseBean<ComplainData> data) {
               getRootView().onSuccessComplain(data.data);
           }

           @Override
           public void onFailure(String error) {
               super.onFailure(error);
               getRootView().onErrorComplain(error, error);
           }
       }).start();
   }
}