package com.kirayepay.KirayePay_Rikki.Navigation.Home.HomePagerFragments;

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

import com.kirayepay.KirayePay_Rikki.Adapters.TrendingAdapter;
import com.kirayepay.KirayePay_Rikki.Network.ApiClient;
import com.kirayepay.KirayePay_Rikki.Network.ApiInterface;
import com.kirayepay.KirayePay_Rikki.Network.Responses.TrendingAdsResponse.TrendingAds;
import com.kirayepay.KirayePay_Rikki.Network.Responses.TrendingAdsResponse.TrendingContainments;
import com.kirayepay.KirayePay_Rikki.R;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rikki on 8/3/17.
 */

public class TrendingFragment extends Fragment
{
    public static TrendingFragment newInstance() {
        return new TrendingFragment();
    }
    RecyclerView recyclerView;
    ArrayList<TrendingAds> trendingAds;
    public TrendingAdapter trendingAdapter;
    public GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recyclerview_adslist,container,false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTrendingAds();
            }
        });
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#FFFFFF"));
        swipeRefreshLayout.setColorSchemeResources(R.color.kp_red,R.color.kp_blue);
        recyclerView = (RecyclerView) v.findViewById(R.id.ads_recycler_list);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        trendingAds  = new ArrayList<>();
        trendingAdapter = new TrendingAdapter(getActivity(),trendingAds,gridLayoutManager);
        recyclerView.setAdapter(trendingAdapter);
        fetchTrendingAds();
        return v;
    }


    private void fetchTrendingAds()
    {
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<TrendingContainments> trendingAds = apiInterface.getTrendingAds(Acquire.API_KEY);
        trendingAds.enqueue(new Callback<TrendingContainments>() {
            @Override
            public void onResponse(Call<TrendingContainments> call, Response<TrendingContainments> response) {
                addItemsToListItem(response.body().getTrending_ads());
            }
            @Override
            public void onFailure(Call<TrendingContainments> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void addItemsToListItem(ArrayList<TrendingAds> ads) {

        trendingAds.clear();
        trendingAds.addAll(ads);
        trendingAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

    }

}
