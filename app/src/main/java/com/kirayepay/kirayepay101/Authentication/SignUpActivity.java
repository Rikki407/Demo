package com.kirayepay.kirayepay101.Authentication;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.kirayepay.kirayepay101.R;

/**
 * Created by rikki on 8/2/17.
 */

public class SignUpActivity extends AppCompatActivity
{
    TextView open_sign_in;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        open_sign_in = (TextView) findViewById(R.id.open_sign_in_activity);
        open_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,SigninActivity.class);
                startActivity(intent);
            }
        });
        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        GoogleAuth googleAuth = new GoogleAuth();
//        ft.replace(R.id.google_signup_button_container,googleAuth);
//        FacebookAuth facebookAuth = new FacebookAuth();
//        ft.replace(R.id.fb_signup_button_container,facebookAuth);
        EmailSignUpFragment emailSignUpFragment = new EmailSignUpFragment();
        ft.replace(R.id.email_signup_container, emailSignUpFragment).commit();
    }
}
