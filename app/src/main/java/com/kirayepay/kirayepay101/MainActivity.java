package com.kirayepay.kirayepay101;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.kirayepay.kirayepay101.Authentication.SigninActivity;
import com.kirayepay.kirayepay101.Dashboard.ContactUs;
import com.kirayepay.kirayepay101.Dashboard.Disclaimer;
import com.kirayepay.kirayepay101.Dashboard.PrivacyPolicy;
import com.kirayepay.kirayepay101.Dashboard.TermsAndCond;
import com.kirayepay.kirayepay101.Dashboard.User_Ads.UserAds;
import com.kirayepay.kirayepay101.Dashboard.User_Profile.UserProfile;
import com.kirayepay.kirayepay101.Dashboard.User_Requirements.UserRequirements;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;
import com.kirayepay.kirayepay101.RikkiClasses.CategoryHierarchy;
import com.kirayepay.kirayepay101.Navigation.NavigationFragment;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.CategoriesContainments;
import com.kirayepay.kirayepay101.Search.SearchFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private HashMap<Integer,ArrayList<CategoriesContainments>> subcategories;
    private Context mContext=this;
    private MaterialSearchView searchView;
    private String user_name,user_id,user_email;
    private int Auth_Method;
    private DrawerLayout mDrawerLayout;
    private GoogleApiClient mGoogleApiClient;
    private TextView user_name_txtview,user_ads,user_requirements,user_profile,contact_us,privacy_policy,terms_and_cond,disclaimer,log_out_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("KirayePay");
        toolbar.setNavigationIcon(R.drawable.bottom_nav_menu);
        setSupportActionBar(toolbar);
        log_out_button = (TextView) findViewById(R.id.log_out);
        log_out_button.setOnClickListener(this);
        user_ads = (TextView) findViewById(R.id.show_user_ads);
        user_ads.setOnClickListener(this);
        user_requirements = (TextView) findViewById(R.id.show_user_requirements);
        user_requirements.setOnClickListener(this);
        contact_us = (TextView) findViewById(R.id.contact_us);
        contact_us.setOnClickListener(this);
        privacy_policy = (TextView) findViewById(R.id.privacy_policy);
        privacy_policy.setOnClickListener(this);
        terms_and_cond = (TextView) findViewById(R.id.terms_and_cond);
        terms_and_cond.setOnClickListener(this);
        disclaimer = (TextView) findViewById(R.id.disclaimer);
        disclaimer.setOnClickListener(this);
        user_profile = (TextView) findViewById(R.id.show_user_profile);
        user_profile.setOnClickListener(this);
        user_name_txtview = (TextView) findViewById(R.id.user_name);


        SharedPreferences prefs = getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE);
        user_name = prefs.getString(Acquire.USER_NAME,"No Name");
        user_email = prefs.getString(Acquire.USER_EMAIL,"No Email");
        user_id = prefs.getString(Acquire.USER_ID,"No Id");
        Auth_Method = prefs.getInt(Acquire.USER_AUTH_METHOD,-1);
        user_name_txtview.setText(""+user_name);
        Log.e("user_details"," "+user_id+" "+user_email+" "+user_name+" "+Auth_Method);
        Toast.makeText(this," "+user_id+" "+user_email+" "+user_name+" "+Auth_Method,Toast.LENGTH_LONG).show();
        subcategories = new HashMap<>();
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Intent intent = new Intent("SEARCH_CALLING");
                intent.putExtra("SEARCH_TEXT", newText);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.left_in,R.anim.left_out);
                fragmentTransaction.replace(R.id.main_container, new SearchFragment());
                fragmentTransaction.commit();
            }

            @Override
            public void onSearchViewClosed() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.right_in,R.anim.right_out);
                fragmentTransaction.replace(R.id.main_container, new NavigationFragment());
                fragmentTransaction.commit();
            }
        });
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, filter);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, new NavigationFragment());
        fragmentTransaction.commit();
    }

    private void fetchAllCategories()
    {

        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<ArrayList<CategoriesContainments>> detailsCall = apiInterface.getAllCategories(Acquire.API_KEY);


        detailsCall.enqueue(new Callback<ArrayList<CategoriesContainments>>()
        {
            @Override
            public void onResponse(Call<ArrayList<CategoriesContainments>> call, Response<ArrayList<CategoriesContainments>> response)
            {
                if (response.isSuccessful())
                {
                    ArrayList<CategoriesContainments> categoryResponse = response.body();
                    for(CategoriesContainments cc : categoryResponse)
                    {
                        if(subcategories.get(cc.getParent_category())==null)
                        {
                            subcategories.put(cc.getParent_category(),new ArrayList<CategoriesContainments>());
                        }
                        subcategories.get(cc.getParent_category()).add(new CategoriesContainments(
                                cc.getCategory_id(),cc.getCategory_name(),cc.getCategory_image_url(),
                                cc.getParent_category(),cc.getIs_service(),cc.getCreated_at(),cc.getUpdated_at()
                        ));
                    }
                    CategoryHierarchy.setSubcategories(subcategories);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<CategoriesContainments>> call, Throwable t) {
               Toast.makeText(mContext,"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        });
    }
    BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

            if(!noConnectivity) {
                onConnectionFound();
            } else {
                onConnectionLost();
            }
        }
    };

    public void onConnectionLost() {
        Acquire.NETWORK_CONNECTED=false;
        Toast.makeText(this, "Connection lost", Toast.LENGTH_LONG).show();
    }

    public void onConnectionFound() {
        Acquire.NETWORK_CONNECTED=true;
        fetchAllCategories();
        Toast.makeText(this, "Connection found", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        if(Auth_Method== Acquire.GOOGLE_AUTH)
        {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    private void signOutTheUser()
    {
        if (Auth_Method == Acquire.FACEBOOK_AUTH) {
            Log.e("facebooklogout","yep");
            LoginManager.getInstance().logOut();
        }
        //09-06 15:42:18.353 3121-3121/com.kirayepay.kirayepay101 E/userdetails:  356 ramlamda@gmail.com Rishab 3


        else if(Auth_Method== Acquire.GOOGLE_AUTH)
        {
            Log.e("googlelogout","yep");
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            //....
                        }
                    });
        }
        gotoSignInActivity();
    }

    private void gotoSignInActivity() {
        setValuesToDefault();
        Intent intent = new Intent(mContext, SigninActivity.class);
        startActivity(intent);
    }

    private void setValuesToDefault()
    {
        SharedPreferences.Editor editor = getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE).edit();
        editor.putString(Acquire.USER_EMAIL,null);
        editor.putString(Acquire.USER_ID,null);
        editor.putString(Acquire.USER_NAME,null);
        editor.putInt(Acquire.USER_AUTH_METHOD,-1);
        editor.apply();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_out :
                signOutTheUser();
                break;
            case R.id.show_user_ads:
                Intent intent = new Intent(mContext, UserAds.class);
                startActivity(intent);
                break;
            case R.id.show_user_requirements:
                Intent intent1 = new Intent(mContext, UserRequirements.class);
                startActivity(intent1);
                break;
            case R.id.show_user_profile:
                Intent intent2 = new Intent(mContext, UserProfile.class);
                startActivity(intent2);
                break;
            case R.id.contact_us:
                Intent intent3 = new Intent(mContext, ContactUs.class);
                startActivity(intent3);
                break;
            case R.id.privacy_policy:
                Intent intent4 = new Intent(mContext, PrivacyPolicy.class);
                startActivity(intent4);
                break;
            case R.id.terms_and_cond:
                Intent intent5 = new Intent(mContext, TermsAndCond.class);
                startActivity(intent5);
                break;
            case R.id.disclaimer:
                Intent intent6 = new Intent(mContext, Disclaimer.class);
                startActivity(intent6);
                break;
        }
    }
}
