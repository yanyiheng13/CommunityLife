package com.iot12369.easylifeandroid.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 一些通用的类
 * @author: huajie
 * @version: 1.0
 * @date: 2017/1/24
 * @email: huajie@le.com
 * @Copyright (c) 2017. le.com Inc. All rights reserved.
 */
public class CommonUtil {

    /**
     * 获取银行卡号后四位
     *
     * @param cardNumber
     * @return
     */
    public static String getCardEndNumber(String cardNumber) {
        if (!TextUtils.isEmpty(cardNumber) && cardNumber.length() >= 4) {
            return cardNumber.substring(cardNumber.length() - 4);
        }
        return cardNumber;
    }

    /**
     * 格式化金额
     *
     * @param amount
     * @param length 小数点后保留位数，如果为-1，小数点后有几位就显示几位(最大2位)，
     * @return amount为空用0补位
     */
    public static String amountFormat(String amount, int length) {
        if (TextUtils.isEmpty(amount)) {
            if (length == 0 || length == -1) {
                return "0";
            }
            return "0." + suffix("", length, "0");
        }

        NumberFormat format = null;
        if (length == 0) {
            format = new DecimalFormat("###,###,##0");
        } else if (length == -1) {
            format = new DecimalFormat("###,###,##0.##");
        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,###,##0.");
            for (int i = 0; i < length; i++) {
                buff.append("0");
            }
            format = new DecimalFormat(buff.toString());
        }
        return format.format(Double.parseDouble(amount));
    }

    /**
     * 目前此规则使用范围为产品相关，不适用于个人资产相关
     * 需要特别注意
     * 1.传入参数单位为分
     * 2.转化为以元为单位后当小数部分为零，格式化后为整数
     * 3.转化为以元为单位后当小数部分不为零，格式化后保留两位小数
     * 4.格式化后含有千分为“,”因此切勿拿返回数据直接做运算
     *
     * @param amount
     * @return
     */
    public static String formatAmountByAutomation(String amount) {
        String keepTwo = formatAmountByKeepTwo(amount);//转化为元
        if (keepTwo.endsWith(".00")) {
            return keepTwo.replace(".00", "");
        }
        return keepTwo;
    }

    /**
     * 传入参数单位为分，保留两位小数
     * 格式化后含有千分为“,”因此切勿拿返回数据直接做运算
     *
     * @param amount
     * @return
     */
    public static String formatAmountByKeepTwo(String amount) {
        return amountFormat(centToYuan(amount), 2);
    }

    /**
     * 传入参数单位为分，保留整数
     * 格式化后含有千分为“,”因此切勿拿返回数据直接做运算
     *
     * @param amount
     * @return
     */
    public static String formatAmountByInteger(String amount) {
        return amountFormat(centToYuan(amount), 0);
    }

    /**
     * 格式化。千位分割。保留整数
     *
     * @param amount
     * @return
     */
    public static String amountFormat(String amount) {

        return amountFormat(amount, 0);
    }

    /**
     * 分转元
     *
     * @param amount
     * @return
     */
    public static String centToYuan(String amount) {
        if (TextUtils.isEmpty(amount)) {
            return "0";
        }
        BigDecimal a = new BigDecimal(amount);
        return a.divide(new BigDecimal("100")).toString();
    }

    /**
     * 元转分
     *
     * @param amount
     * @return
     */
    public static String yuanToCent(String amount) {
        if (TextUtils.isEmpty(amount)) {
            return "0";
        }
        BigDecimal a = new BigDecimal(amount);
        return String.valueOf(a.multiply(new BigDecimal("100")).longValue());
    }

    /**
     * 字符串后面补足到length长度
     *
     * @param key
     * @param length
     * @param val
     * @return
     */
    public static String suffix(String key, int length, String val) {
        StringBuilder builder = new StringBuilder(key == null ? "" : key);
        if (builder.length() > length) {
            return builder.toString();
        }
        int i = 0;
        while (i < length) {
            builder.append(val);
            i++;
        }
        return builder.toString();
    }

    /**
     * 利率格式化
     *
     * @param amount
     * @param length 小数点后保留位数，如果为-1，小数点后有几位就显示几位，
     * @return
     */
//    public static String rateFormat(String amount, int length) {
//        if (TextUtils.isEmpty(amount)) {
//            if (length == 0 || length == -1) {
//                return "0";
//            }
//            return "0." + TextUtils.fillSuffix("", length, "0");
//        }
//
//        NumberFormat format = null;
//        if (length == 0) {
//            format = new DecimalFormat("########0");
//        } else if (length == -1) {
//            format = new DecimalFormat("########0.##");
//        } else {
//            StringBuffer buff = new StringBuffer();
//            buff.append("########0.");
//            for (int i = 0; i < length; i++) {
//                buff.append("0");
//            }
//            format = new DecimalFormat(buff.toString());
//        }
//        return format.format(Double.parseDouble(amount));
//    }

    /**
     * 风险高中低
     */
    public static String getRiskLevelStr(int riskLevel) {
        if (riskLevel == 1 || riskLevel == 2) {
            return "风险低";
        } else if (riskLevel == 3) {
            return "风险中";
        } else if (riskLevel == 4 || riskLevel == 5) {
            return "风险高";
        }
        return "风险低";

    }

    /**
     * 产品进度
     */
    public static String getProgressValueString(double currentSale, double totalValue) {
        if(totalValue == 0){
            totalValue = 1;
        }
        double f = currentSale / totalValue;
        String progressValueStr = "";
        if (f <= 0.3) {
            progressValueStr = "火热开抢";
        } else if (f > 0.3 && f <= 0.8) {
            progressValueStr = "热卖中";
        } else if (f > 0.8 && f < 1.0) {
            progressValueStr = "最后抢购";
        } else if (currentSale == totalValue) {
            progressValueStr = "抢光了";
        }
        return progressValueStr;
    }

    public static String getRepayWay(String repayWay) {
        if ("1".equals(repayWay)) {
            return "等额本息";
        } else if ("2".equals(repayWay)) {
            return "等额本金";
        } else if ("3".equals(repayWay)) {
            return "按月付息到期还本";
        } else if ("4".equals(repayWay)) {
            return "一次性还本付息";
        }
        return "";
    }

}
