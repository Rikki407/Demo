package com.kirayepay.kirayepay101.Navigation.Menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;


import com.kirayepay.kirayepay101.Navigation.Menu.AllCategoriesList.MainCategoriesAdapter;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.Network.Responses.CategoriesContainments;
import com.kirayepay.kirayepay101.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rikki on 8/3/17.
 */

public class AllCategoriesFragment extends Fragment
{
    private MainCategoriesAdapter mainCategoriesAdapter;

    public static AllCategoriesFragment newInstance()
    {
        AllCategoriesFragment mf= new AllCategoriesFragment();
        return mf;
    }


    private HashMap<Integer,ArrayList<CategoriesContainments>> subcategories;
    private Context mContext;
    private ExpandableListView expandableListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.listview_expandable,container,false);
        mContext=getActivity();
        subcategories = new HashMap<>();
        mainCategoriesAdapter = new MainCategoriesAdapter(mContext);
        expandableListView = (ExpandableListView) view.findViewById(R.id.mainList);
        expandableListView.setAdapter(mainCategoriesAdapter);
        expandableListView.setDivider(null);
        expandableListView.setDividerHeight(0);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup) expandableListView.collapseGroup(previousGroup);
                if(Acquire.SUBCAT_NUM>9&&groupPosition!=0)expandableListView.setSelectedGroup(groupPosition-1);
                else if(Acquire.SUBCAT_NUM>6&&groupPosition!=0)expandableListView.setSelectedGroup(groupPosition-2);
                previousGroup = groupPosition;
            }
        });

        return view;
    }

}
