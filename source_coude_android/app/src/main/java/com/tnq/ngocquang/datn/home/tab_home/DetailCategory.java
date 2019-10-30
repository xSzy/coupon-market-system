package com.tnq.ngocquang.datn.home.tab_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.adapter.CategoryAdapter;
import com.tnq.ngocquang.datn.adapter.CouponAdapter;
import com.tnq.ngocquang.datn.constant.Constant;
import com.tnq.ngocquang.datn.interface_.RecyclerViewClickListener;
import com.tnq.ngocquang.datn.model.Category;
import com.tnq.ngocquang.datn.model.Coupon;
import com.tnq.ngocquang.datn.support.EndlessRecyclerViewScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailCategory extends AppCompatActivity implements RecyclerViewClickListener {


    private RecyclerView mCategory;
    private RecyclerView mCouponsByCate;
    private ArrayList<Category> mListCategory;
    private ArrayList<Coupon> mListCoupon;
    private LinearLayoutManager layoutManagerCate;
    private LinearLayoutManager layoutManagerCoupon;
    private CouponAdapter couponAdapter;
    private final String  urlFindCouponsByCate =Constant.hostname + Constant.findCouponByCateAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category);
        anhxa();
        Intent intent = getIntent();
        Category category = intent.getParcelableExtra("category");
        //
        setTitle(category.getName());
        mListCategory = category.getSubCategory();
        initCategory();
        initCouponBegin();
    }

    private void initCategory(){
        layoutManagerCate = new LinearLayoutManager(this);
        layoutManagerCate.setOrientation(RecyclerView.HORIZONTAL);
        mCategory.setLayoutManager(layoutManagerCate);
        CategoryAdapter adapter = new CategoryAdapter(mListCategory,this,"",this);
        mCategory.setAdapter(adapter);
    }

    private void initCouponBegin(){
        layoutManagerCoupon = new LinearLayoutManager(this);
        layoutManagerCoupon.setOrientation(RecyclerView.VERTICAL);
        mCouponsByCate.setLayoutManager(layoutManagerCoupon);

        Category category = mListCategory.get(0);
        final int idCategory = category.getId();
        couponAdapter = new CouponAdapter(this,mListCoupon);
        mCouponsByCate.setAdapter(couponAdapter);
        fetchListCouponFromAPI(1,idCategory);
        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManagerCoupon) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchListCouponFromAPI(page,idCategory);
            }
        };
        mCouponsByCate.addOnScrollListener(endlessRecyclerViewScrollListener);
    }

    private void fetchListCouponFromAPI(int page,int idCate){
        // send request to server, receive list coupon by category and subcategory
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = urlFindCouponsByCate + "?_page=" + page + "&_limit=5" + "&categoryId=" + idCate;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("success")){
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0 ; i < jsonArray.length() ; i ++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Gson gson = new Gson();
                            Coupon coupon = gson.fromJson(jsonObject.toString(),Coupon.class);
                            mListCoupon.add(coupon);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;

            }
        };
        request.setRetryPolicy( new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    private void anhxa(){
        mCategory = findViewById(R.id.recyclerViewCategoryDetail);
        mCouponsByCate = findViewById(R.id.recyclerViewCouponByCate);
        mListCategory = new ArrayList<>();
        mListCoupon = new ArrayList<>();
    }

    //when category is clicked.
    @Override
    public void recyclerViewListClicked(View v, int position) {
        mListCoupon.clear();
        v.setBackgroundColor(Color.rgb(234, 255, 255));
        Toast.makeText(this, "" + mListCategory.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}
