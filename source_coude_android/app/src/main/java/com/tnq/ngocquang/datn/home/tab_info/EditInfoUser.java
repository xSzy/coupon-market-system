package com.tnq.ngocquang.datn.home.tab_info;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.google.gson.JsonObject;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.constant.Constant;
import com.tnq.ngocquang.datn.model.User;
import com.tnq.ngocquang.datn.support.MyVolley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditInfoUser extends AppCompatActivity {

    private User mUser;
    private ImageView mAvatar;
    private EditText mName;
    private EditText mDob;
    private EditText mAddress;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mCitizenId;
    private EditText mGender;
    private EditText mRole;
    private Calendar mCalendar;
    private RequestQueue mRequestQueue;
    private final String UPDATE_USER_URL = Constant.hostname + Constant.updateUserAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info_user);
        anhxa();
        mRequestQueue = MyVolley.getInstance(getApplicationContext()).getRequestQueue();
        mUser = getIntent().getParcelableExtra("editinfouser");
        mCalendar = Calendar.getInstance();
        mCalendar.set(mUser.getDob().getYear() + 1900,mUser.getDob().getMonth(),mUser.getDob().getDate());
        if (mUser != null) {
            initData(mUser);
            mDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b == true) {
                        new DatePickerDialog(view.getContext(), retrieveDob(), mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                }
            });
            mDob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(view.getContext(), retrieveDob(), mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }
    }

    private DatePickerDialog.OnDateSetListener retrieveDob() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, day);
                mDob.setText(DateFormat.format("dd-MM-yyyy", mCalendar));
            }
        };
        return dateSetListener;
    }

    private void initData(User user) {
        if (user.getAvatarUrl() != null) {
            Glide.with(this).load(user.getAvatarUrl()).into(mAvatar);
        }
        if (user.getName() != null) {
            mName.setText(user.getName() + "");
        }
        if(user.getDob() != null){
            mDob.setText(DateFormat.format("dd-MM-yyyy",user.getDob()));
        }
        if (user.getAddress() != null) {
            mAddress.setText(user.getAddress().toString() + "");
        }
        if (user.getEmail() != null) {
            mEmail.setText(user.getEmail() + "");
        }
        if (user.getPhoneNumber() != null) {
            mPhone.setText(user.getPhoneNumber() + "");
        }
        if (user.getCitizenId() != null) {
            mCitizenId.setText(user.getCitizenId() + "");
        }
        if (user.getGender() == 1 || user.getGender() == 2) {
            mGender.setText((user.getGender() == 1) ? "Nam" : "Nữ");
        }
        mRole.setText(user.getRole() + "");

    }

    private void anhxa() {
        mAvatar = findViewById(R.id.avatar_info_edit);
        mName = findViewById(R.id.name_info_edit);
        mDob = findViewById(R.id.dob_info_edit);
        mEmail = findViewById(R.id.email_info_edit);
        mAddress = findViewById(R.id.address_info_edit);
        mPhone = findViewById(R.id.phone_info_edit);
        mCitizenId = findViewById(R.id.citiId_info_edit);
        mGender = findViewById(R.id.gender_info_edit);
        mRole = findViewById(R.id.role_info_edit);
    }

    public void doneEditInfoUser(View view) {
        String regexEmail = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(mEmail.getText().toString());
        User user = new User();
        if (mName.getText().toString().equals("") ||
                mDob.getText().toString().equals("") ||
                mEmail.getText().toString().equals("") ||
                mAddress.getText().toString().equals("") ||
                mPhone.getText().toString().equals("") ||
                mCitizenId.getText().toString().equals("") ||
                mGender.getText().toString().equals("") ||
                mRole.getText().toString().equals("")) {
            Toast.makeText(this, "bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else if(!matcher.matches()){
            Toast.makeText(this, "sai định dạng email", Toast.LENGTH_SHORT).show();
        }
        else {
            user.setId(mUser.getId());
            user.setAvatarUrl(mUser.getAvatarUrl());
            user.setAccount(mUser.getAccount());
            user.setName(mName.getText().toString());
            Date dob = new Date(mCalendar.get(Calendar.YEAR) - 1900, mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
            user.setDob(dob);
            user.setEmail(mEmail.getText().toString());
            user.setAddress(mAddress.getText().toString());
            user.setPhoneNumber(mPhone.getText().toString());
            user.setCitizenId(mCitizenId.getText().toString());
            Log.d("AAA gender : ", mGender.getText().toString().toLowerCase());
            user.setGender((mGender.getText().toString().toLowerCase().equals("nam".trim()) ) ? 1 : 2);
            try {
                user.setRole(Integer.parseInt(mRole.getText().toString()));

            } catch (Exception ex) {
                user.setRole(1);
            }

            HashMap<String,String> accountParams = new HashMap<>();
            accountParams.put("id",String.valueOf(user.getAccount().getId()));
            accountParams.put("username",user.getAccount().getUsername());
            accountParams.put("password",user.getAccount().getPassword());
            accountParams.put("userId",user.getAccount().getUserId());
            JSONObject accountJson = new JSONObject(accountParams);
            JSONObject userJson = new JSONObject();
            try {
                userJson.put("id",String.valueOf(user.getId()));
                userJson.put("account",accountJson);
                userJson.put("avatarUrl",user.getAvatarUrl());
                userJson.put("name",user.getName());
                userJson.put("dob",DateFormat.format("yyyy-MM-dd",user.getDob()));
                userJson.put("email",user.getEmail());
                userJson.put("address",user.getAddress());
                userJson.put("phoneNumber",user.getPhoneNumber());
                userJson.put("citizenId",user.getCitizenId());
                userJson.put("gender",String.valueOf(user.getGender()));
                userJson.put("role",String.valueOf(user.getRole()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("AAA",userJson.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UPDATE_USER_URL, userJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("AAA editinfouser ", response.toString());
                    String status = null;
                    try {
                        status = response.getString("status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(status.equals("success")){
                        Toast.makeText(EditInfoUser.this, "cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("AAA ", error.toString());
                    Toast.makeText(EditInfoUser.this, Constant.errorMessage, Toast.LENGTH_SHORT).show();
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

            mRequestQueue.add(request);


        }

    }
}
