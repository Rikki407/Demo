package com.kirayepay.kirayepay101;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.kirayepay.kirayepay101.Authentication.SigninActivity;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;


/**
 * Created by rikki on 8/27/17.
 */

public class SplashScreen extends AppCompatActivity
{

    ImageView logo_image,title_image;
    private boolean open_auth_activity = false;
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        mContext = this;
        SharedPreferences preferences = getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE);
        if(preferences.getInt(Acquire.USER_AUTH_METHOD,-1)==-1)                     //checking if user has already signed in or not
        {
            open_auth_activity = true;
        }
        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2200);  //Delay of 4.0 seconds
                } catch (Exception e) {
                } finally {
                    // opening main activity if user has already signed in or SigninActivity in the other case
                    Intent i = (open_auth_activity)?new Intent(mContext, SigninActivity.class):new Intent(mContext,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        };
        welcomeThread.start();
        logo_image = (ImageView) findViewById(R.id.kp_logo);
        title_image = (ImageView) findViewById(R.id.kp_title);
        logo_image.setImageResource(R.drawable.ic_kp);
        title_image.setImageResource(R.drawable.ic_kp_title);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.image_fadein);
        title_image.startAnimation(myFadeInAnimation);
        logo_image.clearAnimation();
        TranslateAnimation transAnim = new TranslateAnimation(0, 0, 0,
                getDisplayHeight()/2.6f);
        transAnim.setStartOffset(0);
        transAnim.setDuration(2200);
        transAnim.setFillAfter(true);
        transAnim.setInterpolator(new BounceInterpolator());
        transAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                Log.e("yo", "Starting button dropdown animation");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e("yo",
                        "Ending button dropdown animation. Clearing animation and setting layout");
//                imageView.clearAnimation();
//                final int left = imageView.getLeft();
//                final int top = imageView.getTop();
//                final int right = imageView.getRight();
//                final int bottom = imageView.getBottom();
//                imageView.layout(left, top, right, bottom);
            }
        });
        logo_image.startAnimation(transAnim);

    }


    private int getDisplayHeight()
    {
        return this.getResources().getDisplayMetrics().heightPixels;
    }


}

