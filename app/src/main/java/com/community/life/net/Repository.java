package com.community.life.net;


import com.community.life.BuildConfig;
import com.sai.framework.mvp.MvpModel;
import com.sai.framework.retrofit.RetrofitHelper;

import java.util.HashMap;
/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:32
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public class Repository implements MvpModel {

    private RemoteService mRemoteService;

    private LocalService mLocalService;

    private RetrofitHelper.Builder mBuilder;

    private static class RepositoryInstance {
        public static Repository instance = new Repository();
    }


    private Repository() {
//        InputStream[] inputStreams = new InputStream[]{com.community.life.LeApplication.mApplication.getResources().openRawResource(R.raw.le2017)};

        mBuilder = new RetrofitHelper.Builder().baseUrl(BuildConfig.api_url)
                .debug(BuildConfig.DEBUG);
        mRemoteService = mBuilder.build().create(RemoteService
                .class);
    }

    public Repository setHeader(HashMap<String, String> mapHeader) {
        if (mBuilder != null) {
            mBuilder.headers(mapHeader);
        }
        return this;
    }

    public static Repository get() {
        return RepositoryInstance.instance;
    }

    public RemoteService getRemote() {
        return mRemoteService;
    }

    public LocalService getLocal() {
        return mLocalService;
    }
}
