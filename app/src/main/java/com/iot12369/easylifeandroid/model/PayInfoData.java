package com.iot12369.easylifeandroid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 功能说明：
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 17-8-31
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */

public class PayInfoData implements Serializable {
    //        "currentEstate": "1",
//                "orderno": "1709002433420171101162853240",
//                "communityFloor": null,
//                "subject": "闫毅恒交来物业费",
//                "communityBuiding": null,
//                "description": "待定描述",
//                "body": "闫毅恒交来物业费:待定",
//                "numberOfMonth": "12",
//                "communityCarSpace": null,
//                "communityHouse": null,
//                "money": "235224",
//                "estateAuditStatus": "2",
//                "communityRawAddress": "北京市昌平区紫金新干线小区",
//                "name": "闫毅恒",
//                "communityHouseArea": "9900",
//                "communityName": "紫金新干线小区",
//                "communityId": null,
//                "communityUnit": null,
//                "class": "class com.iot12369.estate.dto.EstateCostDto",
//                "estateServiceUnitprice": "198",
//                "cutoffdate": "2017-09-21",
//                "memberId": "14"
    public String currentEstate;
    public String orderno;
    public String communityFloor;
    public String subject;
    public String communityBuiding;
    public String description;
    public String body;
    public String numberOfMonth;
    public String communityCarSpace;
    public String communityHouse;
    public String money;
    @SerializedName("moneyOfParkingPlace")
    public String moneyCar;
    public String estateAuditStatus;
    public String communityRawAddress;
    public String name;
    public String communityHouseArea;
    public String communityName;
    public String communityId;
    public String communityUnit;
    public String estateServiceUnitprice;
    public String cutoffdate;
    public String memberId;
    public String parkingplaceCutoffdate;
    public String communityParkingPlacePrice;
}
