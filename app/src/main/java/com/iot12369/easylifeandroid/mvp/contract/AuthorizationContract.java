package com.iot12369.easylifeandroid.mvp.contract;

import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.FamilyData;
import com.iot12369.easylifeandroid.model.IsOkData;
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
public class AuthorizationContract {

    public interface View extends MvpView {
        void onSuccessRemove(IsOkData data);
        void onFailureRemove(String code, String msg);

        void onSuccessAddressList(AddressData addressData);
        void onFailureAddressList(String code, String msg);

        void onSuccessAddress(AddressVo addressVo);
        void onFailureAddress(String code, String msg);

        void onSuccessFamilyList(FamilyData familyData);
        void onFailureFamilyList(String code, String msg);
    }
}
