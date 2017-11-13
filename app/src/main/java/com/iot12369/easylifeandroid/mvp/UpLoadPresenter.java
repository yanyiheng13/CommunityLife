package com.iot12369.easylifeandroid.mvp;


import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.model.BaseBean;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.mvp.contract.UploadContract;
import com.iot12369.easylifeandroid.net.Repository;
import com.iot12369.easylifeandroid.net.rx.RxHelper;
import com.luck.picture.lib.entity.LocalMedia;
import com.sai.framework.mvp.BasePresenter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 功能说明： 首页即开锁页面
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class UpLoadPresenter extends BasePresenter<Repository, UploadContract.View> {

    /**
     *
     * @param mediaList
     */
    public void upMaintainRequireOrder(String member_phone,
                                       String community_name,
                                       String estate_address,
                                       String workorder_desc,
                                       List<LocalMedia> mediaList) {
        Map<String, RequestBody> map = new HashMap<>();
        if (mediaList != null && mediaList.size() > 0) {
            for (int i = 0; i < mediaList.size(); i++) {
                LocalMedia localMedia = mediaList.get(i);
                File file = new File(localMedia.getCompressPath());
                RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                map.put("img" + i, body);
            }
        }
        new RxHelper().view(getRootView()).load(getModel().getRemote().upMaintainRequireOrder(member_phone, community_name, estate_address,
                workorder_desc, map)).application(LeApplication.mApplication).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onUpSuccess(result.data);
            }
            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onUpError(error, error);
            }
        }).start();
    }

    /**
     *
     * @param mediaList
     */
    public void upComplainRequireOrder(String member_phone,
                                       String community_name,
                                       String estate_address,
                                       String workorder_desc, List<LocalMedia> mediaList) {
        Map<String, RequestBody> map = new HashMap<>();
        if (mediaList != null && mediaList.size() > 0) {
            for (int i = 0; i < mediaList.size(); i++) {
                LocalMedia localMedia = mediaList.get(i);
                File file = new File(localMedia.getCompressPath());
                RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                map.put("img" + i, body);
            }
        }
        new RxHelper().view(getRootView()).load(getModel().getRemote().upComplainRequireOrder(member_phone, community_name,
                estate_address, workorder_desc, map)).application(LeApplication.mApplication).callBack(new RxHelper
                .CallBackAdapter<BaseBean<IsOkData>>() {
            @Override
            public void onSuccess(String response, BaseBean<IsOkData> result) {
                getRootView().onUpSuccess(result.data);
            }
            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                getRootView().onUpError(error, error);
            }
        }).start();
    }

}
