package com.iot12369.easylifeandroid.model;

import java.io.Serializable;

/**
 * @author YiHeng Yan
 * @Description
 * @Email yanyiheng86@163.com
 * @date 2017/11/5 17:44
 */

public class PayData implements Serializable {
//    "amount":0.01,
//            "amount_refunded":0,
//            "amount_settle":0,
//            "app":"app_A09bYsi7615j8QWq",
//            "body":"生活用品",
//            "channel":"wechat_app",
//            "client_ip":"127.0.0.7",
//            "currency":"cny",
//            "description":"日常使用",
//            "id":"ch_245cee1903193bbe2bb2335c",
//            "liveMode":false,
//            "order_no":"2017110116520018",
//
//            "reqSuccessFlag":true,
//            "status":"PROCESSING",
//            "subject":"商品01",
//            "time_created":1509786500000,
//            "time_expire":1509790099060
    public String amount;
    public String amount_refunded;
    public String app;
    public String body;
    public String channel;
    public String client_ip;
    public String currency;
    public String description;
    public String id;
    public boolean liveMode;
    public String order_no;
    public boolean reqSuccessFlag;
    public String status;
    public String subject;
    public String time_created;
    public String time_expire;

    public Credential credential;

}
