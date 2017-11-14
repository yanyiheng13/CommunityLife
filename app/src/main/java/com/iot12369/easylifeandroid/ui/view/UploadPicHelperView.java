package com.iot12369.easylifeandroid.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * 功能说明：上传图片自定义view
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-31
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class UploadPicHelperView extends View {

    private Paint mPaint;//画笔
    private int progress = 0;
    private boolean isError;
    private boolean isShowHelper = true;

    public UploadPicHelperView(Context context) {
        this(context, null);
    }

    public UploadPicHelperView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UploadPicHelperView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.FILL);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isShowHelper) {
            return;
        }
//        if (isError) {//如果上传失败 则整个图片被阴影遮盖,显示点击重试
//            mPaint.setColor(Color.parseColor("#70000000"));//半透明
//            canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
//            Rect rect = new Rect();
//            String text = getContext().getString(R.string.click_retry);
//            mPaint.setColor(0xFFFFFFFF);
//            mPaint.getTextBounds(text, 0, text.length(), rect);//确定文字的宽度
//            canvas.drawText(text, getWidth()/2-rect.width()/2,getHeight()/2, mPaint);
//            return;
//        }
//
//        mPaint.setColor(Color.parseColor("#70000000"));//半透明
//        canvas.drawRect(0, 0, getWidth(), getHeight()-getHeight() * progress/100, mPaint);
//
//        mPaint.setColor(Color.parseColor("#00000000"));//全透明
//        canvas.drawRect(0, getHeight()-getHeight() * progress/100, getWidth(),  getHeight(), mPaint);
//
//        mPaint.setTextSize(30);
//        mPaint.setColor(Color.parseColor("#FFFFFF"));
//        mPaint.setStrokeWidth(2);
//
//        Rect rect=new Rect();
//        String text = "0%";
//        if (progress == 0) {
//            text = "即将上传";
//        } else if (progress <= 100) {
//            text = progress + "%";
//        }
//        mPaint.setColor(0xFFFFFFFF);
//        mPaint.getTextBounds(text, 0, text.length(), rect);//确定文字的宽度
//        canvas.drawText(text, getWidth() / 2 - rect.width() / 2, getHeight() / 2, mPaint);

    }

    public UploadPicHelperView setProgress(int progress){
        isError = false;
        this.progress=progress;
        postInvalidate();
        return this;
    };

    public UploadPicHelperView setError() {
        isError = true;
        postInvalidate();
        return this;
    }

    public UploadPicHelperView setIsShow(boolean isShow) {
        isShowHelper = isShow;//默认需要
        if (isShowHelper) {
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
        return this;
    }
}
