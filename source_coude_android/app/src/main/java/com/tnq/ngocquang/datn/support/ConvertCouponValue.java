package com.tnq.ngocquang.datn.support;

public class ConvertCouponValue {
    public static String convert(int type, double value, int valueType){

        String couponValue = "- ";
        // sale off by percent
        if (type == 1) {
            couponValue += value + " %";
        }
        // sale off by money
        else if (type == 2) {
            if (valueType == 0) {
                couponValue += (int)(value * 1000) + " Vnd";
            } else if (valueType == 1) {
                couponValue += value + " Usd";
            }
        }
        return couponValue;
    }
}
