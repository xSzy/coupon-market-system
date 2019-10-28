package com.tnq.ngocquang.datn.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.adapter.CategoryAdapter;
import com.tnq.ngocquang.datn.model.Category;

import java.util.ArrayList;

public class TabHome extends Fragment {

    public TabHome() {
    }

    private RecyclerView mRecyclerView;
    private View mView;
    private ArrayList<Category> mCategoryData;


    private void anhxa() {
        mRecyclerView = mView.findViewById(R.id.recyclerViewListCategory);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_tab_home, container, false);
        anhxa();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mView.getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mCategoryData = new ArrayList<>();
        //
        for (int i = 0 ; i < 10 ;i ++){
            Category category1 = new Category();
            category1.setName("" + i);
            mCategoryData.add(category1);
        }
        CategoryAdapter adapter = new CategoryAdapter(mCategoryData,mView.getContext());
        mRecyclerView.setAdapter(adapter);
        return mView;
    }
}
