package com.tnq.ngocquang.datn.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tnq.ngocquang.datn.home.tab_home.TabHome;
import com.tnq.ngocquang.datn.home.tab_info.TabInfo;
import com.tnq.ngocquang.datn.home.tab_search.TabSearch;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    public PagerAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm, numberOfTabs);
        this.mNumOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new TabHome();
            case 1 : return new TabInfo();
            case 2 : return new TabSearch();
            default: return null;

    }


}

    @Override
    public int getCount() {
        return this.mNumOfTabs;
    }
}
