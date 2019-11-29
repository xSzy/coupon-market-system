package com.tnq.ngocquang.datn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.interface_.RecyclerViewClickListener;

import java.util.ArrayList;

public class ImageProductAdapter  extends RecyclerView.Adapter<ImageProductAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<ImageView> listImage;
    private RecyclerViewClickListener mClickItemListener;

    public ImageProductAdapter(Context mContext, ArrayList<ImageView> listImage, RecyclerViewClickListener mClickItemListener) {
        this.mContext = mContext;
        this.listImage = listImage;
        this.mClickItemListener = mClickItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_image_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTo(listImage.get(position));
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageProduct ;
        private ImageView removeImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.image_product);
            removeImage = itemView.findViewById(R.id.remove_image_product);
            removeImage.setOnClickListener(this);

        }

        public void bindTo(ImageView img){
            imageProduct.setImageDrawable(img.getDrawable());
        }

        @Override
        public void onClick(View view) {
            mClickItemListener.clickedItem(view,getAdapterPosition());
        }
    }
}
