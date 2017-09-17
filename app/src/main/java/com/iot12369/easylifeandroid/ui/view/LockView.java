package com.iot12369.easylifeandroid.ui.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.iot12369.easylifeandroid.R;

/**
 * @author YiHeng Yan
 * @Description
 * @Email yanyiheng86@163.com
 * @date 2017/9/17 20:59
 */

public class LockView extends FrameLayout {

    public LockView(@NonNull Context context) {
        this(context, null);
    }

    public LockView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_lock, this);

    }
}
