package com.tnq.ngocquang.datn.home.tab_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.tnq.ngocquang.datn.support.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailCategory extends AppCompatActivity implements RecyclerViewClickListener {

    private TextView mTitle;
    private RecyclerView mCategory;
    private RecyclerView mCouponsByCate;
    private ArrayList<Category> mListCategory;
    private ArrayList<Coupon> mListCoupon;
    private LinearLayoutManager mLayoutManagerCate;
    private LinearLayoutManager mLayoutManagerCoupon;
    private RelativeLayout mProgressBar;
    private CouponAdapter mCouponAdapter;
    private View mPrevView = null;
    private RequestQueue requestQueue;
    private String TAG_REQUEST_VOLLEY;
    private final String urlFindCouponsByCate = Constant.hostname + Constant.findCouponByCateAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category);
        anhxa();
        // init requestQueue volley
        requestQueue = MyVolley.getInstance(this.getApplicationContext()).getRequestQueue();
        Intent intent = getIntent();
        Category category = intent.getParcelableExtra("category");
        // set title
        mTitle.setText("Bạn đang xem " + category.getName());
        mListCategory = category.getSubCategory();
        initCategory();
        initCouponBegin(mListCategory.get(0).getId());
    }

    private void anhxa() {
        mTitle = findViewById(R.id.title_category);
        mCategory = findViewById(R.id.recyclerViewCategoryDetail);
        mCouponsByCate = findViewById(R.id.recyclerViewCouponByCate);
        mProgressBar = findViewById(R.id.linlaheaderProgress);
        mListCategory = new ArrayList<>();
        mListCoupon = new ArrayList<>();
    }

    private void initCategory() {
        mLayoutManagerCate = new LinearLayoutManager(this);
        mLayoutManagerCate.setOrientation(RecyclerView.HORIZONTAL);
        mCategory.setLayoutManager(mLayoutManagerCate);
        CategoryAdapter adapter = new CategoryAdapter(mListCategory, this, "", this);
        mCategory.setAdapter(adapter);
    }

    private void initCouponBegin(final int idCate) {
        if (TAG_REQUEST_VOLLEY != null) {
            requestQueue.cancelAll(TAG_REQUEST_VOLLEY);
        }
        mListCoupon.clear();
        mLayoutManagerCoupon = new LinearLayoutManager(this);
        mLayoutManagerCoupon.setOrientation(RecyclerView.VERTICAL);
        mCouponsByCate.setLayoutManager(mLayoutManagerCoupon);

        // init data when activity start
        Category category = mListCategory.get(0);
        mCouponAdapter = new CouponAdapter(this, mListCoupon);
        mCouponsByCate.setAdapter(mCouponAdapter);
        fetchListCouponFromAPI(1, idCate);
        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManagerCoupon) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("AAA page ",page + "");
                fetchListCouponFromAPI(page, idCate);
            }
        };
        mCouponsByCate.addOnScrollListener(endlessRecyclerViewScrollListener);
    }

    private void fetchListCouponFromAPI(int page, int idCate) {
        TAG_REQUEST_VOLLEY = idCate + "";
        mProgressBar.setVisibility(View.VISIBLE);
        // send request to server, receive list coupon by category and subcategory
        String url = urlFindCouponsByCate + "?_page=" + page + "&_limit=8" + "&categoryId=" + idCate;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("success")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Gson gson = new Gson();
                            Coupon coupon = gson.fromJson(jsonObject.toString(), Coupon.class);

                            mListCoupon.add(coupon);
//                            Log.d("AAA", coupon.getValue() + "");
                        }
                        mCouponAdapter.notifyDataSetChanged();
                        mProgressBar.setVisibility(View.GONE);
                    }
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
        request.setTag(TAG_REQUEST_VOLLEY);
        request.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    //when item of category is clicked.
    @Override
    public void clickedItem(View v, int position) {
        if (mPrevView != null) {
            mPrevView.setBackground(getResources().getDrawable(R.drawable.edt_default));
        }
        mPrevView = v;
        v.setBackgroundColor(Color.rgb(234, 255, 255));
        initCouponBegin(mListCategory.get(position).getId());
    }
}
