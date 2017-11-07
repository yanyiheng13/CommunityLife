package com.iot12369.easylifeandroid.model;

import java.io.Serializable;
import java.util.List;

/**
 * 功能说明： 缴费(交易记录)对象
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-27 下午3:02
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class TransactionData implements Serializable {

//     "startIndex":2,
//             "pageIndex":2,
//             "pageSize":2,
    public String startIndex;
    public String pageIndex;
    public String pageSize;
    public String totalCount;
    public List<TransactionVo> list;
}
