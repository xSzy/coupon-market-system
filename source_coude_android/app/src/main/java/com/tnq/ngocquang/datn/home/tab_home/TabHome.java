package com.tnq.ngocquang.datn.home.tab_home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.android.volley.toolbox.Volley;
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
    private static final String URL_CATEGORY_GETALL = Constant.hostname + Constant.categoryGetAllAPI;

    private void anhxa() {
        mRecyclerView = mView.findViewById(R.id.recyclerViewListCategory);
        mCouponTrendPager = mView.findViewById(R.id.trend_coupon_pager);
        indicator = mView.findViewById(R.id.indicatior);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_tab_home, container, false);
        anhxa();
        initTrendCoupon();
        initCategory();
        return mView;
    }

    private static void initTrendCoupon() {
        mCouponTrendData = new ArrayList<>();
        TrendCouponAdapter adapter = new TrendCouponAdapter(mView.getContext(), mCouponTrendData);
        mCouponTrendPager.setAdapter(adapter);
        // this is write on the responce method
        indicator.setViewPager(mCouponTrendPager);
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


    private static void initCategory() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mView.getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mCategoryData = new ArrayList<>();
        final CategoryAdapter adapter = new CategoryAdapter(mCategoryData, mView.getContext(), "TAB_HOME", null);
        mRecyclerView.setAdapter(adapter);

        // retrieve category data from server
        RequestQueue requestQueue = Volley.newRequestQueue(mView.getContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_CATEGORY_GETALL, null, new Response.Listener<JSONObject>() {
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
                Log.d("AAA", "error : " + error.toString());

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
