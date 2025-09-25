package org.khr.anzenauth.base.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 精确的浮点数运算工具类，基于 BigDecimal 实现，避免 double 精度丢失问题。
 *
 * @author KK
 */
@UtilityClass
public class Arith {

    /** 默认除法运算精度 */
    private static final int DEF_DIV_SCALE = 10;

    /**
     * double 转 BigDecimal（避免 new BigDecimal(double) 精度问题）
     */
    private static BigDecimal valueOf(double v) {
        return BigDecimal.valueOf(v);
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return v1 + v2
     */
    public static double add(double v1, double v2) {
        return valueOf(v1).add(valueOf(v2)).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return v1 - v2
     */
    public static double sub(double v1, double v2) {
        return valueOf(v1).subtract(valueOf(v2)).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return v1 * v2
     */
    public static double mul(double v1, double v2) {
        return valueOf(v1).multiply(valueOf(v2)).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算。
     * 当发生除不尽时，精确到小数点以后10位，四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return v1 / v2
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。
     * 当发生除不尽时，由 scale 参数指定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 小数点后保留几位，必须 >= 0
     * @return v1 / v2
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("Scale 必须是非负整数");
        }
        if (v2 == 0D) {
            throw new ArithmeticException("除数不能为 0");
        }
        return valueOf(v1).divide(valueOf(v2), scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位，必须 >= 0
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("Scale 必须是非负整数");
        }
        return valueOf(v).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }
}
