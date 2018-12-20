package com.iot12369.easylifeandroid.model;

import java.io.Serializable;

/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-27 下午9:32
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class AnnouncementVo implements Serializable {

    public String ctime;
    public String modifyTime;
    public String noticeId;
    public String noticeTitle;
    public String noticeContent;
    public String noticeCommunityId;
    public String noticeCreateUserid;
    public String readState;// 消息是否已读
}
