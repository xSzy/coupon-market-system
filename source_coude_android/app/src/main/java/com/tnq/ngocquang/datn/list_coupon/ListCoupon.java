package com.tnq.ngocquang.datn.list_coupon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.adapter.CouponAdapter;
import com.tnq.ngocquang.datn.login_register_user.LoginActivity;
import com.tnq.ngocquang.datn.model.Coupon;
import com.tnq.ngocquang.datn.support.EndlessRecyclerViewScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tnq.ngocquang.datn.constant.Constant;

public class ListCoupon extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Coupon> mListCoupon;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private CouponAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_coupon);
        anhxa();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mListCoupon = new ArrayList<>();
        fetchListCoupon(1);

        mScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);

            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);
        adapter = new CouponAdapter(this, mListCoupon);
        mRecyclerView.setAdapter(adapter);

    }

    private void loadNextDataFromApi(int page) {
        fetchListCoupon(page);
    }

    private void anhxa() {
        mRecyclerView = findViewById(R.id.recyclerViewListCoupon);

    }

    private void fetchListCoupon(int page) {
        String url = Constant.hostname + Constant.listCouponAPI;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        url += "?_page=" + page + "&_limit=20";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("AAA",response.toString());

                try {
                    JSONArray listCouponJSON = response.getJSONArray("data");
                    for (int i = 0; i < listCouponJSON.length(); i++) {
                        JSONObject couponJSON = listCouponJSON.getJSONObject(i);
                        Gson gson = new Gson();
                        Coupon coupon1 = gson.fromJson(couponJSON.toString(),Coupon.class);
                        mListCoupon.add(coupon1);
                    }
                    // update listCoupon
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AAA", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        request.setRetryPolicy( new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add_coupon:
                // add coupon
                Intent intent = new Intent(ListCoupon.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
