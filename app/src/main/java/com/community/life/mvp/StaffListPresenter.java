//package com.community.life.mvp;
//
//import com.le.jro2o.home.model.JudgeResult;
//import com.le.jro2o.home.model.StaffOfStoreListData;
//import com.le.jro2o.home.mvp.contract.StaffListContract;
//import com.le.jro2o.net.Repository;
//import com.sai.framework.mvp.BasePresenter;
//import com.le.jro2o.net.rx.RxHelper;
//
///**
// * 功能说明： 某一店铺下面的员工列表
// *
// * @author: 闫毅恒
// * @email： yanyiheng@le.com
// * @version: 1.0
// * @date: 2017/5/11
// * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
// */
//public class StaffListPresenter extends BasePresenter<Repository, StaffListContract.View> {
//
//    public void storePeopleList(String version, String appkey, String token, String leparID, String currentPageNo, String pageSize) {
//        new RxHelper().view(getRootView()).load(getModel().getRemote().storePeopleList(version, appkey, token, leparID, currentPageNo, pageSize)).callBack(new RxHelper
//                .CallBackAdapter<StaffOfStoreListData>(StaffOfStoreListData.class) {
//            @Override
//            public void onSuccessPerson(String response, StaffOfStoreListData result) {
//                getRootView().onSuccessStaffList(result);
//            }
//
//            @Override
//            public void onFailurePerson(String error) {
//                super.onFailurePerson(error);
//                getRootView().onFailureStaffList(error, error);
//            }
//        }).start();
//    }
//
//    public void maintain(String version, String appkey, String token, String leparID, String userID) {
//        new RxHelper().view(getRootView()).application(getContext().getApplicationContext()).load(getModel().getRemote().maintain(version, appkey, token, leparID, userID)).callBack(new RxHelper
//                .CallBackAdapter<JudgeResult>(JudgeResult.class) {
//            @Override
//            public void onSuccessPerson(String response, JudgeResult result) {
//                getRootView().onSuccessRemove(result);
//            }
//
//            @Override
//            public void onFailurePerson(String error) {
//                super.onFailurePerson(error);
//                getRootView().onFailureRemove(error, error);
//            }
//        }).start();
//    }
//
//}
