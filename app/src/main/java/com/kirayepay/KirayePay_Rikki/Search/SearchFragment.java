package com.kirayepay.KirayePay_Rikki.Search;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirayepay.KirayePay_Rikki.R;
import com.kirayepay.KirayePay_Rikki.Search.SearchPagerFragments.SearchAdsFragment;


import java.util.ArrayList;

public class SearchFragment extends Fragment
{
    private ViewPager searchViewPager;
    private SearchPagerAdapter searchPagerAdapter;
    private ArrayList<Fragment> searchFragments;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search,container,false);
        searchFragments = new ArrayList<>();
        searchFragments.add(new SearchAdsFragment());

        searchViewPager = (ViewPager) v.findViewById(R.id.search_viewpager);
        searchPagerAdapter = new SearchPagerAdapter(getChildFragmentManager(),searchFragments);
        searchViewPager.setAdapter(searchPagerAdapter);
        return v;
    }
}
