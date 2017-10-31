package com.kirayepay.KirayePay_Rikki.Dashboard.User_Requirements;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kirayepay.KirayePay_Rikki.R;
import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;

/**
 * Created by rikki on 9/25/17.
 */

public class UserRequirements extends AppCompatActivity
{
    UserReqFragment fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_user_requirements);
        fragment = new UserReqFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        SharedPreferences prefs = getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE);

        Bundle bundle = new Bundle();
        bundle.putString("USER_ID",prefs.getString(Acquire.USER_ID,""));
        fragment.setArguments(bundle);
        ft.replace(R.id.user_requirements_holder,fragment).commit();
    }

}
