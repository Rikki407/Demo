package com.kirayepay.kirayepay101.Authentication;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kirayepay.kirayepay101.R;

/**
 * Created by rikki on 8/2/17.
 */

public class AuthActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        SigninFragment signinFragment = new SigninFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.auht_container,signinFragment);
    }
}
