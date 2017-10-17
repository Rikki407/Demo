package com.kirayepay.kirayepay101.Navigation.Home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
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
    private TabLayout homeTabLayout;

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
        homeViewPager.setOffscreenPageLimit(3);
        homeTabLayout = (TabLayout) v.findViewById(R.id.home_tabs);
        homeViewPager.setAdapter(homePagerAdapter);
        homeTabLayout.setupWithViewPager(homeViewPager);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
