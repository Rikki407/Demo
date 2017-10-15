package com.kirayepay.kirayepay101.Fab.ADs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.kirayepay.kirayepay101.R;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.RikkiClasses.CategoryHierarchy;

import java.util.ArrayList;

/**
 * Created by rikki on 8/12/17.
 */

public class GetAdsDetails extends AppCompatActivity {
    EditText title, description, manufacture, condition, quantity, rental_option, rental_amount, security_deposit;
    AutoCompleteTextView category_text, subcat1_text, subcat2_text;
    TextView goto_ads_location;
    ArrayList<String> main_categories, sub_1_categories, sub_2_categories;
    Context mContext;
    CardView subcat_1_card, subcat_2_card;
    ArrayAdapter<String> main_adapter, sub_1_adapter, sub_2_adapter;
    private String main_img_uri, other_img_uri_1, other_img_uri_2, other_img_uri_3, other_img_uri_4;


    private int main_cat_id = -1, sub_cat_1_id = -1, sub_cat_2_id = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_details);
        Intent intent = getIntent();
        mContext = this;
        main_img_uri = intent.getStringExtra("main_img_uri");
        other_img_uri_1 = intent.getStringExtra("other_img_uri_1");
        other_img_uri_2 = intent.getStringExtra("other_img_uri_2");
        other_img_uri_3 = intent.getStringExtra("other_img_uri_3");
        other_img_uri_4 = intent.getStringExtra("other_img_uri_4");

        Log.e("show_uri"," - "+main_img_uri+"\n"+other_img_uri_1+"\n"+other_img_uri_2+"\n"+other_img_uri_3+"\n"+other_img_uri_4);
        goto_ads_location = (TextView) findViewById(R.id.loc_activity);
        goto_ads_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(GetAdsDetails.this,GetAdsLocation.class);
                Acquire.MAIN_URI = main_img_uri;
                Log.e("show_uri",""+main_img_uri);
                intent2.putExtra("main_uri",main_img_uri);
                intent2.putExtra("other_uri_1",other_img_uri_1);
                intent2.putExtra("other_uri_2",other_img_uri_2);
                intent2.putExtra("other_uri_3",other_img_uri_3);
                intent2.putExtra("other_uri_4",other_img_uri_4);
                startActivity(intent2);
            }
        });
        subcat_1_card = (CardView) findViewById(R.id.post_ad_subcat1_card);
        subcat_2_card = (CardView) findViewById(R.id.post_ad_subcat2_card);
        category_text = (AutoCompleteTextView) findViewById(R.id.post_ads_category);
        subcat1_text = (AutoCompleteTextView) findViewById(R.id.post_ads_subcategory_1);
        subcat2_text = (AutoCompleteTextView) findViewById(R.id.post_ads_subcategory_2);
        main_categories = new ArrayList<>();
        sub_1_categories = new ArrayList<>();
        sub_2_categories = new ArrayList<>();


        for (int i = 0; i < CategoryHierarchy.getSubcategories().get(0).size(); i++) {
            main_categories.add(CategoryHierarchy.getSubcategories().get(0).get(i).getCategory_name());
            Log.e("main_categoriesxxx", main_categories.get(i));
        }
        main_adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, main_categories);
        category_text.setThreshold(0);


        category_text.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                // TODO Auto-generated method stub
                View view = getWindow().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return false;
            }
        });
        category_text.setAdapter(main_adapter);
        category_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category_text.showDropDown();
                category_text.requestFocus();
            }
        });

        category_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("position_cat", "" + CategoryHierarchy.getSubcategories().get(0).get(position).getCategory_name());
                if (main_cat_id != CategoryHierarchy.getSubcategories().get(0).get(position).getCategory_id()) {
                    sub_1_categories.clear();
                    subcat_2_card.setVisibility(View.GONE);
                    subcat1_text.setText("");
                    subcat1_text.setHint("optional....");
                    subcat1_text.setHintTextColor(Color.parseColor("#e1dddd"));
                    sub_cat_2_id = -1;
                    sub_cat_1_id = -1;
                    main_cat_id = CategoryHierarchy.getSubcategories().get(0).get(position).getCategory_id();

                    if (CategoryHierarchy.getSubcategories().get(main_cat_id) != null) {
                        subcat_1_card.setVisibility(View.VISIBLE);
                        for (int i = 0; i < CategoryHierarchy.getSubcategories().get(main_cat_id).size(); i++) {
                            sub_1_categories.add(CategoryHierarchy.getSubcategories().get(main_cat_id).get(i).getCategory_name());
                        }
                        sub_1_adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_expandable_list_item_1, sub_1_categories);
                        subcat1_text.setThreshold(0);
                        subcat1_text.setOnTouchListener(new View.OnTouchListener() {

                            @SuppressLint("ClickableViewAccessibility")
                            @Override
                            public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                                // TODO Auto-generated method stub
                                View view = getWindow().getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }
                                return false;
                            }
                        });
                        subcat1_text.setAdapter(sub_1_adapter);
                        subcat1_text.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                subcat1_text.showDropDown();
                                subcat1_text.requestFocus();
                            }
                        });

                    } else {
                        subcat_1_card.setVisibility(View.GONE);
                    }
                }
            }
        });

        subcat1_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         if (sub_cat_1_id != CategoryHierarchy.getSubcategories().get(main_cat_id).get(position).getCategory_id()) {
                             sub_2_categories.clear();
                             subcat2_text.setText("");
                             subcat2_text.setHint("optional....");
                             subcat2_text.setHintTextColor(Color.WHITE);
                             sub_cat_2_id = -1;
                             sub_cat_1_id = CategoryHierarchy.getSubcategories().get(main_cat_id).get(position).getCategory_id();

                             if (CategoryHierarchy.getSubcategories().get(sub_cat_1_id) != null) {
                                 subcat_2_card.setVisibility(View.VISIBLE);
                                 for (int i = 0; i < CategoryHierarchy.getSubcategories().get(sub_cat_1_id).size(); i++) {
                                     sub_2_categories.add(CategoryHierarchy.getSubcategories().get(sub_cat_1_id).get(i).getCategory_name());
                                 }
                                 sub_2_adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_expandable_list_item_1, sub_2_categories);
                                 subcat2_text.setThreshold(0);
                                 subcat2_text.setOnTouchListener(new View.OnTouchListener() {

                                     @SuppressLint("ClickableViewAccessibility")
                                     @Override
                                     public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                                         // TODO Auto-generated method stub
                                         View view = getWindow().getCurrentFocus();
                                         if (view != null) {
                                             InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                             imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                         }
                                         return false;
                                     }
                                 });
                                 subcat2_text.setAdapter(sub_2_adapter);
                                 subcat2_text.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         subcat2_text.showDropDown();
                                         subcat2_text.requestFocus();
                                     }
                                 });

                             } else {
                                 subcat_2_card.setVisibility(View.GONE);
                             }
                         }
                     }
                 }
                );

        subcat2_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sub_cat_2_id = CategoryHierarchy.getSubcategories().get(sub_cat_1_id).get(position).getCategory_id();
            }
        });

        title = (EditText) findViewById(R.id.post_ads_title);
        description = (EditText) findViewById(R.id.post_ads_description);
        manufacture = (EditText) findViewById(R.id.post_ads_manufacturer);
        condition = (EditText) findViewById(R.id.post_ads_condition);
        quantity = (EditText) findViewById(R.id.post_ads_quantity);
        rental_option = (EditText) findViewById(R.id.post_ads_rental_option);
        rental_amount = (EditText) findViewById(R.id.post_ads_rental_amount);
        security_deposit = (EditText) findViewById(R.id.post_ads_security_deposit);
    }
}
