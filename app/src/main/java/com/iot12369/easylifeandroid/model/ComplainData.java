package com.iot12369.easylifeandroid.model;

import java.io.Serializable;
import java.util.List;

/**
 * 功能说明： 投诉列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 17-8-29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */

public class ComplainData implements Serializable {
//     "startIndex":0,
//             "pageIndex":1,
//             "pageSize":10,
    // "totalCount":1
    public int startIndex;
    public int pageIndex;
    public int pageSize;
    public int totalCount;

    public List<ComplainVo> list;

}
