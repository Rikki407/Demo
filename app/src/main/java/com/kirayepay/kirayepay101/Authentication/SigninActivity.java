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

public class SigninActivity extends AppCompatActivity {
    TextView open_sign_up;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        open_sign_up = (TextView) findViewById(R.id.open_sign_up_activity);
        open_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        GoogleAuth googleAuth = new GoogleAuth();
        ft.replace(R.id.google_signin_button_container,googleAuth);
        FacebookAuth facebookAuth = new FacebookAuth();
        ft.replace(R.id.fb_signin_button_container,facebookAuth);
        EmailSignInFragment emailSignInFragment = new EmailSignInFragment();
        ft.replace(R.id.email_signin_container, emailSignInFragment).commit();
    }



}
