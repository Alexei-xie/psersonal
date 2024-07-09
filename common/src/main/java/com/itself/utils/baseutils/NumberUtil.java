package com.itself.utils.baseutils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.util.Assert;

/**
 * @Author: duJi
 * @Date: 2024-06-13
 **/
public class NumberUtil {

    /**
     * BigDecimal类型的值进行转换，避免后续使用空指针
     */
    public static BigDecimal convertBigDecimal(BigDecimal value){
        return null == value ? BigDecimal.ZERO : value;
    }

    //==================================================== 返回值 BigDecimal =======================================================
    //====================================================加法运算=======================================================
    /**
     * 加法运算
     */
    public static BigDecimal add(Number... values) {
        if (ArrayUtil.isEmpty(values)) {
            return new BigDecimal("0");
        }

        Number value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value.toString());
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.add(new BigDecimal(value.toString()));
            }
        }
        return result;
    }
    /**
     * 加法运算
     */
    public static BigDecimal add(String... values) {
        if (ArrayUtil.isEmpty(values)) {
            return new BigDecimal("0");
        }
        String value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value);
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.add(new BigDecimal(value));
            }
        }
        return result;
    }

    /**
     * 加法运算
     */
    public static BigDecimal add(BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            return new BigDecimal("0");
        }

        BigDecimal value = values[0];
        BigDecimal result = null == value ? new BigDecimal("0") : value;
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.add(value);
            }
        }
        return result;
    }

    //====================================================减法运算=======================================================

    /**
     * 减法运算
     */
    public static BigDecimal sub(BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            return new BigDecimal("0");
        }

        BigDecimal value = values[0];
        BigDecimal result = null == value ? new BigDecimal("0") : value;
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.subtract(value);
            }
        }
        return result;
    }
    /**
     * 减法运算
     */
    public static BigDecimal sub(Number... values) {
        if (ArrayUtil.isEmpty(values)) {
            return new BigDecimal("0");
        }

        Number value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value.toString());
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.subtract(new BigDecimal(value.toString()));
            }
        }
        return result;
    }

    /**
     * 减法运算
     */
    public static BigDecimal sub(String... values) {
        if (ArrayUtil.isEmpty(values)) {
            return new BigDecimal("0");
        }

        String value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value);
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.subtract(new BigDecimal(value));
            }
        }
        return result;
    }

    //====================================================乘法运算=======================================================

    /**
     * 乘法运算
     */
    public static BigDecimal mul(Number... values) {
        if (ArrayUtil.isEmpty(values)) {
            return new BigDecimal("0");
        }

        Number value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value.toString());
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.multiply(new BigDecimal(value.toString()));
            }
        }
        return result;
    }
    /**
     * 乘法运算
     */
    public static BigDecimal mul(String... values) {
        if (ArrayUtil.isEmpty(values)) {
            return new BigDecimal("0");
        }

        String value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value);
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.multiply(new BigDecimal(value));
            }
        }
        return result;
    }

    /**
     * 乘法运算
     */
    public static BigDecimal mul(BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            return new BigDecimal("0");
        }

        BigDecimal value = values[0];
        BigDecimal result = null == value ? new BigDecimal("0") : value;
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.multiply(value);
            }
        }
        return result;
    }
    //====================================================除法运算=======================================================
    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度
     *
     * @param v1 被除数
     * @param v2 除数
     * @param roundingMode 保留小数的模式 {@link RoundingMode} 四舍五入：RoundingMode.HALF_UP
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2, RoundingMode roundingMode) {
        Assert.notNull(v2, "Divisor must be not null !");
        assert v2.compareTo(BigDecimal.ZERO) != 0  : "除数不可以为零！";
        if(null == v1) {
            return new BigDecimal("0");
        }

        return v1.divide(v2,2, roundingMode);
    }

    public static void main(String[] args) {
        System.out.println(div(new BigDecimal("100"), new BigDecimal("10"), RoundingMode.HALF_UP));
    }
}
