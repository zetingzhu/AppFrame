package com.example.signatureview;

import java.math.BigDecimal;

/**
 * @author: zeting
 * @date: 2020/9/16
 */
public class BigDecimalUtil {
    // 除法运算默认精度
    private static final int DEF_DIV_SCALE = 2;

    /**
     * 加法
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 加法
     */
    public static double add(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 减法
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 减法
     */
    public static double sub(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 乘法
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 乘法
     */
    public static double mul(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 除法 使用默认精度
     */
    public static double div(double value1, double value2) {
        return div(value1, value2, DEF_DIV_SCALE);
    }

    /**
     * 除法 使用默认精度
     */
    public static double div(String value1, String value2) {
        return div(value1, value2, DEF_DIV_SCALE);
    }

    /**
     * 除法
     *
     * @param scale 精度
     */
    public static double div(double value1, double value2, int scale) {
        if (scale < 0) {
            scale = DEF_DIV_SCALE;
        }
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 除法
     *
     * @param scale 精度
     */
    public static double div(String value1, String value2, int scale) {
        if (scale < 0) {
            scale = DEF_DIV_SCALE;
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入
     *
     * @param scale 小数点后保留几位
     */
    public static double round(double v, int scale) {
        return div(v, 1, scale);
    }

    /**
     * 四舍五入
     *
     * @param scale 小数点后保留几位
     */
    public static double round(String v, int scale) {
        return div(v, "1", scale);
    }

    /**
     * 比较大小
     */
    public static boolean equalTo(BigDecimal b1, BigDecimal b2) {
        if (b1 == null || b2 == null) {
            return false;
        }
        return 0 == b1.compareTo(b2);
    }
}
