package com.community.life.net.rx;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.sai.framework.mvp.MvpView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.lang.reflect.Type;

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

    private Context context;

    private boolean showLoading = true;

    public RxHelper view(MvpView view) {
        this.mvpView = view;
        return this;
    }

//    public RxHelper toBean(Type cls) {
//        this.clsType = cls;
//        return this;
//    }

    public RxHelper load(Flowable flowable) {
        this.flowable = flowable;
        return this;
    }

    public RxHelper callBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public RxHelper showLoad(boolean showLoading) {
        this.showLoading = showLoading;
        return this;
    }

    public RxHelper application(Context context) {
        this.context = context;
        return this;
    }


    public void start() {
        if (mvpView != null) {
            LifecycleTransformer lt = RxUtils.bindToLifecycle(mvpView);
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
                if (callBack != null) {
                    callBack.onSuccess(response, o);
                }
            }

            @Override
            protected void onHandleError(String code, String msg) {
                super.onHandleError(code, msg);
                if (callBack != null) {
                    callBack.onFailure(msg);
                    if (context != null && !TextUtils.isEmpty(msg)) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
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

        void onFailure(String error);
    }

    public static abstract class CallBackAdapter<D> implements CallBack<D> {
        public Type cls;

        public CallBackAdapter() {
        }
        public CallBackAdapter(Type cls) {
            this.cls = cls;
        }

        @Override
        public void onFailure(String error) {
        }
    }
}
