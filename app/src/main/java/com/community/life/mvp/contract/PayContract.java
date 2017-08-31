package com.community.life.mvp.contract;

import com.community.life.model.HomeData;
import com.community.life.model.PayInfoData;
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
        void onSuccessPay(PayInfoData payInfoData);
        void onFailurePay(String code, String msg);
    }
}
