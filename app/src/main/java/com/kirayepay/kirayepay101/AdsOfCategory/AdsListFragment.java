package com.kirayepay.kirayepay101.AdsOfCategory;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kirayepay.kirayepay101.Adapters.AdsAdapter;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.AdsContainments;
import com.kirayepay.kirayepay101.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by rikki on 8/8/17.
 */

public class AdsListFragment extends Fragment {
    RecyclerView recyclerView;
    private int view_pager_position = -1;
    ArrayList<AdsContainments> adsListOfCategory;
    public AdsAdapter adsAdapter;
    boolean already_called = false;
    int max_rent_price = -1, max_deposit_price = -1;
    int curr_rent_price = -1, curr_deposit_price = -1;
    public HashMap<Integer, Boolean> rental_options = new HashMap<>();
    String condition;
    public GridLayoutManager gridLayoutManager;
    int category_id;
    private SwipeRefreshLayout swipeRefreshLayout;
    TextView no_results_found;

    ArrayList<AdsContainments> body;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recyclerview_adslist, container, false);
        no_results_found = (TextView) v.findViewById(R.id.no_result_text);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(true);

        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#FFFFFF"));
        swipeRefreshLayout.setColorSchemeResources(R.color.kp_red,R.color.kp_blue);
        rental_options.put(R.id.ro_daily, true);
        rental_options.put(R.id.ro_weekly, true);
        rental_options.put(R.id.ro_monthly, true);
        rental_options.put(R.id.ro_occasional, true);
        condition = "Both";
        category_id = getArguments().getInt("CategoryId");
        view_pager_position = getArguments().getInt("view_pager_position");
        recyclerView = (RecyclerView) v.findViewById(R.id.ads_recycler_list);
        recyclerView.getItemAnimator().setChangeDuration(600);
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adsListOfCategory = new ArrayList<>();
        adsAdapter = new AdsAdapter(getActivity(), adsListOfCategory, gridLayoutManager, Acquire.NORMAL_CALL);
        recyclerView.setAdapter(adsAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(adsListOfCategory!=null) changeAccToFilter();
            }
        });
        changeAccToFilter();



        return v;
    }

    private void fetchAdsForCategory() {
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<ArrayList<AdsContainments>> arrayListCall = apiInterface.getAllAdsInCategories("" + category_id, Acquire.API_KEY);
        arrayListCall.enqueue(new Callback<ArrayList<AdsContainments>>() {
            @Override
            public void onResponse(Call<ArrayList<AdsContainments>> call, Response<ArrayList<AdsContainments>> response) {
                if (response.isSuccessful()) {
                    body = response.body();
                    for (int i = 0; i < body.size(); i++) {
                        int curr_rent = body.get(i).getRental_amount();
                        int curr_deposit = body.get(i).getSecurity_deposit();
                        if (max_rent_price <= curr_rent) max_rent_price = curr_rent;
                        if (max_deposit_price <= curr_deposit) max_deposit_price = curr_deposit;
                        adsListOfCategory.add(body.get(i));
                    }

                    curr_deposit_price = max_deposit_price;
                    curr_rent_price = max_rent_price;
                    if (view_pager_position == Acquire.INIT_ABC_PAGER_POS && !already_called) {
                        updateInitFilterValues();
                        Log.e("vhgv", "gfgjh");
                        already_called = true;
                    }
                    adsAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<AdsContainments>> call, Throwable t) {
                Toast.makeText(getActivity(),"Connection Error",Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void changeAccToFilter() {
        Log.e("rikki  ", " rent curr" + Acquire.PRICE_SEEKBAR_CURR + " deposit curr " + Acquire.SECURITY_SEEKBAR_CURR);
        adsListOfCategory.clear();
        no_results_found.setVisibility(View.GONE);
        if(body==null)
        {
            fetchAdsForCategory();
        }
        else
        {
            for (int i = 0; i < body.size(); i++) {

                if (body.get(i).getRental_amount() <= Acquire.PRICE_SEEKBAR_CURR && body.get(i).getSecurity_deposit() <= Acquire.SECURITY_SEEKBAR_CURR)
                {
                    Log.e("rikki  ", ""+body.get(i).getRental_option()+" "+ body.get(i).getRental_amount() + " deposit curr " + body.get(i).getRental_amount());


                    if (rental_options.get(R.id.ro_daily) && body.get(i).getRental_option().equals("Daily"))
                    {
                        if (Acquire.CONTDITITON.equals("Both") || Acquire.CONTDITITON.equals("0"))
                            adsListOfCategory.add(body.get(i));
                        else if (Acquire.CONTDITITON.equals(body.get(i).getCondition()))
                            adsListOfCategory.add(body.get(i));
                    }
                    else if (rental_options.get(R.id.ro_weekly) && body.get(i).getRental_option().equals("Weekly"))
                        adsListOfCategory.add(body.get(i));
                    else if (rental_options.get(R.id.ro_monthly) && body.get(i).getRental_option().equals("Monthly"))
                        adsListOfCategory.add(body.get(i));
                    else if (rental_options.get(R.id.ro_occasional) && body.get(i).getRental_option().equals("Occasion"))
                        adsListOfCategory.add(body.get(i));
                }
            }
            swipeRefreshLayout.setRefreshing(false);
        }
        condition = Acquire.CONTDITITON;
        rental_options = Acquire.RENTAL_OPTIONS;
        curr_rent_price = Acquire.PRICE_SEEKBAR_CURR;
        curr_deposit_price = Acquire.SECURITY_SEEKBAR_CURR;
        adsAdapter.notifyDataSetChanged();

        if(adsListOfCategory.size()==0) {
            no_results_found.setVisibility(View.GONE);
        }

    }


    public void updateInitFilterValues() {
         /*
                    For filter updating
         */
        Acquire.CONTDITITON = condition;
        Acquire.RENTAL_OPTIONS = rental_options;
        Set<Map.Entry<Integer, Boolean>> entries = Acquire.RENTAL_OPTIONS.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            if (getActivity() != null) {
                TextView change_text = (TextView) getActivity().findViewById(entry.getKey());
                if (Acquire.RENTAL_OPTIONS.get(entry.getKey())) {
                    ((GradientDrawable) change_text.getBackground()).setColor(Color.parseColor("#145ea7"));
                    change_text.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    ((GradientDrawable) change_text.getBackground()).setColor(Color.parseColor("#FFFFFF"));
                    change_text.setTextColor(Color.parseColor("#145ea7"));
                }
            }
        }
        int radio_bttn_id = R.id.both_button;;

        if(condition!=null)
        {
            switch (condition) {
                case "Used":
                    radio_bttn_id = R.id.used_button;
                    break;
                case "New":
                    radio_bttn_id = R.id.new_button;
                    break;
                default:
                    radio_bttn_id = R.id.both_button;
                    break;
            }
        }
        RadioButton radioButton = (RadioButton) getActivity().findViewById(radio_bttn_id);
        radioButton.setChecked(true);

        Acquire.PRICE_SEEKBAR_MAX = max_rent_price;
        Acquire.SECURITY_SEEKBAR_MAX = max_deposit_price;
        Acquire.SECURITY_SEEKBAR_CURR = curr_deposit_price;
        Acquire.PRICE_SEEKBAR_CURR = curr_rent_price;
        Log.e("vhgv", ""+max_rent_price+" , "+ max_rent_price);




    }

    @Override
    public void onResume() {
        if (Acquire.IS_LISTVIEW) {
            gridLayoutManager.setSpanCount(1);
        } else {
            gridLayoutManager.setSpanCount(2);
        }
        adsAdapter.notifyItemRangeChanged(0, adsAdapter.getItemCount());
        super.onResume();
    }
}
