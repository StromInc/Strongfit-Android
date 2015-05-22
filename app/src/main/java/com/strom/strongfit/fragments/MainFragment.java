package com.strom.strongfit.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strom.strongfit.R;
import com.strom.strongfit.adapters.CustomPagerAdapter;
import com.strom.strongfit.utils.SlidingTabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private SlidingTabLayout mSilidingTabLayout;
    private CustomPagerAdapter customPagerAdapter;
    private static final String TAG = MainFragment.class.getSimpleName();
    private ViewPager viewPager;
    private Fragment[] fragments;
    private String[] titles;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager_paciente);
        mSilidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        titles = getResources().getStringArray(R.array.titles_paciente);
        fragments = new Fragment[]{
            new ConsumoFragment(),
            new DatosFragment()
        };
        customPagerAdapter = new CustomPagerAdapter(getActivity().getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(customPagerAdapter);
        mSilidingTabLayout.setDistributeEvenly(true);
        mSilidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accent_color);
            }
        });
        mSilidingTabLayout.setViewPager(viewPager);
    }
}
