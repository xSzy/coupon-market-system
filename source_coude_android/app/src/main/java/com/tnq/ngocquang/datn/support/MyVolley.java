package com.tnq.ngocquang.datn.support;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyVolley {
    private static MyVolley instance;
    private RequestQueue requestQueue;
    private Context context;

    private MyVolley(Context context){
        this.context = context;
        this.requestQueue = getRequestQueue();

    }

    public static synchronized MyVolley getInstance(Context context){
        if(instance == null){
            instance = new MyVolley(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

}