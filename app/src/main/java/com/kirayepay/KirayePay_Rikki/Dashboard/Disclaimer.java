package com.kirayepay.KirayePay_Rikki.Dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kirayepay.KirayePay_Rikki.R;

/**
 * Created by rikki on 10/7/17.
 */

public class Disclaimer extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_disclaimer);
        Toolbar dis_toolbar = (Toolbar) findViewById(R.id.dis_toolbar);
        dis_toolbar.setTitle("Disclaimer");
        dis_toolbar.setNavigationIcon(R.drawable.back_button);
        setSupportActionBar(dis_toolbar);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
