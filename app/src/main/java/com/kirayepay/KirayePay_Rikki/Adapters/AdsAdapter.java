package com.kirayepay.KirayePay_Rikki.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kirayepay.KirayePay_Rikki.Details.AdsDetailActivity;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.Network.Responses.AdsContainments;
import com.kirayepay.KirayePay_Rikki.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rikki on 8/8/17.
 */

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder>
{
    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_THREE = 2;

    private static final int VIEW_TYPE_GRID = 1;
    private static final int VIEW_TYPE_LIST = 2;

    private Context mContext;
    private ArrayList<AdsContainments> adsListOfCategories;
    private GridLayoutManager mLayoutManager;
    private int calling;

    public AdsAdapter(Context mContext, ArrayList<AdsContainments> adsListOfCategories,GridLayoutManager mLayoutManager, int calling) {
        this.mContext = mContext;
        this.adsListOfCategories = adsListOfCategories;
        this.mLayoutManager = mLayoutManager;
        this.calling = calling;
    }

    @Override
    public int getItemViewType(int position) {
        int spanCount = mLayoutManager.getSpanCount();
        if (spanCount == SPAN_COUNT_ONE) {
            return VIEW_TYPE_LIST;
        } else {
            return VIEW_TYPE_GRID;
        }
    }

    @Override
    public AdsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        if (viewType == VIEW_TYPE_GRID) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_adslist_grid, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_adslist_list, parent, false);
        }
        return new AdsViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(AdsViewHolder holder, int position)
    {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        AdsContainments ads_contenments = adsListOfCategories.get(position);

        if(Acquire.IS_LISTVIEW||calling==Acquire.MOSTVIEWED_CALL)
        {
            double viewHeight = displayMetrics.heightPixels/7;
            double viewWidth = displayMetrics.widthPixels/2.5;
            holder.cardView.getLayoutParams().height = (int)viewHeight;
            holder.ad_image.getLayoutParams().height = (int)viewHeight;
            holder.ad_image.getLayoutParams().width = (int)viewHeight;
            holder.ad_title.setText(ads_contenments.getAds_title());
            if(holder.ad_location!=null) holder.ad_location.setText(ads_contenments.getAddr_city());
            if(holder.ad_rental_price!=null) holder.ad_rental_price.setText((ads_contenments.getRental_amount()==0)?"":"₹ "+ads_contenments.getRental_amount());
            if(holder.ad_rental_option!=null) holder.ad_rental_option.setText((ads_contenments.getRental_option().equals("0"))?"":"  /- "+ads_contenments.getRental_option());
        }
        else
        {
            holder.ad_title.setText(ads_contenments.getAds_title());
            if(holder.ad_rental_price!=null) holder.ad_rental_price.setText((ads_contenments.getRental_amount()==0)?"":"₹ "+ads_contenments.getRental_amount());
            if(holder.ad_rental_option!=null) holder.ad_rental_option.setText((ads_contenments.getRental_option().equals("0"))?"":"  /- "+ads_contenments.getRental_option());
        }



        holder.ad_id = ads_contenments.getAds_id();
        holder.cat_id = ads_contenments.getCategory_id();

        if(ads_contenments.getImage()!=null&&!ads_contenments.getImage().isEmpty())
        {
            Picasso.with(mContext).load(ads_contenments.getImage()).into(holder.ad_image);
        }
        else{
            holder.ad_image.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.kp_wtr_mk));
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
        LinearLayout grid_linear_layout;
        CardView cardView;
        ImageView ad_image;
        TextView ad_title;
        TextView ad_location;
        TextView ad_rental_option;
        TextView ad_rental_price;

        public AdsViewHolder(final View itemView, int viewType) {
            super(itemView);

            if(viewType== VIEW_TYPE_GRID&&calling!=Acquire.MOSTVIEWED_CALL) {
                grid_linear_layout = (LinearLayout) itemView.findViewById(R.id.grid_layout_design);
                cardView = (CardView) itemView.findViewById(R.id.cardview_grid);
                ad_image = (ImageView) itemView.findViewById(R.id.grid_ad_image);
                ad_title = (TextView) itemView.findViewById(R.id.grid_ad_title);
                ad_rental_price = (TextView) itemView.findViewById(R.id.grid_ad_price);
                ad_rental_option = (TextView) itemView.findViewById(R.id.grid_ad_rental_option);
            }
            else {
                cardView = (CardView) itemView.findViewById(R.id.cardview_list);
                ad_image = (ImageView) itemView.findViewById(R.id.ad_image);
                ad_title = (TextView) itemView.findViewById(R.id.ad_title);
                ad_location = (TextView) itemView.findViewById(R.id.ad_location);
                ad_rental_price = (TextView) itemView.findViewById(R.id.ad_rental_price);
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
