package com.kirayepay.KirayePay_Rikki.Dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kirayepay.KirayePay_Rikki.R;

public class TermsAndCond extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_terms_and_cond);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tc_toolbar);
        toolbar.setTitle("Terms & Conditions");
        toolbar.setNavigationIcon(R.drawable.back_button);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
