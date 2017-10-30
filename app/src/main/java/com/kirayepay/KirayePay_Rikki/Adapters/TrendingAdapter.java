package com.kirayepay.KirayePay_Rikki.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kirayepay.KirayePay_Rikki.AdsOfCategory.AdsByCategoryActivity;
import com.kirayepay.KirayePay_Rikki.Network.Responses.AdsContainments;
import com.kirayepay.KirayePay_Rikki.Network.Responses.TrendingAdsResponse.TrendingAds;
import com.kirayepay.KirayePay_Rikki.R;

import java.util.ArrayList;

/**
 * Created by rikki on 10/1/17.
 */

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>
{
    Context mContext;
    ArrayList<TrendingAds> trendingAds;
    GridLayoutManager gridLayoutManager;

    public TrendingAdapter(Context mContext, ArrayList<TrendingAds> trendingAds, GridLayoutManager gridLayoutManager) {
        this.mContext = mContext;
        this.trendingAds = trendingAds;
        this.gridLayoutManager = gridLayoutManager;
    }

    @Override
    public TrendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_adslist,parent,false);
        return new TrendingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TrendingViewHolder holder, int position)
    {
        TrendingAds trending_ads = trendingAds.get(position);
        ArrayList<AdsContainments> onlyAdsList = trending_ads.getAds();
        holder.category_title.setText(trending_ads.getTitle_category().getCategory());
        holder.parent_id = onlyAdsList.get(0).getParent_id();
        holder.cat_id = trending_ads.getTitle_category().getId();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext ,LinearLayoutManager.HORIZONTAL, false);
        holder.ads_recycler_view.setLayoutManager(layoutManager);
        TrendListItemAdapter trendListItemAdapter = new TrendListItemAdapter(mContext,onlyAdsList,layoutManager);
        holder.ads_recycler_view.setAdapter(trendListItemAdapter);
    }

    @Override
    public int getItemCount() {
        return trendingAds.size();
    }

    public class TrendingViewHolder extends RecyclerView.ViewHolder {
        TextView category_title;
        LinearLayout view_all_layout;
        RecyclerView ads_recycler_view;
        int parent_id;
        int cat_id;
        public TrendingViewHolder(View itemView) {
            super(itemView);
            category_title = (TextView) itemView.findViewById(R.id.category_title);
            ads_recycler_view = (RecyclerView) itemView.findViewById(R.id.ads_recycler_view);
            view_all_layout = (LinearLayout) itemView.findViewById(R.id.view_all_layout);
            view_all_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),""+category_title.getText().toString(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, AdsByCategoryActivity.class);
                    intent.putExtra("ParentId",parent_id);
                    intent.putExtra("CategoryId",cat_id);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
