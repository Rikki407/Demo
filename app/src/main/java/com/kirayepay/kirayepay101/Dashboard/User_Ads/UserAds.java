package com.kirayepay.kirayepay101.Dashboard.User_Ads;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.kirayepay.kirayepay101.R;


/**
 * Created by rikki on 9/25/17.
 */

public class UserAds extends AppCompatActivity
{
    UserAdsFragment fragment;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_user_ads);
        mContext = this;
        fragment = new UserAdsFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.user_ads_holder,fragment).commit();
    }


}
