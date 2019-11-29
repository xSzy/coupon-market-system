package com.tnq.ngocquang.datn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.interface_.RecyclerViewClickListener;

import java.util.ArrayList;

public class InfoUserAdapter extends RecyclerView.Adapter<InfoUserAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mListOption;
    private RecyclerViewClickListener mClickListener;

    public InfoUserAdapter(Context mContext, ArrayList<String> mListOption, RecyclerViewClickListener listener) {
        this.mContext = mContext;
        this.mListOption = mListOption;
        this.mClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_option_info,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String option = mListOption.get(position);
        holder.bindTo(option);
    }

    @Override
    public int getItemCount() {
        return mListOption.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameOption;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameOption = itemView.findViewById(R.id.name_option_info);
            itemView.setOnClickListener(this);
        }

        public void bindTo(String option){
            mNameOption.setText(option);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mClickListener.clickedItem(view,position);
        }
    }
}
