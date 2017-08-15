package com.kirayepay.kirayepay101.AdsOfCategory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirayepay.kirayepay101.MyClasses.Acquire;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.AdsContainments;
import com.kirayepay.kirayepay101.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by rikki on 8/8/17.
 */

public class AdsListFragment extends Fragment
{
    RecyclerView recyclerView;
    ArrayList<AdsContainments> adsListOfCategory;
    public AdsByCategoryAdapter adsByCategoryAdapter;
    public GridLayoutManager gridLayoutManager;
    int category_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.recyclerview_adslist,container,false);
        category_id = getArguments().getInt("CategoryId");
        recyclerView = (RecyclerView) v.findViewById(R.id.ads_recycler_list);
        recyclerView.getItemAnimator().setChangeDuration(600);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adsListOfCategory  = new ArrayList<>();
        adsByCategoryAdapter = new AdsByCategoryAdapter(getActivity(),adsListOfCategory,gridLayoutManager,Acquire.CAT_CALL);
        recyclerView.setAdapter(adsByCategoryAdapter);
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
                    adsListOfCategory.addAll(response.body());
                    adsByCategoryAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AdsContainments>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
            if (Acquire.IS_LISTVIEW) {
                gridLayoutManager.setSpanCount(1);
            } else {
                gridLayoutManager.setSpanCount(3);
            }
            adsByCategoryAdapter.notifyItemRangeChanged(0, adsByCategoryAdapter.getItemCount());
        super.onResume();
    }
}
