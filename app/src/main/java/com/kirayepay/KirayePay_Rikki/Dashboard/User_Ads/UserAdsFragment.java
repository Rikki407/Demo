package com.kirayepay.KirayePay_Rikki.Dashboard.User_Ads;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kirayepay.KirayePay_Rikki.Adapters.AdsAdapter;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.Network.ApiClient;
import com.kirayepay.KirayePay_Rikki.Network.ApiInterface;
import com.kirayepay.KirayePay_Rikki.Network.Responses.AdsContainments;
import com.kirayepay.KirayePay_Rikki.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rikki on 9/25/17.
 */

public class UserAdsFragment extends Fragment
{
    RecyclerView recyclerView;
    ArrayList<AdsContainments> userAdsList;
    public AdsAdapter adsAdapter;
    public GridLayoutManager gridLayoutManager;
    String user_id;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView no_results_found;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.recyclerview_adslist,container,false);
        no_results_found = (TextView) v.findViewById(R.id.no_result_text);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchUserAds();
            }
        });
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#FFFFFF"));
        swipeRefreshLayout.setColorSchemeResources(R.color.kp_red, R.color.kp_blue);
        recyclerView = (RecyclerView) v.findViewById(R.id.ads_recycler_list);
        recyclerView.getItemAnimator().setChangeDuration(600);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        userAdsList  = new ArrayList<>();
        adsAdapter = new AdsAdapter(getActivity(),userAdsList,gridLayoutManager, Acquire.NORMAL_CALL);
        recyclerView.setAdapter(adsAdapter);
        fetchUserAds();
        return v;
    }

    private void fetchUserAds() {
        ApiInterface apiInterface = ApiClient.getApiInterface();
        SharedPreferences prefs = getActivity().getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE);
        user_id = prefs.getString(Acquire.USER_ID,"");
        Call<ArrayList<AdsContainments>> arrayListCall = apiInterface.getUserAds(user_id, Acquire.API_KEY);

        arrayListCall.enqueue(new Callback<ArrayList<AdsContainments>>() {
            @Override
            public void onResponse(Call<ArrayList<AdsContainments>> call, Response<ArrayList<AdsContainments>> response) {
                if(response.isSuccessful()) {
                    userAdsList.clear();
                    userAdsList.addAll(response.body());
                    adsAdapter.notifyDataSetChanged();
                    if(response.body().isEmpty()) no_results_found.setVisibility(View.VISIBLE);
                    else no_results_found.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                }
            }
            @Override
            public void onFailure(Call<ArrayList<AdsContainments>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                no_results_found.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"Connection Error",Toast.LENGTH_SHORT).show();

            }
        });
    }


}
