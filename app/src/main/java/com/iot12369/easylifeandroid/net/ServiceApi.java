package com.iot12369.easylifeandroid.net;
/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:54
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public class ServiceApi {

    private ServiceApi() {}

    private static class I {
        private static final ServiceApi INSTANCE = new ServiceApi();
    }

    public ServiceApi get() {
        return I.INSTANCE;
    }


}
