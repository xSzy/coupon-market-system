package com.tnq.ngocquang.datn.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.constant.Constant;
import com.tnq.ngocquang.datn.home.tab_home.DetailCategory;
import com.tnq.ngocquang.datn.home.tab_home.TabHome;
import com.tnq.ngocquang.datn.interface_.RecyclerViewClickListener;
import com.tnq.ngocquang.datn.list_coupon.DetailCouponActivity;
import com.tnq.ngocquang.datn.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private RecyclerViewClickListener mRecyclerClickListener;
    private ArrayList<Category> mListCategory;
    private Context mContext;
    private String mId;

    public CategoryAdapter(ArrayList<Category> mListCategory, Context mContext, String mId, RecyclerViewClickListener listener) {
        this.mListCategory = mListCategory;
        this.mContext = mContext;
        this.mId = mId;
        this.mRecyclerClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        holder.bindTo(category);
    }

    @Override
    public int getItemCount() {
        return mListCategory.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mIcon;
        private TextView mTitle;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            mIcon = this.view.findViewById(R.id.iconCategory);
            mTitle = this.view.findViewById(R.id.titleCategory);
            view.setOnClickListener(this);
        }

        public void bindTo(Category category) {
            String urlIcon = Constant.hostIconCategory;
            urlIcon += category.getIcon();
            String title = category.getName();

            if (category.getIcon() != null) {
                Glide.with(mContext).load(urlIcon).into(mIcon);
            } else {
                // because, this is demo so .....
                TypedArray image = view.getResources().obtainTypedArray(R.array.demo_icon_category);
                Glide.with(mContext).load(image.getResourceId(0, 0)).into(mIcon);
                image.recycle();
            }
            /////
            mTitle.setText(category.getName());

        }

        @Override
        public void onClick(View view) {
            if (mId == "TAB_HOME") {
                Category category = mListCategory.get(getAdapterPosition());
                Intent intent = new Intent(mContext, DetailCategory.class);
                intent.putExtra("category", category);
                mContext.startActivity(intent);
            }
            if (mRecyclerClickListener != null) {
                mRecyclerClickListener.clickedItem(view, getAdapterPosition());

            }
        }
    }
}



