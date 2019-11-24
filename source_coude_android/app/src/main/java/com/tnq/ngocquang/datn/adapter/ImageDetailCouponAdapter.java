package com.tnq.ngocquang.datn.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.constant.Constant;
import com.tnq.ngocquang.datn.model.ProductImage;

import java.util.ArrayList;

public class ImageDetailCouponAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<ProductImage> mlistImage;


    public ImageDetailCouponAdapter(Context mContext, ArrayList<ProductImage> mlistImage) {
        this.mContext = mContext;
        this.mlistImage = mlistImage;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_image_coupon, container, false);
        ImageView imageCoupon = view.findViewById(R.id.image_detail_coupon);
        if(mlistImage != null){
            String urlImageCoupon = Constant.hostImage;
            urlImageCoupon += mlistImage.get(position).getImage();
            Log.d("image detail ",urlImageCoupon);
            Glide.with(mContext).load(urlImageCoupon).into(imageCoupon);
        }else{
            // demo
            TypedArray img = view.getResources().obtainTypedArray(R.array.demo_icon_category);
            Glide.with(mContext).load(img.getResourceId(0,0)).into(imageCoupon);
            img.recycle();
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mlistImage.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
