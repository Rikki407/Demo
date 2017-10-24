package com.kirayepay.kirayepay101.Dashboard.User_Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.PostContainments;
import com.kirayepay.kirayepay101.Network.Responses.SellerProfileResponse.ProfileContainment;
import com.kirayepay.kirayepay101.Network.Responses.SellerProfileResponse.SellerContainments;
import com.kirayepay.kirayepay101.R;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rikki on 10/7/17.
 */

public class UserProfile extends AppCompatActivity
{
    String user_id,user_email;
    ImageView go_back;
    TextView up_email,up_save;
    Context mContext = this;
    EditText up_name,up_phone,up_city,up_locality,up_district,up_state,up_pincode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_user_profile);
        go_back = (ImageView) findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfile.super.onBackPressed();
            }
        });
        SharedPreferences prefs = getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE);
        user_id = prefs.getString(Acquire.USER_ID,null);
        user_email = prefs.getString(Acquire.USER_EMAIL,null);
        up_email = (TextView) findViewById(R.id.up_email);
        up_email.setText(""+user_email);
        up_save = (TextView) findViewById(R.id.up_save);
        up_name = (EditText) findViewById(R.id.up_name);
        up_phone = (EditText) findViewById(R.id.up_phone);
        up_city = (EditText) findViewById(R.id.up_city);
        up_locality = (EditText) findViewById(R.id.up_locality);
        up_district = (EditText) findViewById(R.id.up_district);
        up_state = (EditText) findViewById(R.id.up_state);
        up_pincode = (EditText) findViewById(R.id.up_pincode);

        up_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

        fetchUserInfo();
    }

    private void updateUserProfile()
    {
        int pin_code = Integer.parseInt(up_pincode.getText().toString());

        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<PostContainments> updateProfileCall = apiInterface.userProfileUpdate(user_id,up_name.getText().toString(),
                up_phone.getText().toString(),up_locality.getText().toString(),up_city.getText().toString(),"India",
                up_state.getText().toString(),up_district.getText().toString(),pin_code);

        updateProfileCall.enqueue(new Callback<PostContainments>() {
            @Override
            public void onResponse(Call<PostContainments> call, Response<PostContainments> response) {
                if(response.isSuccessful()) {
                    Log.e("update_profile",response.body().getMessage());
                   Toast.makeText(mContext,"Profile Updated",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE).edit();
                    editor.putString(Acquire.USER_NAME,up_name.getText().toString());
                    editor.apply();
                    storeInfoInSharedPreference(up_phone.getText().toString(),up_city.getText().toString(),
                            up_district.getText().toString(),up_locality.getText().toString(),up_state.getText().toString(),
                            up_pincode.getText().toString());


                }
            }
            @Override
            public void onFailure(Call<PostContainments> call, Throwable t) {
                Toast.makeText(mContext,"Connection Error!!!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchUserInfo()
    {
        ApiInterface apiInterface = ApiClient.getApiInterface();
        Call<ProfileContainment> user_info_call = apiInterface.getSellerInfo(user_id,Acquire.API_KEY);
        user_info_call.enqueue(new Callback<ProfileContainment>() {
            @Override
            public void onResponse(Call<ProfileContainment> call, Response<ProfileContainment> response) {
                Log.e("seller_profile",""+response.body().getSeller().get(0).getName());
                SellerContainments seller = response.body().getSeller().get(0);
                up_name.setText(""+seller.getName());
                up_phone.setText(""+seller.getPhone());
                if(seller.getCity()!=null)up_city.setText(""+seller.getCity());
                if(seller.getDistrict()!=null)up_district.setText(""+seller.getDistrict());
                if(seller.getLocality()!=null)up_locality.setText(""+seller.getLocality());
                if(seller.getState()!=null)up_state.setText(""+seller.getState());
                if(seller.getPincode()!=0)up_pincode.setText(""+seller.getPincode());
                storeInfoInSharedPreference(""+seller.getPhone(),""+seller.getCity(),
                        ""+seller.getDistrict(),""+seller.getLocality(),""+seller.getState(),""+seller.getPincode());

            }

            @Override
            public void onFailure(Call<ProfileContainment> call, Throwable t) {

            }
        });
    }

    private void  storeInfoInSharedPreference( String phone, String city, String district, String locality, String state, String pincode) {
        SharedPreferences.Editor editor = getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE).edit();
        editor.putString(Acquire.LOCALITY,locality);
        editor.putString(Acquire.CITY,city);
        editor.putString(Acquire.STATE,state);
        editor.putString(Acquire.DISTRICT,district);
        editor.putString(Acquire.PHONE,phone);
        editor.putString(Acquire.PINCODE,pincode);
        editor.apply();

    }
}
