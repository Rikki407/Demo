package com.kirayepay.KirayePay_Rikki.Navigation.Menu.AllCategoriesList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;


import com.kirayepay.KirayePay_Rikki.AdsOfCategory.AdsByCategoryActivity;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.CategoryHierarchy;
import com.kirayepay.KirayePay_Rikki.R;
import com.squareup.picasso.Picasso;


/**
 * Created by rikki on 8/5/17.
 */

public class MainCategoriesAdapter extends BaseExpandableListAdapter {
    private Context context;

    public MainCategoriesAdapter(Context context) {
        this.context = context;
    }
    @Override
    public Object getChild(int arg0, int arg1) {
        return arg1;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        if(CategoryHierarchy.getSubcategories().get(0)!=null)
        {
            return CategoryHierarchy.getSubcategories().get(0).size();
        }
        else
            return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubCategoryListView secondLevelELV = new SubCategoryListView(context);
        secondLevelELV.setAdapter(new SubCategoriesAdapter(context,groupPosition));
        secondLevelELV.setGroupIndicator(null);
        secondLevelELV.setDivider(null);
        secondLevelELV.setDividerHeight(0);
        return secondLevelELV;

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,  View convertView, final ViewGroup parent) {
        MainCatViewHolder holder;
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_main, null);
            holder = new MainCatViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.main_cat_text);
            holder.expColImageView = (ImageView) convertView.findViewById(R.id.main_expcol_image);
            holder.mainCatImageView = (ImageView) convertView.findViewById(R.id.category_image);
            convertView.setTag(holder);
        }
        else
        {
            holder = (MainCatViewHolder) convertView.getTag();
        }
        final int group_id = CategoryHierarchy.getSubcategories().get(0).get(groupPosition).getCategory_id();
        if(CategoryHierarchy.getSubcategories().get(group_id)!=null) {

            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isExpanded) ((ExpandableListView) parent).collapseGroup(groupPosition);
                    else
                    {
                        Acquire.SUBCAT_NUM = CategoryHierarchy.getSubcategories().get(group_id).size();
                        ((ExpandableListView) parent).expandGroup(groupPosition, true);
                    }
                }
            });
            if (isExpanded) {
                holder.expColImageView.setImageResource(R.drawable.collapse_circle);
            }
            else {
                holder.expColImageView.setImageResource(R.drawable.expand_circle);
            }
        }
        else {
            holder.expColImageView.setImageResource(android.R.color.transparent);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent open_category = new Intent(context, AdsByCategoryActivity.class);
                    open_category.putExtra("CategoryId",group_id);
                    open_category.putExtra("ParentId",0);
                    context.startActivity(open_category);
                }
            });
        }

        holder.textView.setText(CategoryHierarchy.getSubcategories().get(0).get(groupPosition).getCategory_name());
        Picasso.with(context)
                .load(CategoryHierarchy.getSubcategories().get(0).get(groupPosition).getCategory_image_url())
                .into(holder.mainCatImageView);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class MainCatViewHolder
    {
        private TextView textView;
        private ImageView mainCatImageView;
        private ImageView expColImageView;
    }


}
