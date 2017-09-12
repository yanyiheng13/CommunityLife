package com.iot12369.easylifeandroid.ui.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iot12369.easylifeandroid.R;

/**
 * 功能说明： 空view
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */

public class EmptyView extends RelativeLayout implements View.OnClickListener {
    /** 显示数据状态的view **/
    private ImageView mStateImg;

    /** 加载中旋转动画的view **/
    private ImageView mLoadingImg;

    /** 显示状态的文字 **/
    private TextView mStateTxt;

    /** 显示状态的文字 **/
    private TextView mTvLoad;

    /** 绑定的View,当隐藏状态view mStateImg的时候将其隐藏 **/
    private View mBindView;

    /** loading中的父布局 **/
    private LinearLayout mLoadingLayout;

    /** 出错图片资源id **/
    private int mErrorImgResId;

    /** 无数据图片资源id **/
    private int mNoDataImgResId;

    /** 无数据文字资源id **/
    private int mNoDataTxtResId;

    private int mLoadTxtId;

    /** 购买更多理财产品的数据等底部按钮上的文字 **/
    private int mBtnTxt;


    private Animation animation;

    private boolean isAlpha;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_empty, this);
        mStateTxt = (TextView) findViewById(R.id.tv_text);
        mTvLoad = (TextView) findViewById(R.id.tv_load_txt);
        mStateImg = (ImageView) findViewById(R.id.img_empty);
        mLoadingImg = (ImageView) findViewById(R.id.empty_img_load_out);
        mLoadingLayout = (LinearLayout) findViewById(R.id.empty_loading_layout);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initAnimView();
        onSuccess();
    }

    /**
     * 加载动画
     */
    private void initAnimView() {
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(800);
    }

    /**
     * 绑定的界面布局在加载各种状态消失时自动显示界面数据
     * @param view
     */
    public void bindView(View view) {
        mBindView = view;
    }

    /**
     * 开始
     */
    public void onStart() {
        if (isAlpha) {
           setBackgroundColor(0x0e000000);
        } else {
            setBackgroundColor(0XFFF3F3F3);
        }
        this.setVisibility(View.VISIBLE);
        if (mBindView != null) {
            mBindView.setVisibility(View.GONE);
        }
        if (mLoadTxtId != 0) {
            mTvLoad.setText(mLoadTxtId);
        }
        mStateTxt.setVisibility(View.GONE);
        mStateImg.setVisibility(View.GONE);

        if (animation == null) {
            initAnimView();
        }
        mLoadingLayout.setVisibility(View.VISIBLE);
        mLoadingImg.startAnimation(animation);
    }

    /**
     * 加载数据失败
     */
    public void onError() {
        setBackgroundColor(0XFFF3F3F3);
        setVisibility(View.VISIBLE);
        mStateImg.setVisibility(View.VISIBLE);
        if (mBindView != null) {
            mBindView.setVisibility(View.GONE);
        }
        if (mStateImg != null) {
            mStateImg.clearAnimation();
            mStateImg.setImageDrawable(ContextCompat.getDrawable(getContext(), mErrorImgResId == 0 ? R.mipmap.icon_cry : mErrorImgResId));
        }
        if (mStateTxt != null) {
            mStateTxt.setVisibility(View.VISIBLE);
            mStateTxt.setText(R.string.loading_failure);
        }
        mLoadingLayout.setVisibility(View.GONE);
        mLoadingImg.clearAnimation();
    }

    /**
     * 成功后的处理
     */
    public void onSuccess() {
        setBackgroundColor(0XFFF3F3F3);
        if (mStateImg != null) {
            mStateImg.clearAnimation();
        }
        this.setVisibility(View.GONE);
        if (mBindView != null) {
            mBindView.setVisibility(View.VISIBLE);
        }
        mLoadingLayout.setVisibility(View.GONE);
        mLoadingImg.clearAnimation();
    }

    /**
     * 没有数据显示的状态
     */
    public void onEmpty() {
        setBackgroundColor(0XFFF3F3F3);
        this.setVisibility(View.VISIBLE);
        mStateImg.setVisibility(View.VISIBLE);
        if (mBindView != null) {
            mBindView.setVisibility(View.INVISIBLE);
        }
        if (mStateImg != null) {
            mStateImg.clearAnimation();
            mStateImg.setImageDrawable(ContextCompat.getDrawable(getContext(), mNoDataImgResId == 0 ? R.mipmap.icon_no_data : mNoDataImgResId));
        }
        if (mStateTxt != null) {
            mStateTxt.setVisibility(View.VISIBLE);
            mStateTxt.setText(mNoDataTxtResId == 0 ? R.string.no_data : mNoDataTxtResId);
        }
        mLoadingLayout.setVisibility(View.GONE);
        mLoadingImg.clearAnimation();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (listener != null) {
            onStart();
            listener.onDataLoadAgain();
        }
    }

    /**
     * 底部等点击按钮显示按钮字体文字资源id
     * @param btnTxt
     * @return
     */
    public EmptyView setBottomBtnTxt(int btnTxt) {
        this.mBtnTxt = btnTxt;
        return this;
    }

    /**
     * 没有数据的特定显示图标
     * @param noDataImgResId
     * @return
     */
    public EmptyView setNoDataImageView(int noDataImgResId) {
        this.mNoDataImgResId = noDataImgResId;
        return this;
    }

    /**
     * 没有数据的图标下面特定文本显示
     * @param noDataTxtResId
     * @return
     */
    public EmptyView setNoDataTxtResId(int noDataTxtResId) {
        this.mNoDataTxtResId = noDataTxtResId;
        return this;
    }

    public EmptyView setLoadTextId(int loadTextId) {
        mLoadTxtId = loadTextId;
        return this;
    }

    private OnDataLoadStatusListener listener;

    public interface OnDataLoadStatusListener {
        void onDataLoadAgain();
    }

    /**
     * 点击屏幕再试事件
     * @param listener
     */
    public void setOnDataLoadStatusListener(OnDataLoadStatusListener listener) {
        this.listener = listener;
    }

    /**
     * 设置透明背景颜色
     */
    public void setBgTransColor() {
        isAlpha = true;
    }

    /**
     * 资源回收
     */
    @Override
    protected void onDetachedFromWindow() {
        if (mLoadingImg != null) {
            mLoadingImg.clearAnimation();
        }
        super.onDetachedFromWindow();
    }

}
