package com.easycommerce.util;

public class NumberUtil {

    public static double roundPrice(double price) {
        return (double) Math.round(price * 100) / 100;
    }
}
