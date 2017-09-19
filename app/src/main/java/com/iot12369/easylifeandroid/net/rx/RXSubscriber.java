package com.iot12369.easylifeandroid.net.rx;


import android.app.Activity;
import android.content.Context;

import com.iot12369.easylifeandroid.model.BaseBean;
import com.google.gson.Gson;
import com.sai.framework.exception.ExceptionParser;
import com.sai.framework.mvp.MvpView;

import java.lang.ref.SoftReference;
import java.lang.reflect.Type;
import java.util.List;

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
    private boolean isList;

//    public RXSubscriber(Context context, Type type) {
//        if (context != null) {
//            this.mSoftContext = new SoftReference(context);
//        }
//
//        this.resType = type;
//    }

    public RXSubscriber(MvpView mvpView, Type type) {
        this.mMvpView = mvpView;
        if (this.mMvpView != null) {
            this.mSoftContext = new SoftReference(this.mMvpView.getContext());
        }

        this.resType = type;
    }

    public RXSubscriber(MvpView mvpView, Type type, boolean isList) {
        this.mMvpView = mvpView;
        if (this.mMvpView != null) {
            this.mSoftContext = new SoftReference(this.mMvpView.getContext());
        }

        this.resType = type;
        this.isList = isList;
    }

    protected void onStart() {
        super.onStart();
        if (this.showLoadDialog() && this.mMvpView != null) {
            this.mMvpView.showLoadDialog();
        }

    }

    private Context getContext() {
        return this.mSoftContext != null ? (Context) this.mSoftContext.get() : null;
    }

    public void onComplete() {
    }

    public void onNext(String response) {

        if (intercepted()) {
            return;
        }

        if (response == null || response.length() == 0) {
            onHandleError(null, null);
            return;
        }

        if (this.resType == null) {
            this.onHandleSuccess(response, null);
            return;
        }

        BaseBean result = json2Object(response, BaseBean.class);
        if (result == null) {
            onHandleError(null, null);
            return;
        }

        if (!result.isSuccess()) {
            onHandleError(result.result, result.message);
            return;
        }

        if (isList) {
            onHandleListSuccess(response, (List<T>) json2List(response, resType));
        } else {
            T tt = json2Object(response, resType);
            BaseBean model = (BaseBean)tt;
            if (model == null) {
                onHandleError(null, null);
            } else if (!"200".equals(model.result)) {
                onHandleError(model.result, model.message);
            } else {
                onHandleSuccess(response, tt);
            }
        }
//        T tt = json2Object(response, this.resType);
//        if (tt == null) {
//            this.onHandleError(null, null);
//        } else {
//            this.onHandleSuccess(response, (T) tt);
//        }

    }

    protected boolean intercepted() {
        if (this.showLoadDialog() && this.mMvpView != null) {
            this.mMvpView.hideLoadDialog();
        }

        Context context = this.getContext();
        if (context == null) {
            return true;
        } else {
            if (context instanceof Activity) {
                boolean isDestroyed = ((Activity) context).isDestroyed();
                if (isDestroyed) {
                    return true;
                }
            }

            return false;
        }
    }

    public void onError(Throwable e) {
        if (this.showLoadDialog() && this.mMvpView != null) {
            this.mMvpView.hideLoadDialog();
        }

        ExceptionParser exceptionParser = new ExceptionParser(e);
        this.onHandleError(exceptionParser.getCode(), exceptionParser.getMessage());
    }

    public void unsubscribe() {
        super.cancel();
    }

    protected boolean showLoadDialog() {
        return true;
    }

    protected abstract void onHandleSuccess(String var1, T var2);

    protected void onHandleError(String code, String msg) {
    }

    protected void onHandleListSuccess(String response, List<T> list) {

    }

    public static <T> T json2Object(String jsonString, Type cls) {
        if (jsonString == null) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, cls);
        }
    }

    public static <T> List<T> json2List(String jsonString, Type cls) {
        return null;
    }
}
