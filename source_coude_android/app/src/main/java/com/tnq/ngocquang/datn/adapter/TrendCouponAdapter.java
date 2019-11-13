package com.tnq.ngocquang.datn.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.list_coupon.DetailCouponActivity;
import com.tnq.ngocquang.datn.model.Coupon;
import com.tnq.ngocquang.datn.support.ConvertCouponValue;

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
        final ImageView image = view.findViewById(R.id.image_coupon);
        TextView couponValue = view.findViewById(R.id.coupon_value);
        TextView couponTitle = view.findViewById(R.id.coupon_title);
        final Coupon coupon = mListCoupon.get(position);
        // this is demo
        TypedArray imageT = view.getResources().obtainTypedArray(R.array.demo_icon_category);
        Glide.with(mContext).load(imageT.getResourceId(0,0)).into(image);
        imageT.recycle();

        // convert coupon value///////////////////////
        String couponV = ConvertCouponValue.convert(coupon.getType(),coupon.getValue(),coupon.getValuetype());
        ////////////////////////////////////////////
        couponValue.setText(couponV);
        couponTitle.setText(coupon.getTitle());
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailCouponActivity.class);
                intent.putExtra("coupon",coupon);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, Pair.create((View)image,"image_coupon"));
                    mContext.startActivity(intent,options.toBundle());

                }
            }
        });
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
