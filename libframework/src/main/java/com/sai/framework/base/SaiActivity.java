package com.sai.framework.base;


import android.content.Context;
import android.os.Bundle;

import com.sai.framework.mvp.BasePresenter;
import com.sai.framework.mvp.MvpModel;
import com.sai.framework.mvp.MvpView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class SaiActivity<P extends BasePresenter> extends RxAppCompatActivity implements MvpView {

    protected P mBasePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBasePresenter = initPresenter();
        if (mBasePresenter != null) {
            mBasePresenter.attachView(getModel(), this);
        }
    }

    public Context getContext() {
        return this;
    }


    protected P getPresenter() {
        return mBasePresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBasePresenter != null) {
            mBasePresenter.detachView();
            mBasePresenter = null;
        }
    }


    private <T> T initPresenter() {
        try {
            Type type = getClass().getGenericSuperclass();

            if (type == null) {
                return null;
            }
            if (!(type instanceof ParameterizedType)) {
                return null;
            }
            ParameterizedType ptype = (ParameterizedType) type;
            Class<T> cls = (Class<T>) ptype.getActualTypeArguments()[0];
            if (cls != null) {
                return cls.newInstance();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract MvpModel getModel();
}
