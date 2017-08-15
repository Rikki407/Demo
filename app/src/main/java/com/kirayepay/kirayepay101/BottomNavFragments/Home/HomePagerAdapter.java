package com.kirayepay.kirayepay101.BottomNavFragments.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.kirayepay.kirayepay101.BottomNavFragments.Home.HomePagerFragments.LookOutsFragment;
import com.kirayepay.kirayepay101.BottomNavFragments.Home.HomePagerFragments.MostViewedFragment;
import com.kirayepay.kirayepay101.BottomNavFragments.Home.HomePagerFragments.TrendingFragment;

/**
 * Created by rikki on 8/3/17.
 */

public class HomePagerAdapter extends FragmentPagerAdapter
{

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Trending";
            case 1:
                return "Most Viewed";
            case 2:
                return "Look Outs";
        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return TrendingFragment.newInstance();
            case 1:
                return MostViewedFragment.newInstance();
            case 2:
                return LookOutsFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
