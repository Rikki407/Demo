package com.kirayepay.KirayePay_Rikki.Dashboard.User_Requirements;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kirayepay.KirayePay_Rikki.Adapters.RequirementsAdapter;
import com.kirayepay.KirayePay_Rikki.Network.Responses.RequirementContainments;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.Network.ApiClient;
import com.kirayepay.KirayePay_Rikki.Network.ApiInterface;
import com.kirayepay.KirayePay_Rikki.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rikki on 9/25/17.
 */

public class UserReqFragment extends Fragment
{
    RecyclerView recyclerView;
    ArrayList<RequirementContainments> userReqList;
    public RequirementsAdapter requirementsAdapter;
    public GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    String user_id;
    private TextView no_results_found;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.recyclerview_adslist,container,false);
        Bundle bundle = getArguments();
        user_id = bundle.getString("USER_ID");
        no_results_found = (TextView) v.findViewById(R.id.no_result_text);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchUserRequirements();
            }
        });
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#FFFFFF"));
        swipeRefreshLayout.setColorSchemeResources(R.color.kp_red, R.color.kp_blue);
        recyclerView = (RecyclerView) v.findViewById(R.id.ads_recycler_list);
        recyclerView = (RecyclerView) v.findViewById(R.id.ads_recycler_list);
        recyclerView.getItemAnimator().setChangeDuration(600);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        userReqList  = new ArrayList<>();
        requirementsAdapter = new RequirementsAdapter(getActivity(),userReqList,gridLayoutManager, Acquire.NORMAL_CALL);
        recyclerView.setAdapter(requirementsAdapter);
        fetchUserRequirements();
        return v;
    }

    private void fetchUserRequirements() {

        ApiInterface apiInterface = ApiClient.getApiInterface();

        Call<ArrayList<RequirementContainments>> arrayListCall = apiInterface.getUserRequirements(user_id, Acquire.API_KEY);
        arrayListCall.enqueue(new Callback<ArrayList<RequirementContainments>>() {
            @Override
            public void onResponse(Call<ArrayList<RequirementContainments>> call, Response<ArrayList<RequirementContainments>> response) {
                if(response.isSuccessful()) {
                    userReqList.clear();
                    userReqList.addAll(response.body());
                    requirementsAdapter.notifyDataSetChanged();
                    if(response.body().isEmpty()) no_results_found.setVisibility(View.VISIBLE);
                    else no_results_found.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<RequirementContainments>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                no_results_found.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"Connection Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
