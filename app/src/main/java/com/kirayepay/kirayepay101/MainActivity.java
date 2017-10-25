package com.kirayepay.kirayepay101;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
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
import com.google.firebase.analytics.FirebaseAnalytics;
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

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    NavigationFragment navigationFragment;
    private boolean SearchViewOpen = false;
    private HashMap<Integer,ArrayList<CategoriesContainments>> subcategories;
    private Context mContext=this;
    private MaterialSearchView searchView;
    private String user_name,user_id,user_email;
    private int Auth_Method;
    private DrawerLayout mDrawerLayout;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAnalytics mFirebaseAnalytics;
    private TextView user_name_txtview,user_ads,user_requirements,user_profile,contact_us,privacy_policy,terms_and_cond,disclaimer,log_out_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        navigationFragment = new NavigationFragment();

        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        checkPermissionREAD_EXTERNAL_STORAGE(this);
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
        subcategories = new HashMap<>();
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
                SearchViewOpen = true;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.left_in,R.anim.left_out);
                fragmentTransaction.replace(R.id.main_container, new SearchFragment());
                fragmentTransaction.commit();
            }

            @Override
            public void onSearchViewClosed() {
                SearchViewOpen = false;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.right_in,R.anim.right_out);
                fragmentTransaction.replace(R.id.main_container, navigationFragment);
                fragmentTransaction.commit();
            }
        });
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, filter);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, navigationFragment);
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
    }

    public void onConnectionFound() {
        Acquire.NETWORK_CONNECTED=true;
        fetchAllCategories();
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
            LoginManager.getInstance().logOut();
        }

        else if(Auth_Method== Acquire.GOOGLE_AUTH)
        {
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
    public void onBackPressed() {
        if(SearchViewOpen)
        {
            searchView.closeSearch();
            return;
        }
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        super.onBackPressed();
    }
    public boolean checkPermissionREAD_EXTERNAL_STORAGE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context, android.Manifest.permission.READ_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }

                return false;
            } else {

                return true;
            }

        } else {
            return true;
        }
    }
    public void showDialog(final String msg, final Context context,
                           final String permission, final int myPermissionsRequestCamera) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                myPermissionsRequestCamera);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
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
