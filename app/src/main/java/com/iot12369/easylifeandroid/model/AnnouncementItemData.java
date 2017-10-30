package com.iot12369.easylifeandroid.model;

import java.io.Serializable;

/** 系统公告
 * @author YiHeng Yan
 * @Description
 * @Email yanyiheng86@163.com
 * @date 2017/10/30 15:53
 */

public class AnnouncementItemData implements Serializable {
//     "modifyTime":"--",
//             "createTime":"2017-10-04 20:32:57",
//             "noticeId":4,
//             "noticeCreateUserid":1,
//             "noticeContent":"系统公告：2017-10-01〜2017-10-08系统升级。",
//             "noticeCommunityId":0
    public String modifyTime;
    public String createTime;
    public String noticeId;
    public String noticeCreateUserid;
    public String noticeContent;
    public String noticeCommunityId;
}
