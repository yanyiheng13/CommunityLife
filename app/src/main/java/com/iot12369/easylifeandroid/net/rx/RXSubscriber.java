package com.iot12369.easylifeandroid.net.rx;


import android.app.Activity;
import android.content.Context;

import com.iot12369.easylifeandroid.model.BaseBean;
import com.google.gson.Gson;
import com.sai.framework.exception.ExceptionParser;
import com.sai.framework.mvp.MvpView;

import java.lang.ref.SoftReference;
import java.lang.reflect.Type;

import io.reactivex.subscribers.DefaultSubscriber;

/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:20
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public abstract class RXSubscriber<T> extends DefaultSubscriber<String> {

    private SoftReference<Context> mSoftContext = null;
    private Type resType;
    private MvpView mMvpView = null;

    public RXSubscriber(Context context, Type type) {
        if (context != null) {
            mSoftContext = new SoftReference<>(context);
        }
        resType = type;
    }

    public RXSubscriber(MvpView mvpView, Type type) {
        mMvpView = mvpView;
        if (mMvpView != null) {
            mSoftContext = new SoftReference<>(mMvpView.getContext());
        }
        resType = type;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (showLoadDialog() && mMvpView != null) {
            mMvpView.showLoadDialog();
        }
    }

    private Context getContext() {
        if (mSoftContext != null) {
            return mSoftContext.get();
        }
        return null;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(String response) {

        if (intercepted()) {
            return;
        }

        //检查是否成功
        if (response == null || response.length() == 0) {
            onHandleError(null, null);
            return;
        }
        if (resType == null) {
            onHandleSuccess(response, (T) response);
            return;
        }
        T tt = json2Object(response, resType);
        BaseBean model = (BaseBean)tt;
        if (model == null) {
            onHandleError(null, null);
        } else if (!"200".equals(model.code)) {
            onHandleError(model.code, model.message);
        } else {
            onHandleSuccess(response, tt);
        }
    }

    protected boolean intercepted() {
        if (showLoadDialog() && mMvpView != null) {
            mMvpView.hideLoadDialog();
        }
        Context context = getContext();
        if (context == null) {
            return true;
        }

        if (context instanceof Activity) {
            boolean isDestroyed = ((Activity) context).isDestroyed();
            if (isDestroyed) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onError(Throwable e) {
        if (showLoadDialog() && mMvpView != null) {
            mMvpView.hideLoadDialog();
        }
        ExceptionParser exceptionParser = new ExceptionParser(e);
        onHandleError(exceptionParser.getCode(), exceptionParser.getMessage());
    }

    public void unsubscribe() {
        super.cancel();
    }


    /**
     * 是否显示加载窗
     *
     * @return
     */
    protected boolean showLoadDialog() {
        return true;
    }

    protected abstract void onHandleSuccess(String response, T t);

    protected void onHandleError(String code, String msg) {

    }

    public static <T> T json2Object(String jsonString, Type cls) {
        if (jsonString == null) {
            return null;
        }
        Gson gson = new Gson();
        return (T) gson.fromJson(jsonString, cls);
    }
}
