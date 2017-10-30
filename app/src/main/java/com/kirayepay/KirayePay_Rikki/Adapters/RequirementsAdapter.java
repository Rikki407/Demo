package com.kirayepay.KirayePay_Rikki.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kirayepay.KirayePay_Rikki.Details.RequirementsDetailsActivity;
import com.kirayepay.KirayePay_Rikki.Network.Responses.RequirementContainments;
import com.kirayepay.KirayePay_Rikki.R;

import java.util.ArrayList;

/**
 * Created by rikki on 8/16/17.
 */

public class RequirementsAdapter extends RecyclerView.Adapter<RequirementsAdapter.RequirementsViewHolder> {



    private Context mContext;
    private ArrayList<RequirementContainments> requirementsList;
    private GridLayoutManager mLayoutManager;
    private int calling;

    public RequirementsAdapter(Context mContext, ArrayList<RequirementContainments> requirementsList, GridLayoutManager mLayoutManager, int calling) {
        this.mContext = mContext;
        this.requirementsList = requirementsList;
        this.mLayoutManager = mLayoutManager;
        this.calling = calling;
    }

    @Override
    public RequirementsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_requirement_list, parent, false);
        return new RequirementsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequirementsAdapter.RequirementsViewHolder holder, int position) {

        RequirementContainments requirementContainments = requirementsList.get(position);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double viewHeight = displayMetrics.heightPixels / 7.0;

        if (holder.req_Card != null) holder.req_Card.getLayoutParams().height = (int) viewHeight;

        holder.req_title.setText(requirementContainments.getTitle());
        holder.req_id = requirementContainments.getId();
        holder.req_category.setText(requirementContainments.getCategory());
        holder.req_from.setText(requirementContainments.getWhen());
        Log.e("getFrom", "oci" + " " + requirementContainments.getWhen());
        holder.req_till.setText(requirementContainments.getTill());

        holder.reqm_title = requirementContainments.getTitle();
        holder.reqm_from = requirementContainments.getWhen();
        holder.reqm_till = requirementContainments.getTill();
        holder.req_desc = requirementContainments.getRequirement();
        holder.req_cat = requirementContainments.getCategory();
        if (requirementContainments.getUser().size()>0) {
            holder.user_name = requirementContainments.getUser().get(0).getName();
            holder.user_email = requirementContainments.getUser().get(0).getEmail();
            holder.user_locality = requirementContainments.getUser().get(0).getLocality();
            holder.user_city = requirementContainments.getUser().get(0).getCity();
            holder.user_district = requirementContainments.getUser().get(0).getDistrict();
            holder.user_city = requirementContainments.getUser().get(0).getCity();
            holder.user_state = requirementContainments.getUser().get(0).getState();
            holder.user_pincode = requirementContainments.getUser().get(0).getPincode();
            holder.user_phone = requirementContainments.getUser().get(0).getPhone();
        }

    }

    @Override
    public int getItemCount() {
        return requirementsList.size();
    }

    public class RequirementsViewHolder extends RecyclerView.ViewHolder {
        int req_id;
        CardView req_Card;
        TextView req_title;
        TextView req_category;
        TextView req_from;
        TextView req_till;
        String reqm_title, reqm_from, reqm_till, req_desc, req_cat, user_name, user_email, user_locality, user_city, user_district, user_state;
        long user_phone;
        int user_pincode;

        public RequirementsViewHolder(final View itemView) {
            super(itemView);

            req_Card = (CardView) itemView.findViewById(R.id.cardview_req_list);
            req_title = (TextView) itemView.findViewById(R.id.reqmnt_title);
            req_category = (TextView) itemView.findViewById(R.id.reqmnt_category);
            req_from = (TextView) itemView.findViewById(R.id.reqmnt_from);
            req_till = (TextView) itemView.findViewById(R.id.reqmnt_till);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, RequirementsDetailsActivity.class);
                    intent.putExtra("req_title", reqm_title);
                    intent.putExtra("req_desc", req_desc);
                    intent.putExtra("req_from", reqm_from);
                    intent.putExtra("req_till", reqm_till);
                    intent.putExtra("req_cat", req_cat);

                    intent.putExtra("user_name", user_name);
                    intent.putExtra("user_email", user_email);
                    intent.putExtra("user_phone", user_phone);
                    intent.putExtra("user_locality", user_locality);
                    intent.putExtra("user_city", user_city);
                    intent.putExtra("user_district", user_district);
                    intent.putExtra("user_state", user_state);
                    intent.putExtra("user_pincode", user_pincode);
                    context.startActivity(intent);
                }
            });
        }
    }
}
