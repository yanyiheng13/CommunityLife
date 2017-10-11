package com.iot12369.easylifeandroid.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iot12369.easylifeandroid.ui.BaseFragment;
import com.sai.framework.mvp.BasePresenter;


/**
 * 功能说明： 支付相关半屏的基类</br>
 *
 * @author: Yiheng Yan
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 17-1-5
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public abstract class LePayFragment<P extends BasePresenter> extends BaseFragment<P> {

    protected static int contentViewHeight = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        if (contentViewHeight == 0) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    contentViewHeight = view.getMeasuredHeight();
                }
            });
        }

        return view;
    }

    protected void syncContentViewHeight() {
        if (contentViewHeight != 0) {
            ViewGroup.LayoutParams params = getView().getLayoutParams();
            if (params != null) {
                params.height = contentViewHeight;
            }
        }
    }
}
