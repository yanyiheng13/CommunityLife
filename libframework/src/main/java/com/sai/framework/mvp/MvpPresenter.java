package com.sai.framework.mvp;
public interface MvpPresenter<M extends MvpModel, V extends MvpView> {

    public void attachView(M model, V view);

    public void detachView();
}
