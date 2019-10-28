package com.tnq.ngocquang.datn.adapter;

import android.content.Context;
import android.content.res.TypedArray;
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
import com.tnq.ngocquang.datn.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Category> mListCategory;
    private Context mContext;

    public CategoryAdapter(ArrayList<Category> mListCategory, Context mContext) {
        this.mListCategory = mListCategory;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_category,parent,false);
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

        public void bindTo(Category category){
            String urlIcon = category.getIcon();
            String title = category.getName();

            // because, this is demo so .....
            TypedArray image = view.getResources().obtainTypedArray(R.array.demo_icon_category);
            Glide.with(mContext).load(image.getResourceId(0,0)).into(mIcon);
            image.recycle();
            /////
            mTitle.setText(category.getName());

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext,"" + mTitle.getText(),Toast.LENGTH_SHORT).show();
        }
    }
}



