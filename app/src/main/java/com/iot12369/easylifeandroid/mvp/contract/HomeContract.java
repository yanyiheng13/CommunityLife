package com.iot12369.easylifeandroid.mvp.contract;

import com.iot12369.easylifeandroid.model.HomeData;
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
        void onSuccessWork(HomeData homeData);
        void onFailureWork(String code, String msg);
    }
}
