package com.iot12369.easylifeandroid.model;

import java.io.Serializable;
import java.util.List;

/**
 * 功能说明： 地址对象
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 17-9-3
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */

public class AddressData implements Serializable {
    public String startIndex;
    public String pageIndex;
    public String pageSize;
    public String totalCount;
    public List<AddressVo> list;
}
