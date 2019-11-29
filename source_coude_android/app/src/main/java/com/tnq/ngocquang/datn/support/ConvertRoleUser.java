package com.tnq.ngocquang.datn.support;

public class ConvertRoleUser {

    public static String convert(int role){
        String roleValue = "";
        if(role == 1){
            roleValue = "user";
        }
        else if(role == 2){
            roleValue = "admin";
        }
        return roleValue;
    }
}
