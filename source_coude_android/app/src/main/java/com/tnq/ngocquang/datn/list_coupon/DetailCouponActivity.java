package com.tnq.ngocquang.datn.list_coupon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.model.Coupon;

public class DetailCouponActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_coupon);
        anhxa();
        Intent intent = getIntent();
        Coupon coupon = intent.getParcelableExtra("coupon");
        mTitle.setText(coupon.getTitle());
        mDescription.setText(coupon.getDescription());
    }

    private void anhxa(){
        mTitle = findViewById(R.id.title_detail_coupon);
        mDescription = findViewById(R.id.description_detail_coupon);
    }
}
