package com.kirayepay.kirayepay101.Dashboard.User_Requirements;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kirayepay.kirayepay101.R;

/**
 * Created by rikki on 9/25/17.
 */

public class UserRequirements extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_user_requirements);
        UserReqFragment fragment = new UserReqFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.user_requirements_holder,fragment).commit();
    }

}
