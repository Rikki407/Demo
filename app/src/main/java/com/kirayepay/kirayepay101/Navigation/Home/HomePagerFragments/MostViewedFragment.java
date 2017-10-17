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

import com.kirayepay.kirayepay101.Navigation.NavigationFragment;
import com.kirayepay.kirayepay101.Adapters.AdsAdapter;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.AdsContainments;
import com.kirayepay.kirayepay101.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rikki on 8/3/17.
 */

public class MostViewedFragment extends Fragment
{
    public static MostViewedFragment newInstance()
    {
        return new MostViewedFragment();
    }
    RecyclerView recyclerView;
    NavigationFragment navigationFragment;
    ArrayList<AdsContainments> mostViewedAds;
    public AdsAdapter adsAdapter;
    public GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.recyclerview_adslist,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.ads_recycler_list);
        recyclerView.getItemAnimator().setChangeDuration(600);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMostViewedAds();
            }
        });
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#FFFFFF"));
        swipeRefreshLayout.setColorSchemeResources(R.color.kp_red,R.color.kp_blue);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        mostViewedAds  = new ArrayList<>();
        adsAdapter = new AdsAdapter(getActivity(),mostViewedAds,gridLayoutManager, Acquire.MOSTVIEWED_CALL);
        recyclerView.setAdapter(adsAdapter);
        fetchMostViewedAds();
        navigationFragment = ((NavigationFragment)this.getParentFragment().getParentFragment());
        return v;
    }
    private void fetchMostViewedAds()
    {
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<ArrayList<AdsContainments>> arrayListCall = apiInterface.getMostViewedAds(Acquire.API_KEY);

        arrayListCall.enqueue(new Callback<ArrayList<AdsContainments>>() {
            @Override
            public void onResponse(Call<ArrayList<AdsContainments>> call, Response<ArrayList<AdsContainments>> response) {
                if(response.isSuccessful()) {
                    mostViewedAds.clear();
                    mostViewedAds.addAll(response.body());
                    adsAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AdsContainments>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        if (Acquire.IS_LISTVIEW) gridLayoutManager.setSpanCount(1);
        adsAdapter.notifyItemRangeChanged(0, adsAdapter.getItemCount());
        super.onResume();
    }

}
