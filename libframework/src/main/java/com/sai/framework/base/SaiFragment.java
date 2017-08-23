package com.sai.framework.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sai.framework.mvp.BasePresenter;
import com.sai.framework.mvp.MvpModel;
import com.sai.framework.mvp.MvpView;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class SaiFragment<P extends BasePresenter> extends RxFragment implements MvpView {

    protected P mPresenter;


    public Context getContext() {
        return super.getContext();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(getModel(), this);
        }
    }

    protected P getPresenter() {
        return mPresenter;
    }

    public boolean isDestroy() {
        Activity activity = getActivity();
        if (activity == null || activity.isDestroyed()) {
            return true;
        }
        return false;
    }


    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroyView();
    }

    private <T> T initPresenter() {
        try {
            Type type = getClass().getGenericSuperclass();

            if (type == null) {
                return null;
            }
            if(!(type instanceof ParameterizedType)){
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

    protected MvpModel getModel() {
        return null;
    }
}
