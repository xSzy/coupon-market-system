package com.tnq.ngocquang.datn.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.tnq.ngocquang.datn.NotificationActivity;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.login_register_user.LoginActivity;

public class HomeActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    int mPositionPreviousTab;
    @Override
    protected void onRestart() {
        super.onRestart();
        if(mPositionPreviousTab == 1){
            mViewPager.setCurrentItem(0,true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        anhxa();
        mTabLayout.addTab(mTabLayout.newTab().setText("trang chủ").setIcon(R.drawable.ic_home),true);
        mTabLayout.addTab(mTabLayout.newTab().setText("tôi").setIcon(R.drawable.ic_account));
        mTabLayout.addTab(mTabLayout.newTab().setText("tìm kiếm").setIcon(R.drawable.ic_search));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),mTabLayout.getTabCount());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                mPositionPreviousTab = tab.getPosition();
                //  if tab info is displayed.
                if(tab.getPosition() == 1){
                    displayAlert();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void displayAlert(){
        new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage("Bạn phải đăng nhập để sử dụng chức năng")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mViewPager.setCurrentItem(0,true);
                    }
                })
                .show();
    }

    private void anhxa(){
        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_fragment,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.notification :
                Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivity(intent);
        }
        return true;
    }
}
