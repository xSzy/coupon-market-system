package com.tnq.ngocquang.datn.list_coupon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.model.Coupon;

public class DetailCouponActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mDescription;
    private ImageView mImage;
    private ImageView mShare;
    private TextView mCouponValue;
    private TextView mClickCount;
    private TextView mAddress;
    private ImageView mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_coupon);
        anhxa();
        Intent intent = getIntent();
        Coupon coupon = intent.getParcelableExtra("coupon");
        Log.d("AAA",coupon.getTitle());
        if (coupon != null) {
            initData(coupon);
        }
    }

    private void initData(Coupon coupon) {
        mTitle.setText(coupon.getTitle());
        mDescription.setText(coupon.getDescription());
        // this is demo...
        TypedArray image = getResources().obtainTypedArray(R.array.demo_icon_category);
        Glide.with(this).load(image.getResourceId(0, 0)).into(mImage);
        image.recycle();

        int type = coupon.getType();
        double value = coupon.getValue();
        int valueType = coupon.getValuetype();
        String couponValue = "- ";
        // sale off by percent
        if (type == 1) {
            couponValue += value + " %";
        }
        // sale off by money
        else if (type == 2) {
            if (valueType == 0) {
                couponValue += (int)(value * 1000) + " Vnd";
            } else if (valueType == 1) {
                couponValue += value + " Usd";
            }
        }
        mCouponValue.setText(couponValue);
        mClickCount.setText(coupon.getClickCount() + " lượt xem");
        if(coupon.getCreateBy() != null){
            mAddress.setText(coupon.getCreateBy().getAddress());
        }else{
            mAddress.setText("no address");
        }
    }

    private void anhxa() {
        mTitle = findViewById(R.id.coupon_detail_title);
        mDescription = findViewById(R.id.description_detail_coupon);
        mImage = findViewById(R.id.image_detail_coupon);
        mShare = findViewById(R.id.share_detail_coupon);
        mCouponValue = findViewById(R.id.coupon_detail_value);
        mClickCount = findViewById(R.id.count_click_detail_coupon);
        mAddress = findViewById(R.id.address_detail_coupon);
        mMap = findViewById(R.id.map_detail_coupon);
    }

    public void shareCoupon(View view) {
        Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
    }

    public void mapOfCoupon(View view) {
        Toast.makeText(this, "map", Toast.LENGTH_SHORT).show();
    }

    public void callToAuthor(View view) {
        Toast.makeText(this, "call", Toast.LENGTH_SHORT).show();
    }
}
