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
import android.widget.LinearLayout;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.Network.ApiClient;
import com.kirayepay.KirayePay_Rikki.Network.ApiInterface;
import com.kirayepay.KirayePay_Rikki.Network.Responses.RequirementContainments;
import com.kirayepay.KirayePay_Rikki.R;
import com.kirayepay.KirayePay_Rikki.Adapters.RequirementsAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rikki on 8/3/17.
 */

public class RequirementsFragment extends Fragment implements View.OnClickListener
{
    public static RequirementsFragment newInstance()
    {
        return new RequirementsFragment();
    }
    RecyclerView recyclerView;
    ArrayList<RequirementContainments> requirements;
    public RequirementsAdapter requirementsAdapter;
    public GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayout refresh_layout;
    Calendar calendar = Calendar.getInstance();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.recyclerview_adslist,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.ads_recycler_list);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(true);
        refresh_layout = (LinearLayout) v.findViewById(R.id.refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAllRequirements();
            }
        });
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#FFFFFF"));
        swipeRefreshLayout.setColorSchemeResources(R.color.kp_red,R.color.kp_blue);
        recyclerView.setLayoutManager(gridLayoutManager);
        requirements = new ArrayList<>();
        requirementsAdapter = new RequirementsAdapter(getActivity(), requirements,gridLayoutManager, Acquire.NORMAL_CALL);
        recyclerView.setAdapter(requirementsAdapter);
        refresh_layout.setOnClickListener(this);
        fetchAllRequirements();
        return v;
    }
    private void fetchAllRequirements()
    {
        refresh_layout.setVisibility(View.GONE);
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<ArrayList<RequirementContainments>> arrayListCall = apiInterface.getAllRequirements(Acquire.API_KEY);

        arrayListCall.enqueue(new Callback<ArrayList<RequirementContainments>>() {
            @Override
            public void onResponse(Call<ArrayList<RequirementContainments>> call, Response<ArrayList<RequirementContainments>> response) {
                if(response.isSuccessful()) {
                    requirements.clear();
                    ArrayList<RequirementContainments> req_list = new ArrayList<>();
                    Date req_date = new Date(),curr_date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        curr_date = sdf.parse(String.valueOf(calendar.getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    for(int i=0;i<response.body().size();i++)
                    {
                        try {
                            req_date = sdf.parse(response.body().get(i).getTill());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(curr_date.compareTo(req_date) <= 0){
                            req_list.add(response.body().get(i));
                        }
                    }


                    requirements.addAll(req_list);
                    requirementsAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<RequirementContainments>> call, Throwable t) {
                if(requirements.size()==0) refresh_layout.setVisibility(View.VISIBLE);
                if(swipeRefreshLayout!=null)
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.refresh_layout :
                swipeRefreshLayout.setRefreshing(true);
                fetchAllRequirements();
                break;
        }
    }
}
