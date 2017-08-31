package com.community.life.ui.view;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.community.life.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明：上传图片自定义view
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-31
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class UploadPicView extends RelativeLayout {

    @BindView(R.id.up_helper_view)
    UploadPicHelperView mUploadPicView;

    @BindView(R.id.image_pic)
    ImageView mUpImgView;

    public UploadPicView(Context context) {
        this(context, null);
    }

    public UploadPicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UploadPicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_upload_pic, this);
        ButterKnife.bind(this, this);
    }

    /**
     * 设置显示的view
     */
    public UploadPicView setImageResource(int ids) {
        if (ids != 0) {
            mUpImgView.setImageResource(ids);
        }
        return this;
    }

    public UploadPicView setProgress(int progress){
        mUploadPicView.setProgress(progress);
        return this;
    };

    public UploadPicView setError() {
        mUploadPicView.setError();
        return this;
    }

    public UploadPicView setIsShow(boolean isShow) {
        mUploadPicView.setIsShow(isShow);
        return this;
    }

    public ImageView imgView() {
        return mUpImgView;
    }
}
