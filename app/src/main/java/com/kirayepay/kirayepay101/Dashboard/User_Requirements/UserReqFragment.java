package com.kirayepay.kirayepay101.Dashboard.User_Requirements;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirayepay.kirayepay101.Adapters.RequirementsAdapter;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.RequirementContainments;
import com.kirayepay.kirayepay101.R;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.recyclerview_adslist,container,false);
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
        SharedPreferences prefs = getActivity().getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE);
        String user_id = prefs.getString(Acquire.USER_ID,"");
        Call<ArrayList<RequirementContainments>> arrayListCall = apiInterface.getUserRequirements(user_id, Acquire.API_KEY);

        arrayListCall.enqueue(new Callback<ArrayList<RequirementContainments>>() {
            @Override
            public void onResponse(Call<ArrayList<RequirementContainments>> call, Response<ArrayList<RequirementContainments>> response) {
                if(response.isSuccessful()) {
                    userReqList.addAll(response.body());
                    requirementsAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<RequirementContainments>> call, Throwable t) {

            }
        });
    }
}
