package com.kirayepay.kirayepay101.Navigation.Home.HomePagerFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirayepay.kirayepay101.Adapters.TrendingAdapter;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.TrendingAdsResponse.TrendingAds;
import com.kirayepay.kirayepay101.Network.Responses.TrendingAdsResponse.TrendingContainments;
import com.kirayepay.kirayepay101.R;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recyclerview_adslist,container,false);
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
                Log.e("trendingfragment", t.getMessage());
            }
        });

    }

    private void addItemsToListItem(ArrayList<TrendingAds> ads) {

        trendingAds.addAll(ads);
        Log.e("show_size",""+ads.size());
        trendingAdapter.notifyDataSetChanged();
    }

}