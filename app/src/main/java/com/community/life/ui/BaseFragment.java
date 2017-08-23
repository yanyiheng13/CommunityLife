package com.community.life.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.community.life.net.Repository;
import com.sai.framework.base.SaiFragment;
import com.sai.framework.mvp.BasePresenter;
import com.sai.framework.mvp.MvpModel;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:06
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class BaseFragment<P extends BasePresenter> extends SaiFragment<P> {
    private Unbinder mBinder;

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void hideLoadDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int infId = inflateId();
        if(infId == 0){
            return null;
        }
        View view = inflater.inflate(infId, container, false);
        mBinder = ButterKnife.bind(this, view);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        if (mBinder != null) {
            mBinder.unbind();
        }
        super.onDestroyView();
    }

    public int inflateId() {
        return 0;
    }

    @Override
    protected MvpModel getModel() {
        return Repository.get();
    }
}
