package com.kirayepay.kirayepay101.AdsOfCategory;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rikki on 8/9/17.
 */

public class ABC_PagerAdapter extends FragmentPagerAdapter
{
    public static int pos = 0;

    private List<AdsListFragment> abcFragments;
    private ArrayList<String> categories_names;
    private Context mContext;

    public ABC_PagerAdapter(Context mContext, FragmentManager fragmentManager, List<AdsListFragment> abcFragments, ArrayList<String> categories_names) {
        super(fragmentManager);
        this.abcFragments = abcFragments;
        this.categories_names = categories_names;
        this.mContext = mContext;
    }
    @Override
    public Fragment getItem(int position) {
        return abcFragments.get(position);
    }

    @Override
    public int getCount() {
        return abcFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        setPos(position);
        return categories_names.get(position);
    }

    public static int getPos() {
        return pos;
    }

    public static void setPos(int pos) {
        ABC_PagerAdapter.pos = pos;
    }

}
