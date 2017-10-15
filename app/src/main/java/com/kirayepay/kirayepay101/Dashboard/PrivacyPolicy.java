package com.kirayepay.kirayepay101.Dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kirayepay.kirayepay101.R;

public class PrivacyPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_privacy_policy);
        Toolbar pp_toolbar = (Toolbar) findViewById(R.id.pp_toolbar);
        pp_toolbar.setTitle("Privacy Policy");
        pp_toolbar.setNavigationIcon(R.drawable.back_button);
        setSupportActionBar(pp_toolbar);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
