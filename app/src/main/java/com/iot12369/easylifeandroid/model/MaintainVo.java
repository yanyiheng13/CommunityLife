package com.iot12369.easylifeandroid.model;

import java.io.Serializable;

/**
 * 功能说明： 维修列表的item
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-25 下午10:00
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class MaintainVo implements Serializable {
    public String orderNum;
    public String time;
    public String des;
    public String status;//1:有问题  2:待处理  3:已解决

//    "estate_address":"广安门路1号",
//            "workorder_mtime":1510194236000,
//            "workorder_id":2,
//            "workorder_sn":"201711090002",
//            "member_phone":"13836552100",
//            "img_url_1":"images/2017/11/timg01.jpg",
//            "workorder_ctime":1510194236000,
//            "community_name":"精诚物业",
//            "workorder_type":1
    public String estate_address;
    public String workorder_mtime;
    public String workorder_id;
    public String workorder_sn;
    public String member_phone;
    public String img_url_1;
    public String workorder_ctime;
    public String community_name;
    public String workorder_type;
}
