package com.iot12369.easylifeandroid.model;

import java.io.Serializable;
import java.util.List;

/**
 * 功能说明： 维修列表的item
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-25 下午10:00
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class MaintainData implements Serializable {
    public int startIndex;
    public int pageIndex;
    public int pageSize;
    public int totalCount;

    public List<MaintainVo> list;
}
