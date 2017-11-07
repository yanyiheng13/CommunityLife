package com.iot12369.easylifeandroid.model;

import com.iot12369.easylifeandroid.BuildConfig;

import java.io.Serializable;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-1
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class PayRequest implements Serializable {

    public String order_no;
    public String amount;
    public String amountShow;
    public String subject;
    public String body;
    public String description;
    public String channel;
    public String client_ip;
    public String app = "app_A09bYsi7615j8QWq";
    public String currency = "CNY";


    public String communityRawAddress;
}
