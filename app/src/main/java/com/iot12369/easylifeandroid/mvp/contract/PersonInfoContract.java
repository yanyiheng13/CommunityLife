package com.iot12369.easylifeandroid.mvp.contract;

import com.iot12369.easylifeandroid.model.PersonData;
import com.sai.framework.mvp.MvpView;

/**
 * 功能说明： 个人信息
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/5/11
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class PersonInfoContract {

    public interface View extends MvpView {
        void onSuccessPerson(PersonData data);
        void onFailurePerson(String code, String msg);
    }
}
