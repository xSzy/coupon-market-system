package com.tnq.ngocquang.datn.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.tnq.ngocquang.datn.NotificationActivity;
import com.tnq.ngocquang.datn.R;

public class HomeActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager mViewPager;

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
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
