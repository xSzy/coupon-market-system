package com.tnq.ngocquang.datn.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.model.Coupon;

import java.util.ArrayList;

public class TrendCouponAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Coupon> mListCoupon;

    public TrendCouponAdapter(Context mContext, ArrayList<Coupon> mListCoupon) {
        this.mContext = mContext;
        this.mListCoupon = mListCoupon;
    }

    @Override
    public int getCount() {
        return mListCoupon.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_coupon,container,false);
        ImageView image = view.findViewById(R.id.image_coupon);
        TextView couponValue = view.findViewById(R.id.coupon_value);
        TextView couponTitle = view.findViewById(R.id.coupon_title);
        Coupon coupon = mListCoupon.get(position);
        // this is demo
        TypedArray imageT = view.getResources().obtainTypedArray(R.array.demo_icon_category);
        Glide.with(mContext).load(imageT.getResourceId(0,0));
        imageT.recycle();
        int type = coupon.getType();
        double value = coupon.getValue();
        int valueType = coupon.getValuetype();
        String couponV = "- ";
        // sale off by percent
        if (type == 1) {
            couponV += value + " %";
        }
        // sale off by money
        else if (type == 2) {
            if (valueType == 0) {
                couponV += (int)(value * 1000) + " Vnd";
            } else if (valueType == 1) {
                couponV += value + " Usd";
            }
        }
        couponValue.setText(couponV);
        couponTitle.setText(coupon.getTitle());
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
