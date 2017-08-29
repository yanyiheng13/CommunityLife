//package com.community.life.mvp;
//
//import com.le.jro2o.home.model.TeamPerformanceData;
//import com.le.jro2o.home.mvp.contract.TeamWorkContract;
//import com.le.jro2o.net.Repository;
//import com.sai.framework.mvp.BasePresenter;
//import com.le.jro2o.net.rx.RxHelper;
//
///**
// * 功能说明： 个人业绩
// *
// * @author: 闫毅恒
// * @email： yanyiheng@le.com
// * @version: 1.0
// * @date: 2017/5/12
// * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
// */
//public class TeamWorkPresenter extends BasePresenter<Repository, TeamWorkContract.View> {
//
//    public void teamWorkList(String version, String appkey, String token, String type, String month, String pageSize, String currentPageNo) {
//        new RxHelper().view(getRootView()).load(getModel().getRemote().teamWorkList(version, appkey, token, type, month, pageSize, currentPageNo)).callBack(new RxHelper
//                .CallBackAdapter<TeamPerformanceData>(TeamPerformanceData.class) {
//
//            @Override
//            public void onSuccessPerson(String response, TeamPerformanceData performanceData) {
//                getRootView().onSuccessWork(performanceData);
//            }
//
//            @Override
//            public void onFailurePerson(String error) {
//                super.onFailurePerson(error);
//                getRootView().onErrorComplain(error, error);
//            }
//        }).start();
//    }
//}
