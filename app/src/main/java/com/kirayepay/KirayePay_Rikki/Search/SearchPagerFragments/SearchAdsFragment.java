package com.kirayepay.KirayePay_Rikki.Search.SearchPagerFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.Network.ApiClient;
import com.kirayepay.KirayePay_Rikki.Network.ApiInterface;
import com.kirayepay.KirayePay_Rikki.Network.Responses.AdsContainments;
import com.kirayepay.KirayePay_Rikki.R;
import com.kirayepay.KirayePay_Rikki.Adapters.AdsAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rikki on 8/17/17.
 */

public class SearchAdsFragment extends Fragment {
    static Call<ArrayList<AdsContainments>> arrayListCall;
    RecyclerView recyclerView;
    ArrayList<AdsContainments> searchResults;
    private SwipeRefreshLayout swipeRefreshLayout;
    String recieved_text;
    public AdsAdapter searchAdapter;
    public GridLayoutManager gridLayoutManager;
    public static final String ACTION_INTENT = "SEARCH_CALLING";

    public static SearchAdsFragment newInstance() {
        return new SearchAdsFragment();
    }

    TextView no_results_found;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recyclerview_adslist, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.ads_recycler_list);
        no_results_found = (TextView) v.findViewById(R.id.no_result_text);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAdsForKeys(recieved_text, "DelhiNCR");
            }
        });
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#FFFFFF"));
        swipeRefreshLayout.setColorSchemeResources(R.color.kp_red, R.color.kp_blue);
        recyclerView.getItemAnimator().setChangeDuration(600);
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        searchResults = new ArrayList<>();
        if (searchResults.size() == 0) {
            no_results_found.setVisibility(View.VISIBLE);
        }
        searchAdapter = new AdsAdapter(getActivity(), searchResults, gridLayoutManager, Acquire.MOSTVIEWED_CALL);
        recyclerView.setAdapter(searchAdapter);

        IntentFilter filter = new IntentFilter(ACTION_INTENT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(ActivityDataReceiver, filter);

        return v;
    }

    private void fetchAdsForKeys(final String keyword, String location) {
        ApiInterface apiInterface = ApiClient.getApiInterface();
        no_results_found.setVisibility(View.GONE);
        if (!keyword.isEmpty()) {
            arrayListCall = apiInterface.getSearchedAds(keyword, location, Acquire.API_KEY);

            swipeRefreshLayout.setRefreshing(true);

            arrayListCall.enqueue(new Callback<ArrayList<AdsContainments>>() {
                @Override
                public void onResponse(Call<ArrayList<AdsContainments>> call, Response<ArrayList<AdsContainments>> response) {
                    if (response.isSuccessful()) {
                        searchResults.clear();
                        searchResults.addAll(response.body());
                        searchAdapter.notifyDataSetChanged();
                        if (searchResults.size() == 0) {
                            no_results_found.setVisibility(View.VISIBLE);
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }, 500);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<AdsContainments>> call, Throwable t) {
                }
            });
        }
        else {
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(ActivityDataReceiver);
        super.onDestroy();
    }

    protected BroadcastReceiver ActivityDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_INTENT.equals(intent.getAction())) {
                recieved_text = intent.getStringExtra("SEARCH_TEXT");
                if (arrayListCall != null) arrayListCall.cancel();
                recyclerView.removeAllViewsInLayout();
                fetchAdsForKeys(recieved_text, "DelhiNCR");
            }
        }
    };


}
