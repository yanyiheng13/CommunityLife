package com.community.life.model;

import java.io.Serializable;

/**
 * 功能说明： 维修列表的item
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-25 下午10:00
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class MaintainBean extends BaseBean implements Serializable {
    public String orderNum;
    public String time;
    public String des;
    public String status;//1:有问题  2:待处理  3:已解决
}
