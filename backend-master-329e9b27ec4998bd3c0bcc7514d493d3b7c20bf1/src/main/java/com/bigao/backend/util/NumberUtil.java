package com.bigao.backend.util;

import java.text.DecimalFormat;

/**
 * Created by wait on 2015/12/14.
 */
public class NumberUtil {
    /** 只保存两位小数 */
    private static final DecimalFormat numberFormat = new DecimalFormat("#0.00");

    private static final DecimalFormat intFormat = new DecimalFormat("#");

    public static String divide(int fenZi, int fenMu) {
        if (fenZi > 0 && fenMu > 0) {
            return numberFormat.format(100.0 * fenZi / fenMu) + "%";
        }
        return "0.00%";
    }

    public static String divideToIntPercent(int fenZi, int fenMu) {
        if (fenZi > 0 && fenMu > 0) {
            return intFormat.format(100.0 * fenZi / fenMu) + "%";
        }
        return "0%";
    }

    public static String divideWithoutPercent(int fenZi, int fenMu) {
        if (fenZi > 0 && fenMu > 0) {
            return numberFormat.format(100.0 * fenZi / fenMu);
        }
        return "0.00";
    }

    public static String divideWithBefore(int fenZi, int fenMu) {
        if (fenZi > 0 && fenMu > 0) {
            return fenZi + "(" + numberFormat.format(100.0 * fenZi / fenMu) + "%)";
        }
        return fenZi + "(0.00%)";
    }

    public static void main(String[] args) {
        System.err.println(NumberUtil.divide(1, 289));
    }
}
