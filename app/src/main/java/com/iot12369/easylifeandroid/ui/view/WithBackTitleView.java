package com.iot12369.easylifeandroid.ui.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iot12369.easylifeandroid.R;

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

public class WithBackTitleView extends LinearLayout {
    @BindView(R.id.title_text)
    TextView mTitle;
    @BindView(R.id.rl_title_back)
    RelativeLayout mRlBack;
    @BindView(R.id.title_icon_img)
    ImageView mImageIcon;
    @BindView(R.id.view_line)
    View mViewLine;
    @BindView(R.id.title_txt_right)
    TextView mTvRight;

    public WithBackTitleView(Context context) {
        this(context, null);
    }

    public WithBackTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WithBackTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_back_title, this);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(ContextCompat.getColor(context, R.color.colorTitleBg));
        ButterKnife.bind(this, this);
    }

    public WithBackTitleView setText(int txtId) {
        if (txtId != 0) {
            mTitle.setText(txtId);
        }
        return this;
    }


    public WithBackTitleView setImageResource(int drawableId) {
        if (drawableId != 0) {
            mImageIcon.setVisibility(VISIBLE);
            mImageIcon.setImageResource(drawableId);
        } else {
            mImageIcon.setVisibility(View.GONE);
        }
        return this;
    }

    public WithBackTitleView setImageResource2(int drawableId) {
        if (drawableId != 0) {
            mImageIcon.setVisibility(VISIBLE);
            if (mImageIcon.getLayoutParams() != null) {
                mImageIcon.getLayoutParams().height = dp2px(23);
                mImageIcon.getLayoutParams().width = dp2px(38);
            }
            mImageIcon.setImageResource(drawableId);
        } else {
            mImageIcon.setVisibility(View.GONE);
        }
        return this;
    }

    public int dp2px(float dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    @OnClick(R.id.rl_title_back)
    public void onClick() {
        ((Activity)getContext()).finish();
    }

    public void goneBackAndLine() {
        mRlBack.setVisibility(View.GONE);
        mViewLine.setVisibility(View.GONE);
    }

    public void setRightVisible() {
        mTvRight.setVisibility(VISIBLE);
    }

    @OnClick(R.id.title_txt_right)
    public void onRightClickListener() {
        ((Activity)getContext()).finish();
    }
}
