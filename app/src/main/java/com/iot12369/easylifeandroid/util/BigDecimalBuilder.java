package com.iot12369.easylifeandroid.util;

import java.math.BigDecimal;

public class BigDecimalBuilder {
    private String mValue;
    private BigDecimal mBigDecimal;
    public BigDecimalBuilder(String value){
        mBigDecimal = new BigDecimal(value);
    }

    /**
     * 加法
     * @param value
     * @return
     */
    public BigDecimalBuilder add(String value){
        mBigDecimal = mBigDecimal.add(getBigDecimal(value));
        return this;
    }

    public BigDecimalBuilder add(String... nums) {

        for(String num:nums){
            add(num);
        }
        return this;
    }

    /**
     * 减法
     * @param value
     * @return
     */
    public BigDecimalBuilder sub(String value){
        mBigDecimal = mBigDecimal.subtract(getBigDecimal(value));
        return this;
    }


    /**
     * 乘法
     * @param value
     * @return
     */
    public BigDecimalBuilder multiply(String value){
        mBigDecimal = mBigDecimal.multiply(getBigDecimal(value));
        return this;
    }

    /**
     * 除法
     * @param value
     * @return
     */
    public BigDecimalBuilder divide(String value){
        return divide(value, 2);
    }

    /**
     *
     * @param value
     * @param scale 小数点保留位数
     * @return
     */
    public BigDecimalBuilder divide(String value, int scale){
        BigDecimal reVal = getBigDecimal(value);
        if(reVal.compareTo(BigDecimal.ZERO) == 0){
            reVal = BigDecimal.ONE;
        }
        mBigDecimal = mBigDecimal.divide(reVal, scale, BigDecimal.ROUND_DOWN);
        return this;
    }

    /**
     * 求余
     * @param value
     * @return
     */
    public BigDecimalBuilder remainder(String value){
        BigDecimal reVal = getBigDecimal(value);
        if(reVal.compareTo(BigDecimal.ZERO) == 0){
            reVal = BigDecimal.ONE;
        }
        mBigDecimal = mBigDecimal.remainder(reVal);
        return this;
    }
    /**
     * 求余
     * @param num
     * @return  1 大于 num 0 等于 num -1 小于 num
     */
    public int compare(String num) {
        BigDecimal b = getBigDecimal(num);
        return mBigDecimal.compareTo(b);
    }

    private BigDecimal getBigDecimal(String num) {
        return getBigDecimal(num, BigDecimal.ZERO);
    }

    private BigDecimal getBigDecimal(String num, BigDecimal def) {
        if(num != null && num.length() > 0){
            return new BigDecimal(num);
        }
        return def;
    }


    public BigDecimal getValue(){
        return mBigDecimal;
    }
}
