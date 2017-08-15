package com.kirayepay.kirayepay101.AdsOfCategory;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.kirayepay.kirayepay101.MyClasses.Acquire;
import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.AdsContainments;
import com.kirayepay.kirayepay101.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdsByCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<AdsContainments> adsListOfCategory;
    AdsByCategoryAdapter adsByCategoryAdapter;
    GridLayoutManager gridLayoutManager;
    int category_id,parent_category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_by_category);
        category_id = getIntent().getExtras().getInt("CategoryId");
        parent_category_id = getIntent().getExtras().getInt("ParentId");
        AdsByCategoryFragment abc = new AdsByCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("CategoryId",category_id);
        bundle.putInt("ParentId",parent_category_id);
        abc.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ads_by_cat_container, abc);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.of_ads, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if(id==R.id.list_to_grid)
//        {
//            if (gridLayoutManager.getSpanCount() == 1) {
//                    item.setIcon(R.drawable.list_view);
//                    gridLayoutManager.setSpanCount(3);
//                }
//                else {
//                    item.setIcon( R.drawable.grid_view);
//                    gridLayoutManager.setSpanCount(1);
//                }
//                adsByCategoryAdapter.notifyItemRangeChanged(0, adsByCategoryAdapter.getItemCount());
//
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
