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

import com.bumptech.glide.Glide;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.adapter.InfoUserDetailAdapter;
import com.tnq.ngocquang.datn.model.InfoUser;
import com.tnq.ngocquang.datn.model.User;

import java.util.ArrayList;
import java.util.Date;

public class DetailInfoUser extends AppCompatActivity {

    private ImageView mAvatar;
    private RecyclerView mRecyclerView;
    private ArrayList<InfoUser> mListInfoUser;
    private User mUser;
    private InfoUserDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info_user);
        anhxa();
        Intent intent = getIntent();
        mUser = intent.getParcelableExtra("userDetail");
        Log.d("AAA",mUser.getGender() + "");
        Log.d("AAA",mUser.getAccount().getUsername() + "");
        Log.d("AAA",mUser.getDob().toString() + "");
        if (mUser != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new InfoUserDetailAdapter(this, mListInfoUser);
            mRecyclerView.setAdapter(mAdapter);
            initInfoUser(mUser);
        }
    }

    private void initInfoUser(User user) {
        if (user.getAvatarUrl() != null) {
            Glide.with(this).load(Uri.parse(user.getAvatarUrl())).into(mAvatar);
        }
        if (user.getName() != null) {
            mListInfoUser.add(new InfoUser("Họ và tên ", user.getName()));
        }
        if (user.getDob() != null) {
//            Date dob = new Date()
            Log.d("AAA", user.getDob().toString() + "");

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
        mAdapter.notifyDataSetChanged();

    }


    private void anhxa() {
        mAvatar = findViewById(R.id.avatar_info_detail);
        mRecyclerView = findViewById(R.id.list_info_user_detail);
        mListInfoUser = new ArrayList<>();
    }

    public void editInfoUser(View view) {
        Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
    }
}
