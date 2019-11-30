package com.tnq.ngocquang.datn.support;

import android.util.Log;

public class ConvertCouponValue {
    public static String convert(int type, double value, int valueType){

        String couponValue = "- ";
        // free ship
        if (type == 1) {
            couponValue = " miễn phí vận chuyển";
        }
        // discount
        else if (type == 2) {
            if(valueType == 1){
                couponValue += value + " % ";
            }
            else if (valueType == 2) {
                couponValue += (int)(value * 1000) + " Vnd";
            } else if (valueType == 3) {
                couponValue += value + " Usd";
            } else if(valueType == 4){
                couponValue = "khuyến mãi khác";
            }
        }
        return couponValue;
    }


}
