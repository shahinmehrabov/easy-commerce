package com.easycommerce.util;

public class NumberUtil {

    public static double roundNumber(double number) {
        return (double) Math.round(number * 100) / 100;
    }
}
