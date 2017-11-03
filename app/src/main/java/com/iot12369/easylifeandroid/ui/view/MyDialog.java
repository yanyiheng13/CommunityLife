package com.iot12369.easylifeandroid.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.iot12369.easylifeandroid.R;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-3
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class MyDialog extends Dialog {

    public MyDialog(@NonNull Context context) {
        this(context, R.style.dialog);
    }

    public MyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

}
