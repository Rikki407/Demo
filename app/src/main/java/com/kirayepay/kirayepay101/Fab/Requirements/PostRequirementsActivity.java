package com.kirayepay.kirayepay101.Fab.Requirements;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.kirayepay.kirayepay101.MainActivity;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.RikkiClasses.CategoryHierarchy;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.PostContainments;
import com.kirayepay.kirayepay101.R;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRequirementsActivity extends AppCompatActivity implements View.OnClickListener {
    EditText title, requirement, from, till;
    AutoCompleteTextView category_text, subcat1_text, subcat2_text;
    Button post_req_bttn;
    long from_time, till_time;
    ArrayList<String> main_categories, sub_1_categories, sub_2_categories;
    Context mContext;
    CardView subcat_1_card, subcat_2_card;
    ArrayAdapter<String> main_adapter, sub_1_adapter, sub_2_adapter;
    String user_id;
    private int main_cat_id = -1, sub_cat_1_id = -1, sub_cat_2_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_requirements);
        mContext = this;

        SharedPreferences prefs = getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE);
        user_id = prefs.getString(Acquire.USER_ID,"");

        subcat_1_card = (CardView) findViewById(R.id.sub_1_cat_card);
        subcat_2_card = (CardView) findViewById(R.id.sub_2_cat_card);
        category_text = (AutoCompleteTextView) findViewById(R.id.post_req_category);
        subcat1_text = (AutoCompleteTextView) findViewById(R.id.post_req_sub_1_category);
        subcat2_text = (AutoCompleteTextView) findViewById(R.id.post_req_sub_2_category);

        main_categories = new ArrayList<>();
        sub_1_categories = new ArrayList<>();
        sub_2_categories = new ArrayList<>();

        for (int i = 0; i < CategoryHierarchy.getSubcategories().get(0).size(); i++) {
            main_categories.add(CategoryHierarchy.getSubcategories().get(0).get(i).getCategory_name());
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


        title = (EditText) findViewById(R.id.post_req_title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setCursorVisible(true);
            }
        });


        requirement = (EditText) findViewById(R.id.post_req_requirement);
        from = (EditText) findViewById(R.id.post_req_from);
        from.setOnClickListener(this);
        till = (EditText) findViewById(R.id.post_req_till);
        till.setOnClickListener(this);
        post_req_bttn = (Button) findViewById(R.id.post_req_bttn);
        post_req_bttn.setOnClickListener(this);

    }

    private void postThisRequirement() {

        ApiInterface apiInterface = ApiClient.getApiInterface();
        String sub_cat = (subcat1_text.getText().toString().isEmpty())? null:""+sub_cat_1_id;
        String sub_cat2 = (subcat2_text.getText().toString().isEmpty())? null:""+sub_cat_2_id;

        Call<PostContainments> postReqCall = apiInterface.postRequirements(user_id,title.getText().toString(),""+main_cat_id,sub_cat,sub_cat2,requirement.getText().toString(),from.getText().toString(),till.getText().toString());
        postReqCall.enqueue(new Callback<PostContainments>() {
            @Override
            public void onResponse(Call<PostContainments> call, Response<PostContainments> response) {
                Toast.makeText(PostRequirementsActivity.this, "Requirement Posted", Toast.LENGTH_LONG).show();
                Intent i = new Intent(mContext,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<PostContainments> call, Throwable t) {
                Toast.makeText(PostRequirementsActivity.this, "Can Not Post Requirement", Toast.LENGTH_LONG).show();
            }
        });
    }

    void startDatePicker(View view) {
        final EditText edit_text = (EditText) view;
        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog mDatePicker = new DatePickerDialog(PostRequirementsActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                edit_text.setText("" + selectedyear + "-" + selectedmonth + "-" + selectedday);

                if (edit_text.getId() == R.id.post_req_from) {
                    from_time = mcurrentDate.getTimeInMillis();
                }
                else{
                    till_time = mcurrentDate.getTimeInMillis();
                }
            }
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_req_bttn:
                if(category_text.getText().toString().isEmpty()||title.getText().toString().isEmpty()||requirement.getText().toString().isEmpty()||from.getText().toString().isEmpty()||till.getText().toString().isEmpty())
                {
                    Toast.makeText(mContext,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                postThisRequirement();
                break;
            case R.id.post_req_from:
                startDatePicker(from);
                break;
            case R.id.post_req_till:
                startDatePicker(till);
                break;
        }
    }
}
