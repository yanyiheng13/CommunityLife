package com.community.life.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.community.life.R;
import com.community.life.net.Repository;
import com.community.life.util.UiTitleBarUtil;
import com.sai.framework.base.SaiActivity;
import com.sai.framework.mvp.BasePresenter;
import com.sai.framework.mvp.MvpModel;

/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:01
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public class BaseActivity<P extends BasePresenter> extends SaiActivity<P> {
    protected int mStatausBarHeight;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTitleBarSetting()) {
            UiTitleBarUtil uiTitleBarUtil = new UiTitleBarUtil(this);
            uiTitleBarUtil.setColorBar(ContextCompat.getColor(this, R.color.colorTitleBar), 50);
        }
        if (isCalculateHeight()) {
            mStatausBarHeight = -1;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                mStatausBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
        }


    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void hideLoadDialog() {

    }

    @Override
    protected MvpModel getModel() {
        return Repository.get();
    }

    protected boolean isTitleBarSetting() {
        return true;
    }

    protected boolean isCalculateHeight() {
        return false;
    }
}
