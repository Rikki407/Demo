package com.kirayepay.kirayepay101.AdsOfCategory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kirayepay.kirayepay101.MyClasses.Acquire;
import com.kirayepay.kirayepay101.MyClasses.CategoryHierarchy;
import com.kirayepay.kirayepay101.Network.Responses.CategoriesContainments;
import com.kirayepay.kirayepay101.R;

import java.util.ArrayList;
/**
 * Created by rikki on 8/9/17.
 */

public class AdsByCategoryFragment extends Fragment implements View.OnClickListener
{

    private ABC_PagerAdapter abc_pagerAdapter;
    private ViewPager viewPager;
    ArrayList<String> categories_names;
    ArrayList<AdsListFragment> adsListFragments;
    int category_id,parent_category_id;
    int selected_cat_pos = -1;
    public ImageView list_to_grid;
    public static AdsByCategoryFragment newInstance() {
        AdsByCategoryFragment hf= new AdsByCategoryFragment();
        return hf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_adsbycategory,container,false);
        list_to_grid = (ImageView) v.findViewById(R.id.list_to_grid_image);
        list_to_grid.setImageResource(R.drawable.grid_view);
        list_to_grid.setOnClickListener(this);
        category_id = getArguments().getInt("CategoryId");
        parent_category_id = getArguments().getInt("ParentId");
        categories_names = new ArrayList<>();
        adsListFragments = new ArrayList<>() ;

        fetchCategoriesNames();

        adsListFragments = buildFragments();
        abc_pagerAdapter = new ABC_PagerAdapter(getActivity(),getFragmentManager(), adsListFragments,categories_names);
        viewPager = (ViewPager) v.findViewById(R.id.adsbycat_viewpager);
        viewPager.setAdapter(abc_pagerAdapter);
        viewPager.setCurrentItem(selected_cat_pos);
        return v;
    }

    private ArrayList<AdsListFragment> buildFragments() {
        ArrayList<AdsListFragment> fragments = new ArrayList<>();
        ArrayList<CategoriesContainments> SiblingCategories = CategoryHierarchy.getSubcategories().get(parent_category_id);
        for(int i = 0; i<categories_names.size(); i++) {
            Bundle b = new Bundle();
            b.putInt("CategoryId", SiblingCategories.get(i).getCategory_id());
            AdsListFragment adsListFragment = new AdsListFragment();
            adsListFragment.setArguments(b);
            fragments.add(adsListFragment);
        }
        return fragments;
    }

    private void fetchCategoriesNames()
    {
        int parent_size = CategoryHierarchy.getSubcategories().get(parent_category_id).size();
        ArrayList<CategoriesContainments> SiblingCategories = CategoryHierarchy.getSubcategories().get(parent_category_id);
        for(int i = 0 ;i<parent_size;i++) {
            categories_names.add(SiblingCategories.get(i).getCategory_name());
            if(SiblingCategories.get(i).getCategory_id()==category_id)selected_cat_pos=i;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.list_to_grid_image :
                Acquire.IS_LISTVIEW = !Acquire.IS_LISTVIEW;
                for(int i = 0; i< adsListFragments.size(); i++) {
                    if (adsListFragments.get(i).gridLayoutManager != null) {
                        if (adsListFragments.get(i).gridLayoutManager.getSpanCount() == 1) {
                            list_to_grid.setImageResource(R.drawable.list_view);
                            adsListFragments.get(i).gridLayoutManager.setSpanCount(3);
                        } else {
                            list_to_grid.setImageResource(R.drawable.grid_view);
                            adsListFragments.get(i).gridLayoutManager.setSpanCount(1);
                        }
                        adsListFragments.get(i).adsByCategoryAdapter.notifyItemRangeChanged(0, adsListFragments.get(i).adsByCategoryAdapter.getItemCount());
                    }
                }
        }
    }

}
