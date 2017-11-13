package com.iot12369.easylifeandroid.model;

import java.io.Serializable;

/**
 * 功能说明： 维修进度
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-25 下午10:00
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class RepairOrderDetailData implements Serializable {

    public String orderNum;
    public String time;
    public String des;
    public String status;//1:有问题  2:待处理  3:已解决

//      "workorder_sn":"201711100001",
//                    "member_phone":"13888888888",
//                    "workorder_type":1,
//                    "workorder_state":2,
//                    "community_id":"",
//                    "community_name":"龙龙物业",
//                    "estate_address":"南大街1号",
//                    "workorder_desc":"漏水",
//                    "title":"漏水",
//                    "server_url":"http://39.106.61.132:8989",
//                    "img_url_1":"/images/2017/11/myimg01.jpg",
//                    "img_url_2":"/images/2017/11/myimg02.jpg",
//                    "img_url_3":"",
//                    "img_url_4":"",
//                    "img_url_5":"",
//                    "img_url_6":"",
//                    "img_url_7":"",
//                    "img_url_8":"",
//                    "img_url_9":""
    public String workorder_sn;
    public String member_phone;
    public String workorder_type;
    public String workorder_state;
    public String community_id;
    public String community_name;
    public String estate_address;
    public String workorder_desc;
    public String title;
    public String server_url;
    public String img_url_1;
    public String img_url_2;
    public String img_url_3;
    public String img_url_4;
    public String img_url_5;
    public String img_url_6;
    public String img_url_7;
    public String img_url_8;
    public String img_url_9;
}
