package com.tnq.ngocquang.datn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.model.InfoUser;

import java.util.ArrayList;

import static com.tnq.ngocquang.datn.R.id.info;
import static com.tnq.ngocquang.datn.R.id.list_info_user_detail;

public class InfoUserDetailAdapter extends RecyclerView.Adapter<InfoUserDetailAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<InfoUser> mList;

    public InfoUserDetailAdapter(Context mContext, ArrayList<InfoUser> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_info_user_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTo(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title_info_user_detail);
            mContent = itemView.findViewById(R.id.content_info_user_detail);
        }

        public void bindTo(InfoUser infoUser) {
            mTitle.setText(infoUser.getTitle());
            mContent.setText(infoUser.getContent());
        }
    }
}
