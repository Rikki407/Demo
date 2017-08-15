package com.kirayepay.kirayepay101.BottomNavFragments.Home;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirayepay.kirayepay101.R;

/**
 * Created by rikki on 8/3/17.
 */

public class HomeFragment extends Fragment
{
    private HomePagerAdapter homePagerAdapter;
    private ViewPager homeViewPager;
    public static HomeFragment newInstance()
    {
        HomeFragment hf= new HomeFragment();
        return hf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        homeViewPager = (ViewPager) v.findViewById(R.id.home_viewpager);
        homeViewPager.setAdapter(homePagerAdapter);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
