package com.vladimirov.etsy.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vladimirov.etsy.Fragment.SavedProductFragment;
import com.vladimirov.etsy.Fragment.SearchFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 2;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SearchFragment();
            case 1:
                return new SavedProductFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Search";
        } else {
            return "Saved product";
        }

    }

}