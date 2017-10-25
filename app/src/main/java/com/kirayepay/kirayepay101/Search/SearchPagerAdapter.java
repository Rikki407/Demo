package com.kirayepay.kirayepay101.Search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;

/**
 * Created by rikki on 8/17/17.
 */

public class SearchPagerAdapter extends FragmentPagerAdapter
{
    ArrayList<Fragment> searchFragments;
    public SearchPagerAdapter(FragmentManager fm, ArrayList<Fragment> searchFragments)
    {
        super(fm);
        this.searchFragments = searchFragments;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "Ads";
            case 1:
                return "Requirements";
            case 2:
                return "Categories";
        }
        return null;
    }

    @Override
    public Fragment getItem(int position)
    {
        return searchFragments.get(position);
    }

    @Override
    public int getCount() {
        return searchFragments.size();
    }
}
