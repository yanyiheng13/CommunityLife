package com.iot12369.easylifeandroid.model;

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
public class PayOtherInfo implements Serializable {
    public String type; //1 代表物业费和车费都选择了  2 代表只选择物业费  3 代表只选择车费
    public String time; //物业费缴费说明
    public String time1; //车位费缴费说明
    public String amountShow;
    public String communityRawAddress;
}
