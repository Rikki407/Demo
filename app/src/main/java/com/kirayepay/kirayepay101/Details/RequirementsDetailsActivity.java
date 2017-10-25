package com.kirayepay.kirayepay101.Details;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kirayepay.kirayepay101.R;


public class RequirementsDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    int req_id;
    TextView reqTitle,description,category,name,phone,email,address,from,till;
    ImageView callImage,mailImage;

    String reqm_title,reqm_from,reqm_till,req_desc,req_cat,user_name,user_email,user_locality,user_city,user_district,user_state;
    long user_phone;
    int user_pincode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements_details);
        req_id = getIntent().getIntExtra("req_id",-1);
        reqTitle = (TextView) findViewById(R.id.reqmnt_title);
        description = (TextView) findViewById(R.id.reqmnt_description);
        category = (TextView) findViewById(R.id.reqmnt_category);
        name = (TextView) findViewById(R.id.reqmnt_name);
        phone = (TextView) findViewById(R.id.reqmnt_phone);
        email = (TextView) findViewById(R.id.reqmnt_email);
        address = (TextView) findViewById(R.id.reqmnt_address);
        from = (TextView) findViewById(R.id.reqmnt_from);
        till = (TextView) findViewById(R.id.reqmnt_till);
        callImage = (ImageView) findViewById(R.id.reqmnt_call_seller);
        mailImage = (ImageView) findViewById(R.id.reqmnt_mail_seller);
        callImage.setOnClickListener(this);
        mailImage.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_pincode = extras.getInt("user_pincode");
            user_phone = extras.getLong("user_phone");
            user_email = extras.getString("user_email");
            reqm_title = extras.getString("req_title");
            req_desc = extras.getString("req_desc");
            reqm_from = extras.getString("req_from");
            reqm_till = extras.getString("req_till");
            req_cat = extras.getString("req_cat");
            user_name = extras.getString("user_name");
            user_locality = extras.getString("user_locality");
            user_city = extras.getString("user_city");
            user_district = extras.getString("user_district");
            user_state = extras.getString("user_state");
            setReqDetailsFields();
        }

    }
    private void setReqDetailsFields() {
        reqTitle.setText(""+reqm_title);
        description.setText(""+req_desc);
        category.setText(""+req_cat);
        from.setText(""+reqm_from);
        till.setText(""+reqm_till);

        name.setText((user_name==null) ? "no name provided" : ""+user_name);
        phone.setText((user_phone==0)?  "no number provided": ""+user_phone);
        email.setText((user_email==null) ? "no email provided": ""+user_email);
        user_city = (user_city==null) ? "" : user_city + ", ";
        user_district = (user_district==null) ? "" :user_district+ ", ";
        user_locality = (user_locality==null) ? "" :user_locality;
        address.setText(""+user_city + ""+user_district + ""+user_locality);
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

    @Override
    protected void onStart() {
        super.onStart();
        final int screen_width = getResources().getDisplayMetrics().widthPixels;
        final int screen_height = getResources().getDisplayMetrics().heightPixels;
        final int new_window_width = screen_width * 100 / 100;
        final int new_window_height = screen_height * 63/100;
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.width = Math.max(layout.width, new_window_width);
        layout.height = Math.max(layout.height, new_window_height);
        getWindow().setAttributes(layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.reqmnt_call_seller:
                if (user_phone!=0)
                    callSeller(phone.getText().toString());
                else Toast.makeText(this, "No Phone Number Available", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reqmnt_mail_seller:
                if (user_email!=null)
                    mailSeller(email.getText().toString());
                else Toast.makeText(this, "No Email Provided", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
