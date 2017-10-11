package com.iot12369.easylifeandroid.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iot12369.easylifeandroid.R;


/**
 * 提交按钮按钮加等待
 * Created by Yiheng Yan on 16-8-17.
 * yanyiheng@le.com
 * Copyright  2016年 LetvFinancial. All rights reserved.
 */
public class ProgressButton extends RelativeLayout implements View.OnClickListener {

    private CircleProgressView mProgressBar;
    private LinearLayout mPbParent;
    private TextView mTextView;

    private String mTextContent;
    private boolean isDefaultLoading;

    public ProgressButton(Context context) {
        this(context, null);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R .styleable.CustomTitleView);
        mTextContent = array.getString(R.styleable.CustomTitleView_btn_text);
        isDefaultLoading = array.getBoolean(R.styleable.CustomTitleView_click_loading, true);

        inflate(context, R.layout.view_progress_btn, this);
        mProgressBar = (CircleProgressView) findViewById(R.id.btn_progress);
        mTextView = (TextView) findViewById(R.id.btn_progress_tv);
        mPbParent = (LinearLayout) findViewById(R.id.btn_progress_parent);
        mPbParent.setOnClickListener(null);

        mTextView.setText(TextUtils.isEmpty(mTextContent) ? "" : mTextContent);

        mPbParent.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_progress_parent) {
            if (listener != null) {
                if (isDefaultLoading) {
                    setLoading();
                }
                listener.onSubmitClickListener();
            }
        }
    }

    public void isDefaultLoading(boolean isDefaultLoading) {
        this.isDefaultLoading = isDefaultLoading;
    }

    public void setLoading() {
        mPbParent.setOnClickListener(null);
        mProgressBar.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.GONE);
    }


    public void setCanClick() {
        mPbParent.setEnabled(true);
        mProgressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
        mPbParent.setOnClickListener(this);
    }

    public void setCannotClick() {
        mPbParent.setOnClickListener(null);
        mPbParent.setEnabled(false);
        mProgressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
    }

    public void isCanClick(boolean isCanClick) {
        if (isCanClick) {
            setCanClick();
        } else {
            setCannotClick();
        }
    }

    public void setText(String text) {
        if (mTextView != null && text != null) {
            mTextView.setText(text);
        }
    }

    private OnSubmitClickListener listener;

    public void setOnSubmitClickListener(OnSubmitClickListener listener) {
        this.listener = listener;
    }

    public interface OnSubmitClickListener {
        void onSubmitClickListener();
    }
}
