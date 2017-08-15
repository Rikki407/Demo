package com.kirayepay.kirayepay101.Fab;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.PostAdsContainments;
import com.kirayepay.kirayepay101.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rikki on 8/12/17.
 */

public class PostAdsLocation extends AppCompatActivity implements View.OnClickListener
{

    TextView locality,city,state,destrict,pincode,phone;
    Button post_ad_button,get_image_button;
    private static final String TAG = PostAdsLocation.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "Path_to_your_server";
    ImageView priview;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ads_location);
        priview = (ImageView) findViewById(R.id.get_preview);
        locality = (TextView) findViewById(R.id.post_ads_locality);
        city = (TextView) findViewById(R.id.post_ads_city);
        state = (TextView) findViewById(R.id.post_ads_state);
        destrict = (TextView) findViewById(R.id.post_ads_destrict);
        pincode = (TextView) findViewById(R.id.post_ads_pincode);
        phone = (TextView) findViewById(R.id.post_ads_phone);
        post_ad_button = (Button) findViewById(R.id.post_ads_button);
        get_image_button = (Button) findViewById(R.id.get_image);
        get_image_button.setOnClickListener(this);
        post_ad_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.post_ads_button :
                postTheAd();
                break;
            case R.id.get_image :
                getImageFromGallery();
                break;
        }
    }

    private void postTheAd()
    {
//
//        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), "Tabla000");
//        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "A tabla for rent");
//        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), "15");
//        RequestBody availability= RequestBody.create(MediaType.parse("text/plain"), "yes");
//        RequestBody condition = RequestBody.create(MediaType.parse("text/plain"), "new");
//        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), "1");
//        RequestBody rental_option = RequestBody.create(MediaType.parse("text/plain"), "Daily");
//        RequestBody locality = RequestBody.create(MediaType.parse("text/plain"), "Delhi");
//        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), "Delhi");
//        RequestBody pincode = RequestBody.create(MediaType.parse("text/plain"), "110075");
//        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), "Delhi");
//        RequestBody district = RequestBody.create(MediaType.parse("text/plain"), "Dwarka");
//        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), "9958702304");
//        ApiInterface apiInterface = ApiClient.getApiInterface();
//        Call<PostAdsContainments> postAdCall = apiInterface.postAds(title,description,category,availability,condition,quantity,rental_option,locality,city,pincode,state,district,phone);
//        Log.e("posted","blblb");
//        postAdCall.enqueue(new Callback<PostAdsContainments>() {
//            @Override
//            public void onResponse(Call<PostAdsContainments> call, Response<PostAdsContainments> response) {
//                if(response.isSuccessful())
//                {
//                    Log.e("posted",""+response.body().getMessage());
//                }
//            }
//            @Override
//            public void onFailure(Call<PostAdsContainments> call, Throwable t) {
//                Log.e("cannot_post",""+t.getCause());
//            }
//
//        });
    }

    public void getImageFromGallery()
    {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), "Tabla000");
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "A tabla for rent");
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), "15");
        RequestBody availability= RequestBody.create(MediaType.parse("text/plain"), "yes");
        RequestBody condition = RequestBody.create(MediaType.parse("text/plain"), "new");
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody rental_option = RequestBody.create(MediaType.parse("text/plain"), "Daily");
        RequestBody locality = RequestBody.create(MediaType.parse("text/plain"), "Delhi");
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), "Delhi");
        RequestBody pincode = RequestBody.create(MediaType.parse("text/plain"), "110075");
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), "Delhi");
        RequestBody district = RequestBody.create(MediaType.parse("text/plain"), "Dwarka");
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), "9958702304");


        Uri uri = data.getData();

                String filePath = getRealPathFromURIPath(uri, PostAdsLocation.this);

                File file = new File(filePath);
                Log.e("Filepath ","" + filePath);

                priview.setImageURI(uri);

                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());



//
        Log.e("Filename",""+filename+" "+file.getName());

        ApiInterface apiInterface = ApiClient.getApiInterface();

        Call<PostAdsContainments> postAdCall = apiInterface.postAds(title,description,category,availability,condition,quantity,rental_option,locality,city,pincode,state,district,phone,fileToUpload,filename);
        Log.e("itenom","here");
        postAdCall.enqueue(new Callback<PostAdsContainments>() {
            @Override
            public void onResponse(Call<PostAdsContainments> call, Response<PostAdsContainments> response) {
                if(response.isSuccessful())
                {
                    Log.e("posted","kmlkm"+response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<PostAdsContainments> call, Throwable t) {
                Log.e("cannot_post","kkk"+t.getCause());
                t.printStackTrace();
            }

        });
    }
    private String getRealPathFromURIPath(Uri contentURI, AppCompatActivity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}
