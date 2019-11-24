package com.tnq.ngocquang.datn.adapter;

import android.animation.BidirectionalTypeConverter;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.constant.Constant;
import com.tnq.ngocquang.datn.list_coupon.DetailCouponActivity;
import com.tnq.ngocquang.datn.list_coupon.ListCoupon;
import com.tnq.ngocquang.datn.model.Coupon;
import com.tnq.ngocquang.datn.support.ConvertCouponValue;

import java.util.ArrayList;
import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Coupon> mListCoupon;

    public CouponAdapter(Context mContext, ArrayList<Coupon> mListCoupon) {
        this.mContext = mContext;
        this.mListCoupon = mListCoupon;
    }

    @NonNull
    @Override
    public CouponAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
        View view = layoutInflater.inflate(R.layout.list_coupon,parent,false);
        return new ViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponAdapter.ViewHolder holder, int position) {
        Coupon coupon = mListCoupon.get(position);
        holder.bindTo(coupon);
    }

    @Override
    public int getItemCount() {
        return mListCoupon.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle;
        private ImageView mImageCoupon;
        private TextView mCouponValue;
        private CouponAdapter adapter;
        private View view;

        public ViewHolder(@NonNull View itemView, final CouponAdapter adapter) {
            super(itemView);
            this.view = itemView;
            mTitle = itemView.findViewById(R.id.coupon_title);
            mImageCoupon = itemView.findViewById(R.id.image_coupon);
            mCouponValue = itemView.findViewById(R.id.coupon_value);
            this.adapter = adapter;
            view.setOnClickListener(this);
        }

        void bindTo(Coupon coupon){
            mTitle.setText(coupon.getTitle().toString() + "");
            int type = coupon.getType();
            double value = coupon.getValue();
            int valueType = coupon.getValuetype();
            String couponValue = ConvertCouponValue.convert(type,value,valueType);
            mCouponValue.setText(couponValue);
            String urlImageCoupon = Constant.hostImage;
            urlImageCoupon += coupon.getProduct().getProductImages().get(0).getImage();
            Log.d("AAA",urlImageCoupon);
            if(urlImageCoupon != null){
                Glide.with(mContext).load(urlImageCoupon).into(mImageCoupon);
            }else{
                // demo
                TypedArray image = itemView.getResources().obtainTypedArray(R.array.demo_icon_category);
                Glide.with(mContext).load(image.getResourceId(0,0)).into(mImageCoupon);
                image.recycle();
            }
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Coupon coupon = mListCoupon.get(position);
            //Toast.makeText(mContext, "" + coupon.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, DetailCouponActivity.class);
            intent.putExtra("coupon",coupon);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, Pair.create((View)mImageCoupon,"image_coupon"));
                mContext.startActivity(intent,options.toBundle());

            }
        }
    }
}
