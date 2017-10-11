package com.iot12369.easylifeandroid.ui.behavior;


import java.util.ArrayList;

/**
 * 功能说明： 支付详情页的回调事件
 *
 * @author: Yiheng Yan
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 17-1-7
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public interface OnPayDetailEventListener {
    /**
     * 支付详情页点击进入支付方式选择界面
     */
    void OnPayDetailCardClick(int channel);
    void onPay(int channel);
    void OnPayClose();

    /**
     * 支付详情页返回事件
     */
}
