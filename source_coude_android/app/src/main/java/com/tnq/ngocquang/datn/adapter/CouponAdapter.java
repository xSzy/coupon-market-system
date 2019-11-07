package com.tnq.ngocquang.datn.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.list_coupon.DetailCouponActivity;
import com.tnq.ngocquang.datn.list_coupon.ListCoupon;
import com.tnq.ngocquang.datn.model.Coupon;

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
        private TextView mDescription;
        private CouponAdapter adapter;
        private View view;

        public ViewHolder(@NonNull View itemView, final CouponAdapter adapter) {
            super(itemView);
            this.view = itemView;
            mTitle = itemView.findViewById(R.id.couponTitle);
            mDescription = itemView.findViewById(R.id.couponDescription);
            this.adapter = adapter;
            view.setOnClickListener(this);
        }

        void bindTo(Coupon coupon){
            mTitle.setText(coupon.getTitle().toString() + "");
            mDescription.setText(coupon.getDescription().toString() + "");

        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Coupon coupon = mListCoupon.get(position);
            //Toast.makeText(mContext, "" + coupon.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, DetailCouponActivity.class);
            intent.putExtra("coupon",coupon);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, Pair.create((View)mTitle,"title_coupon"));
                mContext.startActivity(intent,options.toBundle());

            }
        }
    }
}
