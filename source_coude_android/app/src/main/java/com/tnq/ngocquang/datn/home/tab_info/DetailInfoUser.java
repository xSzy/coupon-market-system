package com.tnq.ngocquang.datn.home.tab_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.adapter.InfoUserDetailAdapter;
import com.tnq.ngocquang.datn.login_register_user.LoginActivity;
import com.tnq.ngocquang.datn.login_register_user.LoginFBCallBack;
import com.tnq.ngocquang.datn.model.InfoUser;
import com.tnq.ngocquang.datn.model.User;
import com.tnq.ngocquang.datn.support.MyVolley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailInfoUser extends AppCompatActivity {

    private ImageView mAvatar;
    private RecyclerView mRecyclerView;
    private ArrayList<InfoUser> mListInfoUser;
    private User mUser;
    private InfoUserDetailAdapter mAdapter;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info_user);
        anhxa();
        requestQueue = MyVolley.getInstance(getApplicationContext()).getRequestQueue();
        Intent intent = getIntent();
        mUser = intent.getParcelableExtra("userDetail");
        Log.d("AAA detail",mUser.getGender() + "");
        Log.d("AAA detail",mUser.getAccount().getUsername() + "");
        Log.d("AAA detail",mUser.getDob().toString() + "");
        if (mUser != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new InfoUserDetailAdapter(this, mListInfoUser);
            mRecyclerView.setAdapter(mAdapter);
            initInfoUser(mUser);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loginHandle(LoginActivity.urlLogin,mUser.getAccount().getUsername(),mUser.getAccount().getPassword(),(mUser.getAccount().getUserId() != null) ? mUser.getAccount().getUserId() : "",null, this );
    }

    private void initInfoUser(User user) {
        mListInfoUser.clear();
        if (user.getAvatarUrl() != null) {
            Glide.with(this).load(Uri.parse(user.getAvatarUrl())).into(mAvatar);
        }
        if (user.getName() != null) {
            mListInfoUser.add(new InfoUser("Họ và tên ", user.getName()));
        }
        if (user.getDob() != null) {
            String content = String.valueOf(DateFormat.format("dd/MM/yyyy", user.getDob()));
            mListInfoUser.add(new InfoUser("Ngày sinh ", content));
        }
        if (user.getEmail() != null) {
            mListInfoUser.add(new InfoUser("Email ", user.getEmail()));
        }
        if (user.getAddress() != null) {
            mListInfoUser.add(new InfoUser("Địa chỉ ", user.getAddress()));
        }
        if (user.getPhoneNumber() != null) {
            mListInfoUser.add(new InfoUser("Số điện thoại ", user.getPhoneNumber()));
        }
        if (user.getCitizenId() != null) {
            mListInfoUser.add(new InfoUser("Số chứng minh thư ", user.getCitizenId()));
        }
        if (user.getGender() == 1 || user.getGender() == 2) {
            mListInfoUser.add(new InfoUser("Giới tính ", (user.getGender() == 1) ? "Nam" : "Nữ"));
        }
        mListInfoUser.add(new InfoUser("Chức vụ ", String.valueOf(user.getRole())));
        mAdapter.notifyDataSetChanged();

    }


    private void anhxa() {
        mAvatar = findViewById(R.id.avatar_info_detail);
        mRecyclerView = findViewById(R.id.list_info_user_detail);
        mListInfoUser = new ArrayList<>();
    }

    public void editInfoUser(View view) {
        Intent intent = new Intent(this, EditInfoUser.class);
        intent.putExtra("editinfouser",mUser);
        startActivity(intent);
    }

    private void loginHandle(final String url, final String userName, final String passWord, final String userId, final LoginFBCallBack loginFBCallBack, final DetailInfoUser detailInfoUser){
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
                    Log.d("AAA",data);
                    mUser = new Gson().fromJson(data,User.class);
                    detailInfoUser.initInfoUser(mUser);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(userId.isEmpty() && loginFBCallBack == null){
                    Log.d("AAA", "login : "+ error.toString());
                    Toast.makeText(DetailInfoUser.this, "đăng nhập thất bại", Toast.LENGTH_SHORT).show();
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
}
