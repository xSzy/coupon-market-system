package com.tnq.ngocquang.datn.home.tab_info.manage_coupon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.model.User;
import com.tnq.ngocquang.datn.support.ConvertRoleUser;

public class ManageCoupon extends AppCompatActivity {

    private User mUser;
    private TextView mNameUser;
    private TextView mRoleUser;
    private ImageView mAvatarUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_coupon);
        anhxa();
        mUser = getIntent().getParcelableExtra("manageCouponByUser");
        if(mUser != null){
            initDataUser(mUser);
        }
    }

    private void initDataUser(User user){
        mNameUser.setText(user.getName());
        mRoleUser.setText(ConvertRoleUser.convert(user.getRole()));
        Glide.with(this).load(user.getAvatarUrl()).into(mAvatarUser);
    }

    private void anhxa() {
        mNameUser = findViewById(R.id.nameuser_managecoupon);
        mRoleUser = findViewById(R.id.roleuser_managecoupon);
        mAvatarUser = findViewById(R.id.avatar_managecoupon);
    }


    public void addCouponNew(View view) {
        Intent intent = new Intent(this,AddCouponNew.class);
        intent.putExtra("addCouponByUser",mUser);
        startActivity(intent);
    }
}
