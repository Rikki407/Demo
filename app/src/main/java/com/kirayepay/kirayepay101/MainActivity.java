package com.kirayepay.kirayepay101;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kirayepay.kirayepay101.BottomNavFragments.Home.HomeFragment;
import com.kirayepay.kirayepay101.BottomNavFragments.Menu.AllCategoriesFragment;
import com.kirayepay.kirayepay101.BottomNavFragments.Search.SearchFragment;
import com.kirayepay.kirayepay101.Fab.OnFabClickedActivity;
import com.kirayepay.kirayepay101.MyClasses.Acquire;
import com.kirayepay.kirayepay101.MyClasses.CategoryHierarchy;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.CategoriesContainments;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private HashMap<Integer,ArrayList<CategoriesContainments>> subcategories;
    private Context mContext;
    private FloatingActionButton postButton;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.bottom_navigation_home:
                    fragment = HomeFragment.newInstance();
                    break;
                case R.id.bottom_navigation_search:
                    fragment = SearchFragment.newInstance();
                    break;
                case R.id.bottom_navigation_menu:
                    fragment = AllCategoriesFragment.newInstance();
                    break;
            }
            if(fragment!=null)
            {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_contents,fragment);
                fragmentTransaction.commit();
            }
            return true;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        subcategories = new HashMap<>();
        postButton = (FloatingActionButton) findViewById(R.id.float_post_action_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OnFabClickedActivity.class);
                startActivity(intent);

            }
        });

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, filter);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(0);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_contents,HomeFragment.newInstance());
        fragmentTransaction.commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
//                Toast.makeText(mContext,"No Internet Connection",Toast.LENGTH_LONG).show();
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
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.of_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
