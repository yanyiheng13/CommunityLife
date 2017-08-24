package com.community.life.ui.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.community.life.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 标题栏
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 下午11:10
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class TitleView extends LinearLayout {
    @BindView(R.id.title_text)
    TextView mTitle;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_title, this);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(ContextCompat.getColor(context, R.color.colorTitleBg));
        ButterKnife.bind(this, this);
    }

    public void setText(int txtId) {
        if (txtId != 0) {
            mTitle.setText(txtId);
        }
    }

    @OnClick(R.id.rl_title_back)
    public void onClick() {
        ((Activity)getContext()).finish();
    }
}
