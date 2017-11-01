package com.iot12369.easylifeandroid.model;

import java.io.Serializable;
import java.util.List;

/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-27 下午9:32
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class AnnouncementData implements Serializable {
    public String startIndex;
    public String pageIndex;
    public String pageSize;
    public String totalCount;
    public List<AnnouncementVo> list;

}
