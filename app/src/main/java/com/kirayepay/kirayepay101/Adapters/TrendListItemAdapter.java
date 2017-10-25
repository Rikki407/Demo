package com.kirayepay.kirayepay101.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirayepay.kirayepay101.Details.AdsDetailActivity;
import com.kirayepay.kirayepay101.Network.Responses.AdsContainments;
import com.kirayepay.kirayepay101.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rikki on 10/1/17.
 */

public class TrendListItemAdapter extends RecyclerView.Adapter<TrendListItemAdapter.TrendingViewHolder>
{

    private Context mContext;
    private ArrayList<AdsContainments> trendingAdsList;
    private LinearLayoutManager mLayoutManager;

    public TrendListItemAdapter(Context mContext, ArrayList<AdsContainments> trendingAdsList, LinearLayoutManager mLayoutManager) {
        this.mContext = mContext;
        this.trendingAdsList = trendingAdsList;
        this.mLayoutManager = mLayoutManager;
    }

    @Override
    public TrendListItemAdapter.TrendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_list_items,parent,false);
        return new TrendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrendingViewHolder holder, int position) {
        AdsContainments adsContainments = trendingAdsList.get(position);
        holder.ad_id = adsContainments.getAds_id();
        holder.cat_id = adsContainments.getCategory_id();
        holder.ad_title.setText(""+adsContainments.getAds_title());

        if(holder.ad_price!=null) holder.ad_price.setText((adsContainments.getRental_amount()==0)?"":"₹ "+adsContainments.getRental_amount());
        if(holder.ad_rental_option!=null) holder.ad_rental_option.setText((adsContainments.getRental_option().equals("0"))?"":"  /- "+adsContainments.getRental_option());

        if(adsContainments.getImage()!=null&&!adsContainments.getImage().isEmpty())
        {
            Picasso.with(mContext).load(adsContainments.getImage()).into(holder.ad_image);
        }

    }

    @Override
    public int getItemCount() {
        return trendingAdsList.size();
    }

    public class TrendingViewHolder extends RecyclerView.ViewHolder {
        ImageView ad_image;
        TextView ad_title;
        TextView ad_price;
        TextView ad_rental_option;
        int ad_id;
        int cat_id;
        public TrendingViewHolder(View itemView) {
            super(itemView);
            ad_image = (ImageView)itemView.findViewById(R.id.trending_ad_image);
            ad_title = (TextView) itemView.findViewById(R.id.trending_ad_title);
            ad_price = (TextView) itemView.findViewById(R.id.trending_ad_price);
            ad_rental_option =(TextView) itemView.findViewById(R.id.trending_ad_rental_option);
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
