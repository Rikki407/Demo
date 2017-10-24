package com.kirayepay.kirayepay101.Navigation.Menu.AllCategoriesList;

import android.content.Context;
import android.content.Intent;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirayepay.kirayepay101.AdsOfCategory.AdsByCategoryActivity;
import com.kirayepay.kirayepay101.RikkiClasses.CategoryHierarchy;
import com.kirayepay.kirayepay101.R;

/**
 * Created by rikki on 8/5/17.
 */

public class SubCategoriesAdapter extends BaseExpandableListAdapter
{
    private Context context;
    private int parent_position;

    public SubCategoriesAdapter(Context context,int parent_position) {
        this.context = context;
        this.parent_position = parent_position;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        int group_id = CategoryHierarchy.getSubcategories().get(0).get(parent_position).getCategory_id();
        if(CategoryHierarchy.getSubcategories().get(group_id)==null)
        {
            return 0;
        }
        return CategoryHierarchy.getSubcategories().get(group_id).size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int grand_parent_id = CategoryHierarchy.getSubcategories().get(0).get(parent_position).getCategory_id();
        int parent_id = CategoryHierarchy.getSubcategories().get(grand_parent_id).get(groupPosition).getCategory_id();
        if(CategoryHierarchy.getSubcategories().get(parent_id)==null)
        {
            return 0;
        }
        return CategoryHierarchy.getSubcategories().get(parent_id).size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent)
    {
        SubCatViewHolder holder;
        final int parent_id = CategoryHierarchy.getSubcategories().get(0).get(parent_position).getCategory_id();
        final int group_id  = CategoryHierarchy.getSubcategories().get(parent_id).get(groupPosition).getCategory_id();

            if (convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.category_sub, null);
                holder = new SubCatViewHolder();
                holder.sub_text_view = (TextView) convertView.findViewById(R.id.sub_cat_text);
                holder.expColImageView = (ImageView) convertView.findViewById(R.id.sub_expcol_image);
                convertView.setTag(holder);
            }
            else
            {
                holder = (SubCatViewHolder) convertView.getTag();
            }

            holder.sub_text_view.setText(CategoryHierarchy.getSubcategories().get(parent_id).get(groupPosition).getCategory_name());


        if(CategoryHierarchy.getSubcategories().get(group_id)!=null)
        {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isExpanded) ((ExpandableListView) parent).collapseGroup(groupPosition);
                    else ((ExpandableListView) parent).expandGroup(groupPosition, true);
                }
            });

            if (isExpanded) {

                holder.expColImageView.setImageResource(R.drawable.collapse_circle);
            }
            else {
                holder.expColImageView.setImageResource(R.drawable.expand_circle);
            }
        }
        else
        {
            holder.expColImageView.setImageResource(android.R.color.transparent);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent open_category = new Intent(context, AdsByCategoryActivity.class);
                    open_category.putExtra("CategoryId",group_id);
                    open_category.putExtra("ParentId",parent_id);
                    context.startActivity(open_category);
                }
            });
        }
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubOfSubCatViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_sub_sub, null);
            holder = new SubOfSubCatViewHolder();
            holder.sub_of_sub_text_view = (TextView) convertView.findViewById(R.id.sub_sub_cat_text);
            convertView.setTag(holder);
        }
        else
        {
            holder = (SubOfSubCatViewHolder) convertView.getTag();
        }
        int grand_parent_id = CategoryHierarchy.getSubcategories().get(0).get(parent_position).getCategory_id();
        final int parent_id = CategoryHierarchy.getSubcategories().get(grand_parent_id).get(groupPosition).getCategory_id();
        final int group_id = CategoryHierarchy.getSubcategories().get(parent_id).get(childPosition).getCategory_id();
        holder.sub_of_sub_text_view.setText(CategoryHierarchy.getSubcategories().get(parent_id).get(childPosition).getCategory_name());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_category = new Intent(context, AdsByCategoryActivity.class);
                open_category.putExtra("CategoryId",group_id);
                open_category.putExtra("ParentId",parent_id);
                context.startActivity(open_category);
            }
        });
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class SubCatViewHolder
    {
        private TextView sub_text_view;
        private ImageView expColImageView;
    }
    static class SubOfSubCatViewHolder
    {
        private TextView sub_of_sub_text_view;
    }
}
