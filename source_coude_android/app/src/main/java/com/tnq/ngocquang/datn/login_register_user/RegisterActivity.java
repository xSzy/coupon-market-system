package com.tnq.ngocquang.datn.login_register_user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.tnq.ngocquang.datn.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tnq.ngocquang.datn.constant.Constant;
import com.tnq.ngocquang.datn.support.MyVolley;

public class RegisterActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;
    Button mRegister;
    static RequestQueue requestQueue;
    public static String url = Constant.hostname + Constant.registerAPI;
    private static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhxa();
        requestQueue = MyVolley.getInstance(getApplicationContext()).getRequestQueue();
        userId = getIntent().getStringExtra("userId");
        if(userId != null){
            mUsername.setVisibility(View.INVISIBLE);
            mPassword.setVisibility(View.INVISIBLE);
        }
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewAccount(view);
            }
        });


    }


    private void anhxa(){
        mUsername = findViewById(R.id.usernameReg);
        mPassword = findViewById(R.id.passwordReg);
        mRegister = findViewById(R.id.registerReg);
    }

    public  void registerNewAccount(View view) {
       if(userId != null){
           registerHandle("","", userId.toString());
           finish();
       }else{
           String username = mUsername.getText().toString().trim();
           String password = mPassword.getText().toString().trim();
           if(username.isEmpty() || password.isEmpty()){
               Toast.makeText(this, "bạn chưa nhập tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
           }else if(!isPassValid(password)){
               Toast.makeText(this, "mật khẩu bạn nhập chưa đúng định dạng", Toast.LENGTH_SHORT).show();
           }
           else{
               registerHandle(username,password, "");

           }
       }
    }

    private static boolean isPassValid(String password){
        String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if(matcher.matches())
            return true;
        return false;
    }

    public void registerHandle(String userName, String passWord, String userId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",userName);
            jsonObject.put("password",passWord);
            jsonObject.put("userId",userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if(status.equals("success")){
                        Toast.makeText(getApplicationContext(), "đăng ký thành công ", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AAA","register : " + error.toString());

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

    public void cancelRegister(View view) {
        finish();
    }
}
