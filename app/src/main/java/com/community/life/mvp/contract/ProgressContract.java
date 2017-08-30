package com.community.life.mvp.contract;

import com.community.life.model.ComplainData;
import com.community.life.model.ComplainProgressData;
import com.community.life.model.IsOkData;
import com.community.life.model.MaintainData;
import com.community.life.model.MaintainProgressData;
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
        void onSuccessMaintain(MaintainProgressData maintainBean);
        void onFailureMaintain(String code, String msg);

        void onSuccessComplain(ComplainProgressData complainData);
        void onFailureComplain(String code, String msg);

        void onSuccessNoSolve(IsOkData isOkData);
        void onFailureNoSolve(String code, String msg);

        void onSuccessSatisfaction(IsOkData isOkData);
        void onFailureSatisfaction(String code, String msg);
    }
}
