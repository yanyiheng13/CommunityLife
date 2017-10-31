package com.iot12369.easylifeandroid.mvp;

import com.iot12369.easylifeandroid.model.AnnouncementData;
import com.iot12369.easylifeandroid.model.AnnouncementVo;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.mvp.contract.AnnouncementContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.sai.framework.mvp.BasePresenter;

/**
 * 功能说明： 公告列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/30
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class AnnouncementPresenter extends BasePresenter<Repository, AnnouncementContract.View> {

    public void announcementSystem(String start, String  length) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().announcementSystem(start, length)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<AnnouncementData>>() {

            @Override
            public void onSuccess(String response, BaseBean<AnnouncementData> result) {
                getRootView().onSuccessAnnouncement(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onErrorAnnouncement(error, error);
            }
        }).start();
    }

    public void announcementCommunity(String start, String length, String phone) {
        new RxHelper().view(getRootView()).load(getModel().getRemote().announcementCommunity(start, length, phone)).callBack(new RxHelper
                .CallBackAdapter<BaseBean<AnnouncementData>>() {

            @Override
            public void onSuccess(String response, BaseBean<AnnouncementData> result) {
                getRootView().onSuccessAnnouncement(result.data);
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onErrorAnnouncement(error, error);
            }
        }).start();
    }
}
