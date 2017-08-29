package com.community.life.model;

import java.io.Serializable;

/**
 * 功能说明：店员对象
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 17-5-3
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */

public class StaffVo implements Serializable {
    public String name;    //店员姓名
    public String picURL; //店员头像
    public String userID; //店员标识
    public String telePhone;//电话号码

    public boolean isSelect;//本地字段
}
