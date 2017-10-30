package com.kirayepay.KirayePay_Rikki.Dashboard.User_Requirements;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirayepay.KirayePay_Rikki.Adapters.RequirementsAdapter;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.Network.Responses.RequirementContainments;
import com.kirayepay.KirayePay_Rikki.R;

import java.util.ArrayList;

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

//        ApiInterface apiInterface = ApiClient.getApiInterface();
//        SharedPreferences prefs = getActivity().getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE);
//        String user_id = prefs.getString(Acquire.USER_ID,"");
//        Call<ArrayList<RequirementContainments>> arrayListCall = apiInterface.getUserRequirements(user_id, Acquire.API_KEY);
//        arrayListCall.enqueue(new Callback<ArrayList<RequirementContainments>>() {
//            @Override
//            public void onResponse(Call<ArrayList<RequirementContainments>> call, Response<ArrayList<RequirementContainments>> response) {
//                if(response.isSuccessful()) {
//                    userReqList.addAll(response.body());
//                    requirementsAdapter.notifyDataSetChanged();
//                }
//            }
//            @Override
//            public void onFailure(Call<ArrayList<RequirementContainments>> call, Throwable t) {
//
//            }
//        });
    }
}
