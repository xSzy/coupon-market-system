package com.tnq.ngocquang.datn.home.tab_search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.adapter.CouponAdapter;
import com.tnq.ngocquang.datn.constant.Constant;
import com.tnq.ngocquang.datn.model.Coupon;

import java.util.ArrayList;

public class TabSearch extends Fragment {

    private EditText mEdtSearch;
    private static View mView;
    private static RecyclerView mRecyclerView;
    private static ArrayList<Coupon> mListCouponSearch;
    private CouponAdapter mAdapter;
    private LinearLayoutManager mlayoutManager;
    private static final String URL_SEARCH_COUPON = Constant.hostname + Constant.findByNameAPI;
    public TabSearch() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab_search,container,false);
        anhxa();

        return mView;
    }
    private void anhxa(){
        mEdtSearch = mView.findViewById(R.id.search_text_coupon);
        mRecyclerView = mView.findViewById(R.id.recyclerViewCouponBySearch);
    }


}
