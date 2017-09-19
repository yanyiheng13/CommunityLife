package com.iot12369.easylifeandroid.mvp;

import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.PersonData;
import com.iot12369.easylifeandroid.mvp.contract.PersonInfoContract;
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
}
