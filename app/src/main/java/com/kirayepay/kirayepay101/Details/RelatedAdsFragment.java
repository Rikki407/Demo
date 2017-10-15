package com.kirayepay.kirayepay101.Details;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirayepay.kirayepay101.Adapters.AdsAdapter;
import com.kirayepay.kirayepay101.Adapters.RelatedAdsAdapter;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.RikkiClasses.RecyclerViewItemDecorator;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.AdsContainments;
import com.kirayepay.kirayepay101.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rikki on 8/11/17.
 */

public class RelatedAdsFragment extends Fragment
{
    RecyclerView recyclerView;
    ArrayList<AdsContainments> adsListOfCategory;
    public RelatedAdsAdapter adsAdapter;
    public LinearLayoutManager linearLayoutManager;
    int category_id,ad_id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.recyclerview_adslist,container,false);
        category_id = getArguments().getInt("CategoryId");
        ad_id = getArguments().getInt("Ad_Id");
        recyclerView = (RecyclerView) v.findViewById(R.id.ads_recycler_list);;
        recyclerView.addItemDecoration(new RecyclerViewItemDecorator(0));
        linearLayoutManager = new LinearLayoutManager(getActivity() ,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adsListOfCategory  = new ArrayList<>();
        adsAdapter = new RelatedAdsAdapter(getActivity(),adsListOfCategory);
        recyclerView.setAdapter(adsAdapter);
        fetchAdsForCategory();
        return v;
    }
    private void fetchAdsForCategory()
    {
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<ArrayList<AdsContainments>> arrayListCall = apiInterface.getAllAdsInCategories(""+category_id, Acquire.API_KEY);

        arrayListCall.enqueue(new Callback<ArrayList<AdsContainments>>() {
            @Override
            public void onResponse(Call<ArrayList<AdsContainments>> call, Response<ArrayList<AdsContainments>> response) {
                if(response.isSuccessful()) {
                    ArrayList<AdsContainments> arrayList = new ArrayList<>();
                    for(int i=0;i<response.body().size();i++) {
                        if(!(response.body().get(i).getAds_id()==ad_id))arrayList.add(response.body().get(i));
                    }
                    adsListOfCategory.addAll(arrayList);
                    adsAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AdsContainments>> call, Throwable t) {

            }
        });
    }
}
