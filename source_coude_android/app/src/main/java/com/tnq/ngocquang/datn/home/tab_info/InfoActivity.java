package com.tnq.ngocquang.datn.home.tab_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.adapter.InfoUserAdapter;
import com.tnq.ngocquang.datn.interface_.RecyclerViewClickListener;
import com.tnq.ngocquang.datn.model.Account;
import com.tnq.ngocquang.datn.model.User;

import java.util.ArrayList;
import java.util.Date;

public class InfoActivity extends AppCompatActivity implements RecyclerViewClickListener {

    private ArrayList<String> mListOption;
    private RecyclerView mRecyclerView;
    private InfoUserAdapter mInfoUserAdapter;
    private TextView mNameUser;
    private ImageView mAvatarUser;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info2);
        anhxa();
        Bundle bundle = getIntent().getExtras();
        mUser  = bundle.getParcelable("user");
//        Log.d("AAA",mUser.getGender() + "");
        Account account = bundle.getParcelable("account");
        String dob = bundle.getString("dob");
        Log.d("AAA",account.getUsername()+ "");
        Log.d("AAA",dob.toString() + "");
        initOption(mUser);
    }

    private void initOption(User user) {
        mListOption = new ArrayList<>();
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
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        switch (position) {
            case 0:
                displayInfoUser(mUser);
                break;
            case 1:
                manageCoupon();
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

    private void manageCoupon(){

    }
}
