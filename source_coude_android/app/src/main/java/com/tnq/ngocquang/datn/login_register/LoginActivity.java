package com.tnq.ngocquang.datn.login_register;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tnq.ngocquang.datn.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mPassWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserName = findViewById(R.id.username);
        mPassWord = findViewById(R.id.password);
    }

    public void loginAccount(View view) {
        String url = "http://192.168.1.20:8080/cms/user/login";
        String username = mUserName.getText().toString().trim();
        String password = mPassWord.getText().toString().trim();
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "bạn chưa nhập tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else{

            loginHandle(url);
        }

    }

    private void loginHandle(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Map<String,String> params = new HashMap<>();
        params.put("username",mUserName.getText().toString().trim());
        params.put("password",mPassWord.getText().toString().trim());


        JSONObject jsonObject = new JSONObject(params);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String status = response.getString("status");
                    if(status.equals("success")){
                        Toast.makeText(LoginActivity.this, "thành công", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this, "thất bại", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                return headers;
            }
        };

        requestQueue.add(request);
    }


    public void registerAccount(View view) {
    }
}
