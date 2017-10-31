package com.iot12369.easylifeandroid.mvp.contract;


import com.iot12369.easylifeandroid.model.AnnouncementData;
import com.iot12369.easylifeandroid.model.AnnouncementVo;
import com.sai.framework.mvp.MvpView;

/**
 * 功能说明： 公告
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/30
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class AnnouncementContract {

    public interface View extends MvpView {
        void onSuccessAnnouncement(AnnouncementData announcementData);
        void onErrorAnnouncement(String code, String msg);
    }
}
