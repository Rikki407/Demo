package com.kirayepay.KirayePay_Rikki.Navigation.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kirayepay.KirayePay_Rikki.Navigation.Home.HomePagerFragments.RequirementsFragment;
import com.kirayepay.KirayePay_Rikki.Navigation.Home.HomePagerFragments.MostViewedFragment;
import com.kirayepay.KirayePay_Rikki.Navigation.Home.HomePagerFragments.TrendingFragment;

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
                return "Required";
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
                return RequirementsFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
