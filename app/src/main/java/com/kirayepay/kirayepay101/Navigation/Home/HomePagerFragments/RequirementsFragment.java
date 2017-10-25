package com.kirayepay.kirayepay101.Navigation.Home.HomePagerFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.RequirementContainments;
import com.kirayepay.kirayepay101.R;
import com.kirayepay.kirayepay101.Adapters.RequirementsAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rikki on 8/3/17.
 */

public class RequirementsFragment extends Fragment implements View.OnClickListener
{
    public static RequirementsFragment newInstance()
    {
        return new RequirementsFragment();
    }
    RecyclerView recyclerView;
    ArrayList<RequirementContainments> mostViewedAds;
    public RequirementsAdapter requirementsAdapter;
    public GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayout refresh_layout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.recyclerview_adslist,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.ads_recycler_list);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(true);
        refresh_layout = (LinearLayout) v.findViewById(R.id.refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAllRequirements();
            }
        });
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#FFFFFF"));
        swipeRefreshLayout.setColorSchemeResources(R.color.kp_red,R.color.kp_blue);
        recyclerView.setLayoutManager(gridLayoutManager);
        mostViewedAds  = new ArrayList<>();
        requirementsAdapter = new RequirementsAdapter(getActivity(),mostViewedAds,gridLayoutManager, Acquire.NORMAL_CALL);
        recyclerView.setAdapter(requirementsAdapter);
        refresh_layout.setOnClickListener(this);
        fetchAllRequirements();
        return v;
    }
    private void fetchAllRequirements()
    {
        refresh_layout.setVisibility(View.GONE);
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<ArrayList<RequirementContainments>> arrayListCall = apiInterface.getAllRequirements(Acquire.API_KEY);

        arrayListCall.enqueue(new Callback<ArrayList<RequirementContainments>>() {
            @Override
            public void onResponse(Call<ArrayList<RequirementContainments>> call, Response<ArrayList<RequirementContainments>> response) {
                if(response.isSuccessful()) {
                    mostViewedAds.clear();
                    mostViewedAds.addAll(response.body());
                    requirementsAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<RequirementContainments>> call, Throwable t) {
                if(mostViewedAds.size()==0) refresh_layout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.refresh_layout :
                swipeRefreshLayout.setRefreshing(true);
                fetchAllRequirements();
                break;
        }
    }
}
