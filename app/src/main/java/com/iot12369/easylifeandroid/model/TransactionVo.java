package com.iot12369.easylifeandroid.model;

import java.io.Serializable;

/**
 * 功能说明： 缴费(交易记录)对象
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-27 下午3:02
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class TransactionVo implements Serializable {

//"payer":"13436552144",
//        "orderNo":"ch_7bb675b2bf680c2bf05945c2",
//        "chargeID":"2",
//        "amount":"2376",
//        "recordCreateTime":"2017-11-07 09:03:34",
//        "title":"我是来交物业的",
//        "body":"徐丽交来物业费:待定",
//        "chargeType":"0"
   public String payer;
   public String orderNo;
   public String chargeID;
   public String amount;
   public String recordCreateTime;
   public String title;
   public String body;
   public String chargeType;
}
