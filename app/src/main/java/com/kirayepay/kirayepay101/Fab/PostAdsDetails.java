package com.kirayepay.kirayepay101.Fab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kirayepay.kirayepay101.Network.Responses.PostAdsContainments;
import com.kirayepay.kirayepay101.R;

/**
 * Created by rikki on 8/12/17.
 */

public class PostAdsDetails extends AppCompatActivity
{
    TextView category,title,description,manufacture,availability,condition,quantity,rental_option,rental_amount,security_deposit,site_url;
    Button goto_ads_location;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ads_details);
        goto_ads_location = (Button) findViewById(R.id.go_to_adslocation);
        goto_ads_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostAdsDetails.this,PostAdsLocation.class);
                startActivity(intent);
                PostAdsDetails.this.overridePendingTransition(R.anim.left_to_right,R.anim.left_to_right);
            }
        });
        category = (TextView) findViewById(R.id.post_ads_category);
        title = (TextView) findViewById(R.id.post_ads_title);
        description = (TextView) findViewById(R.id.post_ads_description);
        manufacture = (TextView) findViewById(R.id.post_ads_manufacture);
        availability = (TextView) findViewById(R.id.post_ads_availability);
        condition = (TextView) findViewById(R.id.post_ads_condition);
        quantity = (TextView) findViewById(R.id.post_ads_quantity);
        rental_option = (TextView) findViewById(R.id.post_ads_rental_option);
        rental_amount = (TextView) findViewById(R.id.post_ads_rental_amount);
        security_deposit = (TextView) findViewById(R.id.post_ads_security_deposit);
    }


}
