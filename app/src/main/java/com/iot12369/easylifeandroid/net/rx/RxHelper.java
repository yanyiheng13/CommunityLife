package com.iot12369.easylifeandroid.net.rx;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.sai.framework.mvp.MvpView;
import com.sai.framework.rx.*;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:12
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public class RxHelper {

    private MvpView mvpView;
    private Type clsType;

    private Flowable flowable;
    private CallBack callBack;

    private boolean isList;

    private boolean showLoading = true;
    private Context mContext;

    public RxHelper view(MvpView view) {
        this.mvpView = view;
        return this;
    }

    public RxHelper load(Flowable flowable) {
        this.flowable = flowable;
        return this;
    }

    public RxHelper callBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public RxHelper showDialog(boolean showLoading) {
        this.showLoading = showLoading;
        return this;
    }

    public RxHelper isList(boolean isList) {
        this.isList = isList;
        return this;
    }

    public RxHelper application(Context context) {
        mContext= context;
        return this;
    }


    public void start() {
        if (mvpView != null) {
            LifecycleTransformer lt = com.sai.framework.rx.RxUtils.bindToLifecycle(mvpView);
            if (lt != null) {
                flowable.compose(lt);
            }
        }

        if (callBack != null && callBack instanceof CallBackAdapter) {
            clsType = ((CallBackAdapter) callBack).cls;
        }

        flowable.subscribeOn(Schedulers.io()).map(new Function<ResponseBody, String>() {
            @Override
            public String apply(ResponseBody responseBody) throws Exception {
                return responseBody.string();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new RXSubscriber<Object>(mvpView, clsType) {
            @Override
            protected void onHandleSuccess(String response, Object o) {
                if (callBack != null && mvpView != null) {
                    callBack.onSuccess(response, o);
                }
            }

            @Override
            protected void onHandleListSuccess(String response, List<Object> list) {
                super.onHandleListSuccess(response, list);
                if (callBack != null && mvpView != null) {
                    callBack.onSuccess(response, list);
                }
            }

            @Override
            protected void onHandleError(String code, String msg) {
                super.onHandleError(code, msg);
                if (callBack != null && mvpView != null) {
                    callBack.onFailure(msg);
                }
            }

            @Override
            protected boolean showLoadDialog() {
                return showLoading;
            }
        });
    }


    public interface CallBack<D> {
        void onSuccess(String response, D result);

        void onSuccess(String response, List<D> result);

        void onFailure(String error);
    }

    public static abstract class CallBackAdapter<D> implements CallBack<D> {
        public Type cls;

        public CallBackAdapter() {
            Type p = getClass().getGenericSuperclass();
            if (p != null && p instanceof ParameterizedType) {
                cls = ((ParameterizedType) p).getActualTypeArguments()[0];
            }
        }


        @Override
        public void onSuccess(String response, D result) {

        }

        @Override
        public void onSuccess(String response, List<D> result) {

        }

        @Override
        public void onFailure(String error) {
        }
    }
}
