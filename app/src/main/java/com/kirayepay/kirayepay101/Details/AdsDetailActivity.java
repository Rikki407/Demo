package com.kirayepay.kirayepay101.Details;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kirayepay.kirayepay101.AdsOfCategory.AdsByCategoryActivity;
import com.kirayepay.kirayepay101.MainActivity;
import com.kirayepay.kirayepay101.Network.Responses.AdsResponse;
import com.kirayepay.kirayepay101.Network.Responses.PostContainments;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rikki on 8/11/17.
 */

public class AdsDetailActivity extends AppCompatActivity implements View.OnClickListener {
    int ad_id, cat_id, parent_id;
    TextView adsTitle, rentalAmount, rentalOption, securityDeposit, description, condition,
            quantity, availability, name, phone, email, address, view_all, show_info_text;
    ImageView adImage, callImage, mailImage,move_right,move_left;
    boolean already_expanded = false;
    String seller_city, seller_district, seller_locality;
    Toolbar toolbar;
    public CollapsingToolbarLayout collapsing_toolbar;
    Context mContext;
    LinearLayout seller_info,show_seller_info,first_child;
    ImageView expand_circle;

    int pager_position = 0;

    ArrayList<String> other_images;
    PhotosPagerAdapter photosPagerAdapter;
    ViewPager photosPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_detail);
        mContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        first_child = (LinearLayout) findViewById(R.id.first_child);
        first_child.setOnClickListener(this);

        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar.setNavigationIcon(R.drawable.back_button);
        setSupportActionBar(toolbar);

        other_images = new ArrayList<>();
        photosPager = (ViewPager) findViewById(R.id.photos_viewpager);

        photosPagerAdapter = new PhotosPagerAdapter(this,other_images,collapsing_toolbar);
        photosPager.setAdapter(photosPagerAdapter);
        photosPager.setOffscreenPageLimit(2);
        photosPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pager_position = position;

                if(pager_position==(other_images.size()-1)) move_right.setVisibility(View.GONE);
                else move_right.setVisibility(View.VISIBLE);

                if(pager_position==0) move_left.setVisibility(View.GONE);
                else move_left.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        view_all = (TextView) findViewById(R.id.view_all);
        view_all.setOnClickListener(this);
        seller_info = (LinearLayout) findViewById(R.id.seller_info);
        show_seller_info = (LinearLayout) findViewById(R.id.show_seller_info);
        show_seller_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapseExpandTextView();
            }
        });
        expand_circle = (ImageView) findViewById(R.id.expand_circle);
        show_info_text = (TextView) findViewById(R.id.show_info_text);
        move_left = (ImageView) findViewById(R.id.move_left);
        move_right = (ImageView) findViewById(R.id.move_right);
        move_left.setOnClickListener(this);
        move_right.setOnClickListener(this);
        adsTitle = (TextView) findViewById(R.id.ads_detail_title);
        rentalAmount = (TextView) findViewById(R.id.ads_detail_rental_amount);
        rentalOption = (TextView) findViewById(R.id.ads_detail_rental_option);
        securityDeposit = (TextView) findViewById(R.id.ads_detail_security_deposit);
        description = (TextView) findViewById(R.id.ads_detail_description);
        condition = (TextView) findViewById(R.id.ads_detail_condition);
        quantity = (TextView) findViewById(R.id.ads_detail_quantity);
        availability = (TextView) findViewById(R.id.ads_detail_Availability);
        name = (TextView) findViewById(R.id.ads_detail_name);
        phone = (TextView) findViewById(R.id.ads_detail_phone);
        email = (TextView) findViewById(R.id.ads_detail_email);
        address = (TextView) findViewById(R.id.ads_detail_address);
//        adImage = (ImageView) findViewById(R.id.ads_detail_images);
        callImage = (ImageView) findViewById(R.id.call_seller);
        mailImage = (ImageView) findViewById(R.id.mail_seller);
        callImage.setOnClickListener(this);
        mailImage.setOnClickListener(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ad_id = extras.getInt("ad_id");
            cat_id = extras.getInt("cat_id");
            fetchAdsDetails(ad_id);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("Ad_Id", ad_id);
        bundle.putInt("CategoryId", cat_id);

        RelatedAdsFragment fragment = new RelatedAdsFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.related_ads_holder, fragment).commit();
    }

    void collapseExpandTextView() {
        if (seller_info.getVisibility() == View.GONE) {
            // it's collapsed - expand it
            if(!already_expanded){
                sendSurvey();
                already_expanded = true;
            }
            show_info_text.setText("Seller's Info");
            seller_info.setVisibility(View.VISIBLE);
            expand_circle.setImageResource(R.drawable.blue_remove_circle);
        } else {
            // it's expanded - collapse it
            show_info_text.setText("Show Seller's Info");
            seller_info.setVisibility(View.GONE);
            expand_circle.setImageResource(R.drawable.blue_expand_circle);
        }

    }

    private void sendSurvey()
    {
        ApiInterface apiInterface = ApiClient.getApiInterface();
        SharedPreferences prefs = getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE);
        final String  user_id = prefs.getString(Acquire.USER_ID,"No Id");
        Call<PostContainments> sendSurveyCall = apiInterface.userSendSurvey(user_id,""+ad_id,Acquire.IP_ADDRESS);
        sendSurveyCall.enqueue(new Callback<PostContainments>() {
            @Override
            public void onResponse(Call<PostContainments> call, Response<PostContainments> response) {
                Log.e("survey_message",""+Acquire.IP_ADDRESS+" "+response.body().getMessage());
            }

            @Override
            public void onFailure(Call<PostContainments> call, Throwable t) {
                Log.e("survey_message",""+Acquire.IP_ADDRESS+" "+t.getMessage());
            }
        });
    }

    private void fetchAdsDetails(final int ad_id) {
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<AdsResponse> arrayListCall = apiInterface.getAdsDetails("" + ad_id, Acquire.API_KEY);

        arrayListCall.enqueue(new Callback<AdsResponse>() {
            @Override
            public void onResponse(Call<AdsResponse> call, Response<AdsResponse> response) {
                if (response.isSuccessful()) {

                    Log.e("ad_id", "" + ad_id);
                    setDetailsFields(response.body());
                }
            }
            @Override
            public void onFailure(Call<AdsResponse> call, Throwable t) {
                Log.e("otherImage", "cc" + t.getMessage());
            }
        });
    }


    private void setDetailsFields(AdsResponse body) {
        for(int i=0;i<body.getImages().size();i++) {
            other_images.add(body.getImages().get(i).getOther_image());
        }
        if(other_images.size()>1) {
            move_right.setVisibility(View.VISIBLE);
        }
        photosPagerAdapter.notifyDataSetChanged();
        collapsing_toolbar.setTitle("" + body.getAds().get(0).getAds_title());
        collapsing_toolbar.setCollapsedTitleTextColor(Color.parseColor("#3d3c3c"));
        adsTitle.setText("" + body.getAds().get(0).getAds_title());

        Log.e("ad_id", "" + ad_id);

        if (!(body.getAds().get(0).getRental_amount() == 0))
            rentalAmount.setText("₹" + body.getAds().get(0).getRental_amount() + "/-");
        else {
            removeTheView(rentalAmount.getParent().getParent());
        }
        parent_id = body.getAds().get(0).getParent_id();
        rentalOption.setText("" + body.getAds().get(0).getRental_option());

        description.setText("" + body.getAds().get(0).getDescription());
        condition.setText("" + body.getAds().get(0).getCondition());
        quantity.setText("" + body.getAds().get(0).getQuantity());
        availability.setText("" + body.getAds().get(0).getAvailability());
        name.setText("" + body.getAds().get(0).getSeller_name());
        seller_city = (body.getAds().get(0).getSeller_city() == null) ? "" : body.getAds().get(0).getSeller_city() + ", ";
        seller_district = (body.getAds().get(0).getSeller_district() == null) ? "" : body.getAds().get(0).getSeller_district() + ", ";
        seller_locality = (body.getAds().get(0).getSeller_locality() == null) ? "" : body.getAds().get(0).getSeller_locality();
        if (!(body.getAds().get(0).getSecurity_deposit() == 0))
            securityDeposit.setText("₹" + body.getAds().get(0).getSecurity_deposit());
        else {
            removeTheView(securityDeposit.getParent());
        }
        if (!(body.getAds().get(0).getSeller_phone() == 0))
            phone.setText("" + body.getAds().get(0).getSeller_phone());
        else {
            removeTheView(phone.getParent());
        }
        if (!(body.getAds().get(0).getSeller_email() == null))
            email.setText("" + body.getAds().get(0).getSeller_email());
        else {
            removeTheView(email.getParent());
        }
        if (seller_city.isEmpty() && seller_locality.isEmpty() && seller_district.isEmpty()) {
            removeTheView(address.getParent());
        } else {
            address.setText(seller_city + seller_district + seller_locality);
        }
    }

    private void callSeller(String seller_phno) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + seller_phno));
        startActivity(Intent.createChooser(intent, "Select an app"));
    }

    private void mailSeller(String seller_email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + seller_email));
        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    void removeTheView(ViewParent viewParent) {
        ((ViewGroup) viewParent).removeAllViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_seller:
                if (phone.getText() != null && !phone.getText().toString().isEmpty())
                    callSeller(phone.getText().toString());
                else Toast.makeText(this, "No Phone Number Available", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mail_seller:
                if (email.getText() != null && !email.getText().toString().isEmpty())
                    mailSeller(email.getText().toString());
                else Toast.makeText(this, "No Email Available", Toast.LENGTH_SHORT).show();
                break;
            case R.id.view_all:
                Intent intent = new Intent(mContext, AdsByCategoryActivity.class);
                intent.putExtra("ParentId", parent_id);
                intent.putExtra("CategoryId", cat_id);
                mContext.startActivity(intent);
                break;
            case R.id.move_right :
                pager_position++;
                if(pager_position<other_images.size()) photosPager.setCurrentItem(pager_position);
                break;
            case R.id.move_left :
                pager_position--;
                if(pager_position>=0) photosPager.setCurrentItem(pager_position);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_home:
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.share_detail_link:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "" + adsTitle.getText().toString());
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.kirayepay.com/Product-detail/" + ad_id);
                startActivity(Intent.createChooser(shareIntent, "Share"));
                break;
            default:
                super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
