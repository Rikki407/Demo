package com.kirayepay.kirayepay101.AdsOfCategory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kirayepay.kirayepay101.AdsDetail.AdsDetailActivity;
import com.kirayepay.kirayepay101.MyClasses.Acquire;
import com.kirayepay.kirayepay101.Network.Responses.AdsContainments;
import com.kirayepay.kirayepay101.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rikki on 8/8/17.
 */

public class AdsByCategoryAdapter extends RecyclerView.Adapter<AdsByCategoryAdapter.AdsViewHolder>
{
    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_THREE = 3;

    private static final int VIEW_TYPE_SMALL = 1;
    private static final int VIEW_TYPE_BIG = 2;

    Context mContext;
    ArrayList<AdsContainments> adsListOfCategories;
    private GridLayoutManager mLayoutManager;
    int calling;

    public AdsByCategoryAdapter(Context mContext, ArrayList<AdsContainments> adsListOfCategories,GridLayoutManager mLayoutManager,int calling) {
        this.mContext = mContext;
        this.adsListOfCategories = adsListOfCategories;
        this.mLayoutManager = mLayoutManager;
        this.calling = calling;
    }

    @Override
    public int getItemViewType(int position) {
        int spanCount = mLayoutManager.getSpanCount();
        if (spanCount == SPAN_COUNT_ONE) {
            return VIEW_TYPE_BIG;
        } else {
            return VIEW_TYPE_SMALL;
        }
    }

    @Override
    public AdsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;

        if (viewType == VIEW_TYPE_SMALL||calling==Acquire.DET_CALL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_2_adslist, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_1_adslist, parent, false);
        }
        return new AdsViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(AdsViewHolder holder, int position)
    {
        AdsContainments ads_contenments = adsListOfCategories.get(position);
        holder.ad_id = ads_contenments.getAds_id();
        holder.cat_id = ads_contenments.getCategory_id();
        holder.ad_title.setText(ads_contenments.getAds_title());
        holder.ad_location.setText(ads_contenments.getAddr_city());
        holder.ad_rental_option.setText(ads_contenments.getRental_option());
        if(ads_contenments.getImage()!=null&&!ads_contenments.getImage().isEmpty())
        {
            Picasso.with(mContext).load(ads_contenments.getImage()).into(holder.ad_image);
        }
    }

    @Override
    public int getItemCount() {
        return adsListOfCategories.size();
    }

    public class AdsViewHolder extends RecyclerView.ViewHolder
    {
        int ad_id;
        int cat_id;
        ImageView ad_image;
        TextView ad_title;
        TextView ad_location;
        TextView ad_rental_option;

        public AdsViewHolder(final View itemView, int viewType) {
            super(itemView);
            if(viewType==VIEW_TYPE_SMALL||calling==Acquire.DET_CALL) {
                ad_image = (ImageView) itemView.findViewById(R.id.grid_ad_image);
                ad_title = (TextView) itemView.findViewById(R.id.grid_ad_title);
                ad_location = (TextView) itemView.findViewById(R.id.grid_ad_location);
                ad_rental_option = (TextView) itemView.findViewById(R.id.grid_ad_rental_option);
            }
            else {
                ad_image = (ImageView) itemView.findViewById(R.id.ad_image);
                ad_title = (TextView) itemView.findViewById(R.id.ad_title);
                ad_location = (TextView) itemView.findViewById(R.id.ad_location);
                ad_rental_option = (TextView) itemView.findViewById(R.id.ad_rental_option);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, AdsDetailActivity.class);
                    intent.putExtra("ad_id",ad_id);
                    intent.putExtra("cat_id",cat_id);
                    context.startActivity(intent);
                }
            });
        }
    }
}
