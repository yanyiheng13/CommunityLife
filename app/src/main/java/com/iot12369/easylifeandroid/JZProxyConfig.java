package com.iot12369.easylifeandroid;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

/**
 * @author Shylock
 * @create 2018/8/10 11:07
 * @Describe
 */
public class JZProxyConfig {
    HttpProxyCacheServer proxy;//视频缓存代理

    public void init(Context context) {
        proxy = new HttpProxyCacheServer.Builder(context).maxCacheSize((1024 * 1024 * 512)) //512M 缓存
                .maxCacheFilesCount(30)//最大缓存30个视频
                .build();
    }

    public HttpProxyCacheServer  getProxy() {
        return proxy;
    }

    // 利用静态内部类特性实现外部类的单例
    private static class SingleTonBuilder {
        private static JZProxyConfig INSTANCE = new JZProxyConfig();
    }
    // 私有化构造函数private SingleTon() {


    public static JZProxyConfig getInstance() {
        return SingleTonBuilder.INSTANCE;
    }
}
