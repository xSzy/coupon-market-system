package com.tnq.ngocquang.datn.login_register_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.tnq.ngocquang.datn.InfoActivity;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.model.Account;
import com.tnq.ngocquang.datn.model.Coupon;
import com.tnq.ngocquang.datn.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tnq.ngocquang.datn.constant.Constant;
import com.tnq.ngocquang.datn.support.MyVolley;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private EditText mUserName;
    private EditText mPassWord;
    private LoginButton mLoginButton;
    private RequestQueue requestQueue;
    private String url = Constant.hostname + Constant.loginAPI;
    private String urlUpdateUser = Constant.hostname + Constant.updateUserAPI;
    private LoginFBCallBack mLoginFB;
    private boolean isOK;
    @Override
    protected void onStart() {
       // LoginManager.getInstance().logOut();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        requestQueue = MyVolley.getInstance(getApplicationContext()).getRequestQueue();
        callbackManager = CallbackManager.Factory.create();
        mLoginButton.setReadPermissions(Arrays.asList("public_profile","email","user_gender","user_birthday"));
        mLoginFB = new LoginFBCallBack(){
            @Override
            public void onSuccess(User user) {
                Log.d("AAA", user.getAccount().getUserId() );

                loginHandle(url,"","",user.getAccount().getUserId(),mLoginFB);
            }

            @Override
            public void registerNewAccount(String userId) {

            }
        };

        loginByFacebook(mLoginFB);
    }
    private void anhxa(){
        mUserName = findViewById(R.id.usernameLog);
        mPassWord = findViewById(R.id.passwordLog);
        mLoginButton = findViewById(R.id.login_button);
    }

    private void loginByFacebook(final LoginFBCallBack mLoginFB) {
        final User user = new User();
        final Account account = new Account();
        mLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mLoginButton.setVisibility(View.INVISIBLE);

                        try {
                            account.setUserId(object.getString("id"));
                            user.setAccount(account);
                            user.setName(object.getString("name"));
                            user.setGender((object.getString("gender").equals("male")? 1 : 2));
                            String[] dob = object.getString("birthday").split("/");
                            Date date = new Date(Integer.parseInt(dob[2]), Integer.parseInt(dob[0]), Integer.parseInt(dob[1]) );
                            user.setDob(date);
                            mLoginFB.onSuccess(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,name,email,birthday,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void loginAccount(View view) {
        String username = mUserName.getText().toString().trim();
        String password = mPassWord.getText().toString().trim();
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "bạn chưa nhập tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else{
            loginHandle(url,username, password, "1201897076688077",null);
        }
    }

    private void loginHandle(final String url, final String userName, final String passWord, final String userId, final LoginFBCallBack loginFBCallBack){
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
                    if(status.equals("success")){

                        User user = new Gson().fromJson(response.toString(),User.class);
                        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(userId.isEmpty() && loginFBCallBack == null){
                    Log.d("AAA", "login : "+ error.toString());
                    Toast.makeText(LoginActivity.this, "đăng nhập thất bại", Toast.LENGTH_SHORT).show();
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

    public void registerAccount(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
