package com.tnq.ngocquang.datn.home.tab_info;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.login_register_user.LoginActivity;

public class TabInfo extends Fragment {

    private View mView;
    private FloatingActionButton mNextLoginActivity;

    public TabInfo() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mView = inflater.inflate(R.layout.fragment_tab_info,container,false);
         anhxa();
         mNextLoginActivity.setOnClickListener(openLoginActivity());
        return mView;
    }

    private void anhxa() {
        mNextLoginActivity = mView.findViewById(R.id.next_login_activity);
    }


    public View.OnClickListener openLoginActivity(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mView.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        };
    }


}
