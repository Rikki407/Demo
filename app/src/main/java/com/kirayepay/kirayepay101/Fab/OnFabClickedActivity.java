package com.kirayepay.kirayepay101.Fab;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.kirayepay.kirayepay101.R;

/**
 * Created by rikki on 8/14/17.
 */

public class OnFabClickedActivity extends AppCompatActivity implements View.OnClickListener
{
    ImageView post_ads,post_requirments;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_fab_clicked);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        post_ads = (ImageView) findViewById(R.id.post_ads);
        post_requirments = (ImageView) findViewById(R.id.post_requirements);
        post_ads.setOnClickListener(this);
        post_requirments.setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        // In order to not be too narrow, set the window size based on the screen resolution:
        final int screen_width = getResources().getDisplayMetrics().widthPixels;
        final int screen_height = getResources().getDisplayMetrics().heightPixels;
        final int new_window_width = screen_width * 100 / 100;
        final int new_window_height = screen_height * 60/100;
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.width = Math.max(layout.width, new_window_width);
        layout.height = Math.max(layout.height, new_window_height);
        getWindow().setAttributes(layout);
    }

    @Override
    public void onClick(View v)
    {
      switch (v.getId())
      {
          case R.id.post_ads :
              Intent intent = new Intent(OnFabClickedActivity.this,PostAdsDetails.class);
              startActivity(intent);
              this.overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
              break;
          case R.id.post_requirements :
              break;
      }
    }
}
