package com.kirayepay.KirayePay_Rikki.AdsOfCategory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.CategoryHierarchy;
import com.kirayepay.KirayePay_Rikki.Network.Responses.CategoriesContainments;
import com.kirayepay.KirayePay_Rikki.R;

import java.util.ArrayList;

/**
 * Created by rikki on 8/9/17.
 */

public class AdsByCategoryFragment extends Fragment {

    private ABC_PagerAdapter abc_pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    ArrayList<String> categories_names;
    static ArrayList<AdsListFragment> adsListFragments;
    int category_id, parent_category_id;
    int selected_cat_pos = -1;

    public static AdsByCategoryFragment newInstance() {
        AdsByCategoryFragment hf = new AdsByCategoryFragment();
        return hf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_adsbycategory, container, false);
        category_id = getArguments().getInt("CategoryId");
        parent_category_id = getArguments().getInt("ParentId");

        categories_names = new ArrayList<>();
        adsListFragments = new ArrayList<>();

        fetchCategoriesNames();

        adsListFragments = buildFragments();

        abc_pagerAdapter = new ABC_PagerAdapter(getActivity(), getFragmentManager(), adsListFragments, categories_names);
        tabLayout = (TabLayout) v.findViewById(R.id.adsbycat_tablayout);
        viewPager = (ViewPager) v.findViewById(R.id.adsbycat_viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(abc_pagerAdapter);
        viewPager.setCurrentItem(selected_cat_pos);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Acquire.PRICE_SEEKBAR_CURR = Integer.MAX_VALUE;
                Acquire.SECURITY_SEEKBAR_CURR = Integer.MAX_VALUE;
                selected_cat_pos = position;
                if (adsListFragments != null) {
                    adsListFragments.get(position).updateInitFilterValues();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return v;
    }


    private ArrayList<AdsListFragment> buildFragments() {
        ArrayList<AdsListFragment> fragments = new ArrayList<>();
        ArrayList<CategoriesContainments> SiblingCategories = CategoryHierarchy.getSubcategories().get(parent_category_id);
        for (int i = 0; i < categories_names.size(); i++) {
            Bundle b = new Bundle();
            b.putInt("CategoryId", SiblingCategories.get(i).getCategory_id());
            b.putInt("view_pager_position", i);
            AdsListFragment adsListFragment = new AdsListFragment();
            adsListFragment.setArguments(b);
            fragments.add(adsListFragment);
        }

        return fragments;
    }

    private void fetchCategoriesNames() {
        if (CategoryHierarchy.getSubcategories().get(parent_category_id) != null) {
            int parent_size = CategoryHierarchy.getSubcategories().get(parent_category_id).size();
            ArrayList<CategoriesContainments> SiblingCategories = CategoryHierarchy.getSubcategories().get(parent_category_id);
            for (int i = 0; i < parent_size; i++) {
                categories_names.add(SiblingCategories.get(i).getCategory_name());
                if (SiblingCategories.get(i).getCategory_id() == category_id) {
                    selected_cat_pos = i;
                    Acquire.INIT_ABC_PAGER_POS = selected_cat_pos;
                }

            }
        }
    }

}
