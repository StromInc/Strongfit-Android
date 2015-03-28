package com.strom.strongfit.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.strom.strongfit.fragments.ArticulosFragment;
import com.strom.strongfit.fragments.ChatFragment;

/**
 * Created by USER on 27/03/2015.
 */
public class CustomPagerAdapter extends FragmentStatePagerAdapter {
    String[] titles;
    Fragment[] fragments;
    public CustomPagerAdapter(FragmentManager fm, String[] titles) {

        super(fm);
        this.titles = titles;
        fragments = new Fragment[]{
                new ArticulosFragment(),
                new ChatFragment()
        };
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return titles[position];
            case 1:
                return titles[position];
        }
        return "";
    }
}
