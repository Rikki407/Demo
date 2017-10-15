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

public class RequirementsFragment extends Fragment
{
    public static RequirementsFragment newInstance()
    {
        return new RequirementsFragment();
    }
    RecyclerView recyclerView;
    ArrayList<RequirementContainments> mostViewedAds;
    public RequirementsAdapter requirementsAdapter;
    public GridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.recyclerview_adslist,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.ads_recycler_list);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);

        recyclerView.setLayoutManager(gridLayoutManager);
        mostViewedAds  = new ArrayList<>();
        requirementsAdapter = new RequirementsAdapter(getActivity(),mostViewedAds,gridLayoutManager, Acquire.NORMAL_CALL);
        recyclerView.setAdapter(requirementsAdapter);
        fetchMostViewedAds();
        return v;
    }
    private void fetchMostViewedAds()
    {
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<ArrayList<RequirementContainments>> arrayListCall = apiInterface.getAllRequirements(Acquire.API_KEY);

        arrayListCall.enqueue(new Callback<ArrayList<RequirementContainments>>() {
            @Override
            public void onResponse(Call<ArrayList<RequirementContainments>> call, Response<ArrayList<RequirementContainments>> response) {
                if(response.isSuccessful()) {
                    Log.e("req_suc",""+response.body().get(0).getCategory());
                    mostViewedAds.addAll(response.body());
                    requirementsAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<RequirementContainments>> call, Throwable t) {
                Log.e("req_suc"," "+t.getCause()+t.getMessage());

            }
        });
    }
}
