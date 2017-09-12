package com.iot12369.easylifeandroid.model;

import java.io.Serializable;

/**
 * 功能说明： 投诉进度
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 17-8-29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */

public class ComplainProgressData implements Serializable {
    public String orderNum;
    public String time;
    public String des;
    public String status;//1:有问题  2:待处理  3:已解决
}
