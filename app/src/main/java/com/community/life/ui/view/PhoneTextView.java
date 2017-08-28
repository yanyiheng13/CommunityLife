package com.community.life.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 功能说明： 显示电话号码 去除下划线和默认颜色
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-28
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class PhoneTextView extends AppCompatTextView {

    public PhoneTextView(Context context) {
        super(context);
    }

    public PhoneTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PhoneTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
