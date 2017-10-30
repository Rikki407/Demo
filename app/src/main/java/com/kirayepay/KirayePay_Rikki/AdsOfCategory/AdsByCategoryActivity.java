package com.kirayepay.KirayePay_Rikki.AdsOfCategory;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kirayepay.KirayePay_Rikki.RikkiClasses.Acquire;
import com.kirayepay.KirayePay_Rikki.R;

import java.util.Map;
import java.util.Set;

public class AdsByCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    int category_id,parent_category_id;
    int rent_seek_progress,deposit_seek_progress;
    View bottomSheet;
    SeekBar price_seekbar,security_seekbar;
    TextView price_textview,security_textview;
    TextView ro_daily,ro_weekly,ro_monthly,ro_occasional;
    RadioGroup conditions;
    RadioButton used_button,new_button,both_button;
    TextView apply_filter;
    AdsByCategoryFragment abc;
    BottomSheetBehavior behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_by_category);

        Acquire. RENTAL_OPTIONS.put(R.id.ro_daily,true);
        Acquire.RENTAL_OPTIONS.put(R.id.ro_weekly,true);
        Acquire.RENTAL_OPTIONS.put(R.id.ro_monthly,true);
        Acquire.RENTAL_OPTIONS.put(R.id.ro_occasional,true);

        category_id = getIntent().getExtras().getInt("CategoryId");
        parent_category_id = getIntent().getExtras().getInt("ParentId");
        abc = new AdsByCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("CategoryId",category_id);
        bundle.putInt("ParentId",parent_category_id);

        price_seekbar = (SeekBar) findViewById(R.id.price_seek_bar);
        security_seekbar = (SeekBar) findViewById(R.id.security_seek_bar);
        price_textview = (TextView) findViewById(R.id.max_price);
        security_textview = (TextView) findViewById(R.id.max_security);
        apply_filter = (TextView) findViewById(R.id.apply_filter);
        apply_filter.setOnClickListener(this);

        ro_daily = (TextView) findViewById(R.id.ro_daily);
        ro_daily.setOnClickListener(this);
        ro_weekly = (TextView) findViewById(R.id.ro_weekly);
        ro_weekly.setOnClickListener(this);
        ro_monthly = (TextView) findViewById(R.id.ro_monthly);
        ro_monthly.setOnClickListener(this);
        ro_occasional = (TextView) findViewById(R.id.ro_occasional);
        ro_occasional.setOnClickListener(this);

        conditions = (RadioGroup) findViewById(R.id.conditons);
        used_button = (RadioButton) findViewById(R.id.used_button);
        new_button = (RadioButton) findViewById(R.id.new_button);
        both_button = (RadioButton) findViewById(R.id.both_button);
        used_button.setOnClickListener(this);
        new_button.setOnClickListener(this);
        both_button.setOnClickListener(this);

        CoordinatorLayout main_content = (CoordinatorLayout) findViewById(R.id.main_content);
        bottomSheet = main_content.findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    WindowManager.LayoutParams windowManager = getWindow().getAttributes();
                    windowManager.dimAmount = 0.75f;
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    price_seekbar.setMax(Acquire.PRICE_SEEKBAR_MAX);
                    price_seekbar.setProgress(Acquire.PRICE_SEEKBAR_CURR);
                    security_seekbar.setMax(Acquire.SECURITY_SEEKBAR_MAX);
                    security_seekbar.setProgress(Acquire.SECURITY_SEEKBAR_CURR);

                    if(Acquire.PRICE_SEEKBAR_CURR>-1) price_textview.setText("₹"+Acquire.PRICE_SEEKBAR_CURR);
                    else price_textview.setText("₹ 0");

                    if(Acquire.SECURITY_SEEKBAR_CURR>-1) security_textview.setText("₹"+Acquire.SECURITY_SEEKBAR_CURR);
                    else security_textview.setText("₹ 0");


                    rent_seek_progress = Acquire.PRICE_SEEKBAR_CURR;
                    deposit_seek_progress = Acquire.SECURITY_SEEKBAR_CURR;
                    price_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            price_textview.setText("₹"+progress);
                            rent_seek_progress = progress;
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                    security_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            security_textview.setText("₹"+progress);
                            deposit_seek_progress = progress;
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        behavior.setPeekHeight(0);
        abc.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ads_by_cat_container, abc);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ads_by_cat_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.list_change_view);
        if(Acquire.IS_LISTVIEW) menuItem.setIcon(R.drawable.grid_view);
        else menuItem.setIcon(R.drawable.list_view);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.ads_filter)
        {
            openBottomSheet();
        }

        else if(item.getItemId()==R.id.list_change_view)
        {
            Acquire.IS_LISTVIEW = !Acquire.IS_LISTVIEW;
            for (int i = 0; i < AdsByCategoryFragment.adsListFragments.size(); i++) {
                if (AdsByCategoryFragment.adsListFragments.get(i).gridLayoutManager != null) {
                    if (AdsByCategoryFragment.adsListFragments.get(i).gridLayoutManager.getSpanCount() == 1) {
                        item.setIcon(R.drawable.list_view);
                        AdsByCategoryFragment.adsListFragments.get(i).gridLayoutManager.setSpanCount(2);
                    } else {
                        item.setIcon(R.drawable.grid_view);
                        AdsByCategoryFragment.adsListFragments.get(i).gridLayoutManager.setSpanCount(1);
                    }
                    AdsByCategoryFragment.adsListFragments.get(i).adsAdapter.notifyItemRangeChanged(0, AdsByCategoryFragment.adsListFragments.get(i).adsAdapter.getItemCount());
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void openBottomSheet()
    {
        if(behavior.getState()==BottomSheetBehavior.STATE_EXPANDED) {

            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        else {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ro_daily :
                selectRentalOption(v);
                break;
            case R.id.ro_weekly:
                selectRentalOption(v);
                break;
            case R.id.ro_monthly:
                selectRentalOption(v);
                break;
            case R.id.new_button:
            case R.id.used_button:
            case R.id.both_button:
                selectCondition(v);
                break;
            case R.id.ro_occasional:
                selectRentalOption(v);
                break;
            case R.id.apply_filter:
                applyFilterBehavior(v);
                break;

        }

    }

    private void selectCondition(View v) {
        RadioButton rb = (RadioButton) v;
        Acquire.CONTDITITON = (String) rb.getText();
    }

    private void applyFilterBehavior(View v) {


        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Acquire.PRICE_SEEKBAR_CURR = rent_seek_progress;
        Acquire.SECURITY_SEEKBAR_CURR = deposit_seek_progress;
        abc.adsListFragments.get(abc.selected_cat_pos).changeAccToFilter();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                Rect outRect = new Rect();
                bottomSheet.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    return true;
                }

            }
        }
        return super.dispatchTouchEvent(event);
    }
    private void selectRentalOption(View v)
    {
        int i=0;
        Set<Map.Entry<Integer, Boolean>> entries = Acquire.RENTAL_OPTIONS.entrySet();
        for(Map.Entry<Integer,Boolean> entry: entries)
        {
            if(entry.getValue())
                i++;
        }
        if(i==1&&Acquire.RENTAL_OPTIONS.get(v.getId()))
        {
            Toast.makeText(this,"One Option is required", Toast.LENGTH_SHORT).show();
            return;
        }

        TextView change_text = (TextView) findViewById(v.getId());
        if(Acquire.RENTAL_OPTIONS.get(v.getId()))
        {
            ((GradientDrawable) v.getBackground()).setColor(Color.parseColor("#FFFFFF"));
            change_text.setTextColor(Color.parseColor("#145ea7"));
        }
        else
        {
            ((GradientDrawable) v.getBackground()).setColor(Color.parseColor("#145ea7"));
            change_text.setTextColor(Color.parseColor("#FFFFFF"));
        }
        Acquire.RENTAL_OPTIONS.put(v.getId(),!Acquire.RENTAL_OPTIONS.get(v.getId()));

    }

}
