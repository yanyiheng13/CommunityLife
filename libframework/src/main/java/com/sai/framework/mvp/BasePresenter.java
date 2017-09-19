package com.sai.framework.mvp;

import android.content.Context;


public class BasePresenter<M extends MvpModel, V extends MvpView> implements MvpPresenter<M, V> {

    private V mView;
    private M mModel;

    public BasePresenter() {
    }

    public BasePresenter(M model, V view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void attachView(M model, V view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void detachView() {
        if (mModel != null) {
            mModel = null;
        }
        mView = null;
    }

    protected V getRootView() {
        return mView;
    }

    protected M getModel() {
        return mModel;
    }

    protected Context getContext() {
        if (mView != null) {
            return mView.getContext();
        }
        return null;
    }
}
