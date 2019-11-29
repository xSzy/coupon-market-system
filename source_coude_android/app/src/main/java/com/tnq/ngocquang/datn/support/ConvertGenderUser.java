package com.tnq.ngocquang.datn.support;

public class ConvertGenderUser {
    public static String convert(int gender){
        String genderValue = "";
        if(gender == 1){
            genderValue = " nam ";
        }else if(gender == 2){
            genderValue = " nữ ";
        }else if(gender == 0){
            genderValue = " không rõ ";
        }
        return genderValue;
    }
    public static int reConvert(String genderValue){
        int gender = 0;
        if(genderValue.trim().toLowerCase().equals("nam")){
            gender = 1;
        }else if(genderValue.trim().toLowerCase().equals("nữ") || genderValue.trim().toLowerCase().equals("nu")){
            gender = 2;
        }
        return gender;
    }
}
