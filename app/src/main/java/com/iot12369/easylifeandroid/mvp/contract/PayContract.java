package com.iot12369.easylifeandroid.mvp.contract;

import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.PayData;
import com.iot12369.easylifeandroid.model.PayInfoData;
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
public class PayContract {

    public interface View extends MvpView {
        void onSuccessPayPre(PayInfoData payInfoData);
        void onFailurePayPre(String code, String msg);

        void onSuccessAddressList(AddressData addressData);
        void onFailureAddressList(String code, String msg);

        void onSuccessAddress(AddressVo addressVo);
        void onFailureAddress(String code, String msg);

    }
}
