package com.iot12369.easylifeandroid.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author YiHeng Yan
 * @Description
 * @Email yanyiheng86@163.com
 * @date 2017/11/1 5:33
 */

public class NoticeData implements Serializable {
    public String startIndex;
    public String pageIndex;
    public String pageSize;
    public String totalCount;
    public List<AnnouncementVo> list;
}
