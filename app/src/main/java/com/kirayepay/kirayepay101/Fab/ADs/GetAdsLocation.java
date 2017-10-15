package com.kirayepay.kirayepay101.Fab.ADs;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.kirayepay.kirayepay101.Network.ApiClient;
import com.kirayepay.kirayepay101.Network.ApiInterface;
import com.kirayepay.kirayepay101.Network.Responses.PostContainments;
import com.kirayepay.kirayepay101.R;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rikki on 10/4/17.
 */

public class GetAdsLocation extends AppCompatActivity
{
    private String main_img_uri, other_img_uri_1, other_img_uri_2, other_img_uri_3, other_img_uri_4;
    private TextView post_this_ad;
    MultipartBody.Part fileToUpload;
    RequestBody filename;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_location);

        Bundle bundle = getIntent().getExtras();
        main_img_uri = bundle.getString("main_uri");
        other_img_uri_1 = bundle.getString("other_uri_1");
        other_img_uri_2 = bundle.getString("other_uri_2");
        other_img_uri_3 = bundle.getString("other_uri_3");
        other_img_uri_4 = bundle.getString("other_uri_4");

        Log.e("show_uri_loc"," "+main_img_uri);

        createFile(Uri.parse(main_img_uri));

        post_this_ad = (TextView) findViewById(R.id.post_this_ad);
        post_this_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postTheAd();
            }
        });
    }

    private void postTheAd() {

        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), "Tabla111");
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "A tabla for rent");
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), "15");
        RequestBody availability = RequestBody.create(MediaType.parse("text/plain"), "yes");
        RequestBody condition = RequestBody.create(MediaType.parse("text/plain"), "new");
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody rental_option = RequestBody.create(MediaType.parse("text/plain"), "Daily");
        RequestBody locality = RequestBody.create(MediaType.parse("text/plain"), "Delhi");
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), "Delhi");
        RequestBody pincode = RequestBody.create(MediaType.parse("text/plain"), "110075");
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), "Delhi");
        RequestBody district = RequestBody.create(MediaType.parse("text/plain"), "Dwarka");
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), "9958702304");

        ApiInterface apiInterface = ApiClient.getApiInterface();

        Log.e("itenom", "here" );

        Call<PostContainments> postAdCall = apiInterface.postAds(title, description, category, availability, condition, quantity, rental_option, locality, city, pincode, state, district, phone, fileToUpload, filename);
        postAdCall.enqueue(new Callback<PostContainments>() {

            @Override
            public void onResponse(Call<PostContainments> call, Response<PostContainments> response) {
                if (response.isSuccessful()) {
                    Log.e("posted", "kmlkm" + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<PostContainments> call, Throwable t) {
                Log.e("cannot_post", "kkk" + t.getCause() + t.getMessage());
                t.printStackTrace();
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // In order to not be too narrow, set the window size based on the screen resolution:
        final int screen_width = getResources().getDisplayMetrics().widthPixels;
        final int screen_height = getResources().getDisplayMetrics().heightPixels;
        final int new_window_width = screen_width * 100 / 100;
        final int new_window_height = screen_height * 72/100;
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.width = Math.max(layout.width, new_window_width);
        layout.height = Math.max(layout.height, new_window_height);
        getWindow().setAttributes(layout);
    }

    private void createFile(Uri uri) {
        Log.e("kbkbnk",""+uri);
        String filePath = getRealPathFromURIPath(uri,GetAdsLocation.this);
        File file =new File(filePath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
    }

    private String getRealPathFromURIPath(Uri contentURI, AppCompatActivity activity) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
