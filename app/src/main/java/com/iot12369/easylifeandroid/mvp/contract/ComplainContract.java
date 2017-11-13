package com.iot12369.easylifeandroid.mvp.contract;

import com.iot12369.easylifeandroid.model.ComplainData;
import com.iot12369.easylifeandroid.model.ComplainVo;
import com.iot12369.easylifeandroid.model.MaintainData;
import com.sai.framework.mvp.MvpView;

/**
 * 功能说明： 个人业绩界面接口
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/5/12
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class ComplainContract {

    public interface View extends MvpView {
        void onSuccessComplain(MaintainData maintainData);
        void onErrorComplain(String code, String msg);
    }
}
