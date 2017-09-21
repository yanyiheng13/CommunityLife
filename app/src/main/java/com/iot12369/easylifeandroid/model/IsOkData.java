package com.iot12369.easylifeandroid.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 功能说明：
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 17-8-30
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */

public class IsOkData implements Serializable {
    public String status;

    public boolean isOk() {
        return "1".equals(status);
    }
}
