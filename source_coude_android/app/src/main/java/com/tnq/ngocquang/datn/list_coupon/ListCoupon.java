package com.tnq.ngocquang.datn.list_coupon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.adapter.CouponAdapter;
import com.tnq.ngocquang.datn.model.Coupon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import constant.Constant;

public class ListCoupon extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Coupon> mListCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_coupon);
        anhxa();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListCoupon = new ArrayList<>();
//        Coupon coupon1 = new Coupon("quang","tran ngoc quang");
//        for (int i = 0 ; i < 5 ; i ++){
//            mListCoupon.add(coupon1);
//        }
        mListCoupon = fetchListCoupon();
        Log.d("AAA",mListCoupon.size() + "");
        CouponAdapter adapter = new CouponAdapter(this, mListCoupon);
        mRecyclerView.setAdapter(adapter);

    }

    private void anhxa() {
        mRecyclerView = findViewById(R.id.recyclerViewListCoupon);

    }

    private ArrayList<Coupon> fetchListCoupon() {
        final ArrayList<Coupon> listCoupon = new ArrayList<>();
        String url = Constant.hostname + Constant.listCouponAPI;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<>();
        params.put("_page", "1");
        params.put("_limit", "10");
        JSONObject jsonObject = new JSONObject(params);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray listCouponJSON = response.getJSONArray("data");
                    for (int i = 0; i < listCouponJSON.length(); i++) {
                        JSONObject coupon = listCouponJSON.getJSONObject(i);
                        listCoupon.add(new Coupon(coupon.getString("title"), coupon.getString("description")));
                        Log.d("AAA",coupon.getString("title"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AAA", "error");
            }
        });
        requestQueue.add(request);
        return listCoupon;
    }
}
