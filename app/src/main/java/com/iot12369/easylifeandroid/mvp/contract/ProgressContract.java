package com.iot12369.easylifeandroid.mvp.contract;

import com.iot12369.easylifeandroid.model.ComplainProgressData;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.RepairOrderDetailData;
import com.sai.framework.mvp.MvpView;

/**
 * 功能说明：个人信息页面的请求接口 包括店长身份的店铺列表和店长查看店员详情页更改店员所属店铺的接口
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/5/11
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class ProgressContract {

    public interface View extends MvpView {
        void onSuccessRepairOrderDetail(RepairOrderDetailData maintainBean);
        void onFailureRepairOrderDetail(String code, String msg);

        void onSuccessRepairOrderMaintain(IsOkData isOkData);
        void onFailureRepairOrderMaintain(String code, String msg);

        void onSuccessRepairOrderComplain(IsOkData isOkData);
        void onFailureRepairOrderComplain(String code, String msg);

        void onSuccessRepairOrderMaintainBack(IsOkData isOkData);
        void onFailureRepairOrderMaintainBack(String code, String msg);

        void onSuccessRepairOrderComplainBack(IsOkData isOkData);
        void onFailureRepairOrderComplainBack(String code, String msg);
    }
}
