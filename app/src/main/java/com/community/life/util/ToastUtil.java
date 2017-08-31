package com.community.life.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 功能说明：
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 17-8-31
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */

public class ToastUtil {

    public static void toast(Context context, String txt) {
        Toast.makeText(context, txt, Toast.LENGTH_SHORT).show();
    }
}
