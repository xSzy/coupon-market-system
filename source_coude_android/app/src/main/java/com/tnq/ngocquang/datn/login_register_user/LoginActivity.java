package com.tnq.ngocquang.datn.login_register_user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.model.Account;
import com.tnq.ngocquang.datn.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tnq.ngocquang.datn.constant.Constant;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private EditText mUserName;
    private EditText mPassWord;
    private LoginButton mLoginButton;
    private String url = Constant.hostname + Constant.loginAPI;
    private String urlUpdateUser = Constant.hostname + Constant.updateUserAPI;
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

        callbackManager = CallbackManager.Factory.create();
        mLoginButton.setReadPermissions(Arrays.asList("public_profile","email","user_gender","user_birthday"));
        loginByFacebook();
    }
    private void anhxa(){
        mUserName = findViewById(R.id.usernameLog);
        mPassWord = findViewById(R.id.passwordLog);
        mLoginButton = findViewById(R.id.login_button);
    }

    private void loginByFacebook() {
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
            loginHandle(url,username, password, "");
        }
    }

    private void loginHandle(final String url, final String userName, final String passWord, final String userId){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                        isOK = true;
                        Toast.makeText(LoginActivity.this, "đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AAA", "login : "+ error.toString());
                isOK = false;
                Toast.makeText(LoginActivity.this, "đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
//        request.setRetryPolicy( new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    public void registerAccount(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
