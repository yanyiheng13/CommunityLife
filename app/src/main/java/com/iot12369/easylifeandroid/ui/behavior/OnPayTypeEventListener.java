package com.iot12369.easylifeandroid.ui.behavior;


/**
 * 功能说明： 支付详情页的回调事件
 *
 * @author: Yiheng Yan
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 17-1-7
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public interface OnPayTypeEventListener {
    /**
     * 支付详情页点击进入支付方式选择界面
     */
    void OnPayTypeSelected(int channel);
    void OnPaySelectBack();

    /**
     * 支付详情页返回事件
     */
}
