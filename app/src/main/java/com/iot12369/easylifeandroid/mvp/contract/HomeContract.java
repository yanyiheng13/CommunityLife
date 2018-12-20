package com.iot12369.easylifeandroid.mvp.contract;

import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.NoticeData;
import com.iot12369.easylifeandroid.model.UpdateData;
import com.sai.framework.mvp.MvpView;

/**
 * 功能说明： 首页即开锁页面
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class HomeContract {

    public interface View extends MvpView {
        void onSuccessLock(IsOkData isOkData, String kind);
        void onFailureLock(String code, String msg, String kind);

        void onSuccessAddressList(AddressData addressData);
        void onFailureAddressList(String code, String msg);

        void onSuccessNoticeData(NoticeData noticeData);
        void onFailureNoticeData(String code, String msg);

        void onSuccessUpdateData(UpdateData noticeData);
        void onFailureUpdateData(String code, String msg);

        void onSuccessMsgCount(IsOkData notReadMsgCount);
        void onFailureMsgCode(String code, String msg);

        void onSuccessUploadPhonBook(String isOkData);
        void onFailureUploadPhoneBook(String code, String msg);
    }
}
