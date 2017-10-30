package com.kirayepay.KirayePay_Rikki.Details;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kirayepay.KirayePay_Rikki.Adapters.RelatedAdsAdapter;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.RecyclerViewItemDecorator;
import com.kirayepay.KirayePay_Rikki.Network.ApiClient;
import com.kirayepay.KirayePay_Rikki.Network.ApiInterface;
import com.kirayepay.KirayePay_Rikki.Network.Responses.AdsContainments;
import com.kirayepay.KirayePay_Rikki.R;

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
        View v  = inflater.inflate(R.layout.related_ads_list,container,false);
        category_id = getArguments().getInt("CategoryId");
        ad_id = getArguments().getInt("Ad_Id");
        recyclerView = (RecyclerView) v.findViewById(R.id.related_recycler_view);;
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
