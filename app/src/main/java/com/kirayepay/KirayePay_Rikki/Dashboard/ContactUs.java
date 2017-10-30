package com.kirayepay.KirayePay_Rikki.Dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kirayepay.KirayePay_Rikki.R;

/**
 * Created by rikki on 10/2/17.
 */

public class ContactUs extends AppCompatActivity implements View.OnClickListener {
    ImageView call_image,mail_image;
    Toolbar cu_toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_contact_us);
        cu_toolbar = (Toolbar) findViewById(R.id.cu_toolbar);
        cu_toolbar.setTitle("Contact Us");
        cu_toolbar.setNavigationIcon(R.drawable.back_button);
        setSupportActionBar(cu_toolbar);
        call_image = (ImageView) findViewById(R.id.call_kp);
        mail_image = (ImageView) findViewById(R.id.mail_kp);
        call_image.setOnClickListener(this);
        mail_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_kp :
                callSeller("8376922900");
                break;
            case R.id.mail_kp :
                mailSeller("care@kirayepay.com");
                break;
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
