package com.iot12369.easylifeandroid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author YiHeng Yan
 * @Description
 * @Email yanyiheng86@163.com
 * @date 2017/11/5 18:01
 */

public class WechatApp implements Serializable {
//   "appid":"wx41b7c1c1bcbf4af5",
//           "noncestr":"htp1nk5gesepex79q9g7nul10xt03zp5",
//           "package":"Sign=WXPay",
//           "partnerid":"1489153092",
//           "prepayid":"wx20171104170819e7757944f40451454506",
//           "sign":"D4B45F5299F5E4A242D319FBC7579FDD",
//           "timestamp":1509786499
    public String appid;
    public String noncestr;
    @SerializedName("package")
    public String packages;
    public String partnerid;
    public String prepayid;
    public String sign;
    public String timestamp;

    public String orderInfo;
}
