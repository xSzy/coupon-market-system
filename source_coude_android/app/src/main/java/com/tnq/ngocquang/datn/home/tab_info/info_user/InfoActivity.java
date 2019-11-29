package com.tnq.ngocquang.datn.home.tab_info.info_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tnq.ngocquang.datn.home.tab_info.manage_coupon.ManageCoupon;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.adapter.InfoUserAdapter;
import com.tnq.ngocquang.datn.interface_.RecyclerViewClickListener;
import com.tnq.ngocquang.datn.login_register_user.LoginActivity;
import com.tnq.ngocquang.datn.login_register_user.LoginFBCallBack;
import com.tnq.ngocquang.datn.model.User;
import com.tnq.ngocquang.datn.support.MyVolley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoActivity extends AppCompatActivity implements RecyclerViewClickListener {

    private ArrayList<String> mListOption;
    private RecyclerView mRecyclerView;
    private InfoUserAdapter mInfoUserAdapter;
    private TextView mNameUser;
    private ImageView mAvatarUser;
    private User mUser;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info2);
        anhxa();
        requestQueue = MyVolley.getInstance(getApplicationContext()).getRequestQueue();
        Intent intent = getIntent();
        intent.setExtrasClassLoader(getClassLoader());
        mUser  = intent.getParcelableExtra("user");
        initOption(mUser);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loginHandle(LoginActivity.urlLogin,mUser.getAccount().getUsername(),mUser.getAccount().getPassword(),(mUser.getAccount().getUserId() != null) ? mUser.getAccount().getUserId() : "",null, this );

    }

    private void initOption(User user) {
        mListOption.clear();
        if (user != null) {
            String name = user.getName();
            String avatarUrl = user.getAvatarUrl();
            if (user.getName() != null)
                mNameUser.setText(name);
            if (user.getAvatarUrl() != null)
                Glide.with(this).load(Uri.parse(avatarUrl)).into(mAvatarUser);
            mListOption.add("Thông tin người dùng");
            mListOption.add("Quản lý coupon");
            mListOption.add("Đăng xuất");
            mInfoUserAdapter = new InfoUserAdapter(this, mListOption, this);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mInfoUserAdapter);
        } else {
            Toast.makeText(this, "user null", Toast.LENGTH_SHORT).show();
        }
    }

    private void anhxa() {
        mAvatarUser = findViewById(R.id.avatar_info);
        mNameUser = findViewById(R.id.name_user_info);
        mRecyclerView = findViewById(R.id.option_info);
        mListOption = new ArrayList<>();

    }

    private void loginHandle(final String url, final String userName, final String passWord, final String userId, final LoginFBCallBack loginFBCallBack, final InfoActivity infoActivity){
        Map<String,String> params = new HashMap<>();
        params.put("username",userName);
        params.put("password",passWord);
        params.put("userId",userId);
        final JSONObject jsonObject = new JSONObject(params);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");
                    String data = response.getString("data");
                    mUser = new Gson().fromJson(data,User.class);
                    infoActivity.initOption(mUser);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(userId.isEmpty() && loginFBCallBack == null){
                    Toast.makeText(InfoActivity.this, "đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
                else{
                    loginFBCallBack.registerNewAccount(userId);
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        request.setRetryPolicy( new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

    }

    @Override
    public void clickedItem(View v, int position) {
        switch (position) {
            case 0:
                displayInfoUser(mUser);
                break;
            case 1:
                manageCoupon(mUser);
                break;
            case 2:
                logOut();
                break;
            default:

        }
    }

    private void logOut() {
    }

    private void displayInfoUser(User user) {
        Intent intent = new Intent(this,DetailInfoUser.class);
        intent.putExtra("userDetail",user);
        startActivity(intent);
    }

    private void manageCoupon(User user){
        Intent intent = new Intent(this, ManageCoupon.class);
        intent.putExtra("manageCouponByUser",user);
        startActivity(intent);
    }
}
