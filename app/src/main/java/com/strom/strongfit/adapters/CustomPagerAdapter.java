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
    //Recuperamos los titulos y manejamos los fragments con un array
    //Se podria pasar los titulos y fragmentos por separado
    public CustomPagerAdapter(FragmentManager fm, Fragment[]fragments, String[] titles) {
        super(fm);
        this.titles = titles;
        //Estos son los fragmentos (Las vistas por pestaña)
        this.fragments = fragments;
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
        return titles[position];
    }
}
