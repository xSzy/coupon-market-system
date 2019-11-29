package com.tnq.ngocquang.datn.home.tab_home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.adapter.CategoryAdapter;
import com.tnq.ngocquang.datn.adapter.TrendCouponAdapter;
import com.tnq.ngocquang.datn.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tnq.ngocquang.datn.constant.Constant;
import com.tnq.ngocquang.datn.model.Coupon;
import com.tnq.ngocquang.datn.support.MyVolley;

import me.relex.circleindicator.CircleIndicator;

public class TabHome extends Fragment {

    public TabHome() {
    }

    private static RecyclerView mRecyclerView;
    public static View mView;
    private static ViewPager mCouponTrendPager;
    private static CircleIndicator indicator;
    private static ArrayList<Category> mCategoryData;
    private static ArrayList<Coupon> mCouponTrendData;
    private static int currentItem;
    private static Handler handler;
    private static Runnable runnable;
    private static RequestQueue requestQueue;
    private static final String URL_CATEGORY_DETALL = Constant.hostname + Constant.categoryGetAllAPI;
    private static final String URL_TREND_COUPON = Constant.hostname + Constant.trendCouponAPI;

    private void anhxa() {
        mRecyclerView = mView.findViewById(R.id.recyclerViewListCategory);
        mCouponTrendPager = mView.findViewById(R.id.trend_coupon_pager);
        indicator = mView.findViewById(R.id.indicatior);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab_home, container, false);
        anhxa();
        requestQueue = MyVolley.getInstance(mView.getContext()).getRequestQueue();
        initTrendCoupon();
        initCategory();
        return mView;
    }

    private static void initTrendCoupon() {
        mCouponTrendData = new ArrayList<>();
        final TrendCouponAdapter adapter = new TrendCouponAdapter(mView.getContext(), mCouponTrendData);
        mCouponTrendPager.setAdapter(adapter);
        // fetch data trend coupon from server
        String url = URL_TREND_COUPON + "?pageSize=8";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray listResponse = response.getJSONArray("data");
                    for (int i = 0 ; i < listResponse.length() ;i ++){
                        JSONObject object = listResponse.getJSONObject(i);
                        Coupon coupon = new Gson().fromJson(object.toString(),Coupon.class);
                        mCouponTrendData.add(coupon);
                    }
                    adapter.notifyDataSetChanged();
                    indicator.setViewPager(mCouponTrendPager);
                    adapter.registerDataSetObserver(indicator.getDataSetObserver());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AAA", "error_trend_coupon : " + error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = mCouponTrendPager.getCurrentItem();
                currentItem++;
                if (currentItem >= mCouponTrendPager.getAdapter().getCount()) {
                    currentItem = 0;
                }
                mCouponTrendPager.setCurrentItem(currentItem, true);
                handler.postDelayed(runnable, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private static void initCategory() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mView.getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mCategoryData = new ArrayList<>();
        final CategoryAdapter adapter = new CategoryAdapter(mCategoryData, mView.getContext(), "TAB_HOME", null);
        mRecyclerView.setAdapter(adapter);

        // retrieve category data from server
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_CATEGORY_DETALL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("success")) {
//                        Log.d("AAA",response.toString());
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Gson gson = new Gson();
                            Category category = gson.fromJson(jsonObject.toString(), Category.class);
                            mCategoryData.add(category);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AAA", "error_init_category : " + error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

    }
}
