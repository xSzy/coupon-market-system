package com.tnq.ngocquang.datn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.model.Coupon;

import java.util.ArrayList;

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

        public ViewHolder(@NonNull View itemView, CouponAdapter adapter) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.couponTitle);
            mDescription = itemView.findViewById(R.id.couponDescription);
            this.adapter = adapter;
        }

        void bindTo(Coupon coupon){
            mTitle.setText(coupon.getTitle().toString() + "");
            mDescription.setText(coupon.getDescription().toString() + "");
        }


        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            Coupon coupon = mListCoupon.get(position);
            Toast.makeText(mContext, "" + coupon.getTitle(), Toast.LENGTH_SHORT).show();

        }
    }
}
