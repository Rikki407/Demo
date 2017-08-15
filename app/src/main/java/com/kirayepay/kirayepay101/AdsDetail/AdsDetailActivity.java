package com.kirayepay.kirayepay101.AdsDetail;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kirayepay.kirayepay101.AdsOfCategory.AdsListFragment;
import com.kirayepay.kirayepay101.MyClasses.Acquire;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.AdsContainments;
import com.kirayepay.kirayepay101.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rikki on 8/11/17.
 */

public class AdsDetailActivity extends AppCompatActivity implements View.OnClickListener
{
    int ad_id,cat_id;
    TextView adsTitle,rentalAmount,rentalOption,securityDeposit,description,condition,quantity
            ,availability,name,phone,email,address;
    ImageView adImage,callImage,mailImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_detail);
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
        adImage = (ImageView) findViewById(R.id.ads_detail_images);
        callImage = (ImageView) findViewById(R.id.call_seller);
        mailImage = (ImageView) findViewById(R.id.mail_seller);
        callImage.setOnClickListener(this);
        mailImage.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            ad_id = extras.getInt("ad_id");
            cat_id = extras.getInt("cat_id");
            fetchAdsDetails(ad_id);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("Ad_Id",ad_id);
        bundle.putInt("CategoryId",cat_id);
        RelatedAdsFragment fragment = new RelatedAdsFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.related_ads_holder,fragment).commit();

    }

private void fetchAdsDetails(final int ad_id)
{
    ApiInterface apiInterface = ApiClient.getApiInterface();
    Call<ArrayList<AdsContainments>> arrayListCall = apiInterface.getAdsDetails(""+ad_id, Acquire.API_KEY);

    arrayListCall.enqueue(new Callback<ArrayList<AdsContainments>>() {
        @Override
        public void onResponse(Call<ArrayList<AdsContainments>> call, Response<ArrayList<AdsContainments>> response) {
            if(response.isSuccessful()) {
                Log.e("otherImage",""+response.body().get(0));
                Log.e("ad_id",""+ad_id);
                setDetailsFields(response.body());
            }
        }
        @Override
        public void onFailure(Call<ArrayList<AdsContainments>> call, Throwable t) {
        }
    });
}


    private void setDetailsFields(ArrayList<AdsContainments> body) {
        adsTitle.setText(""+body.get(0).getAds_title());
        Log.e("ad_id",""+ad_id);
        if(body.get(0).getImage()!=null&&!body.get(0).getImage().isEmpty()) {
            Picasso.with(this).load(body.get(0).getImage()).into(adImage);
        }
        rentalAmount.setText(""+body.get(0).getRental_amount());
        rentalOption.setText(""+body.get(0).getRental_option());
        securityDeposit.setText(""+body.get(0).getSecurity_deposit());
        description.setText(""+body.get(0).getDescription());
        condition.setText(""+body.get(0).getCondition());
        quantity.setText(""+body.get(0).getQuantity());
        availability.setText(""+body.get(0).getAvailability());
        name.setText(""+body.get(0).getSeller_name());
        phone.setText(""+body.get(0).getSeller_phone());
        if(!(body.get(0).getSeller_email()==null))email.setText(""+body.get(0).getSeller_email());
        address.setText(""+body.get(0).getSeller_city()+", "+body.get(0).getSeller_district()+", "+body.get(0).getSeller_locality());
    }

    private void callSeller(String seller_phno) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+seller_phno));
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_seller :
                if(phone.getText()!=null&&!phone.getText().toString().isEmpty())callSeller(phone.getText().toString());
                else Toast.makeText(this,"No Phone Number Available",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mail_seller :
                if(email.getText()!=null&&!email.getText().toString().isEmpty())mailSeller(email.getText().toString());
                else Toast.makeText(this,"No Email Available",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
