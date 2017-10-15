package com.kirayepay.kirayepay101.Search;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirayepay.kirayepay101.R;
import com.kirayepay.kirayepay101.Search.SearchPagerFragments.SearchAdsFragment;
import com.kirayepay.kirayepay101.Search.SearchPagerFragments.SearchCategoryFragments;
import com.kirayepay.kirayepay101.Search.SearchPagerFragments.SearchRequirementsFragment;

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
//        searchFragments.add(new SearchRequirementsFragment());
//        searchFragments.add(new SearchCategoryFragments());
        searchViewPager = (ViewPager) v.findViewById(R.id.search_viewpager);
        searchPagerAdapter = new SearchPagerAdapter(getChildFragmentManager(),searchFragments);
        searchViewPager.setAdapter(searchPagerAdapter);
        return v;
    }
}
