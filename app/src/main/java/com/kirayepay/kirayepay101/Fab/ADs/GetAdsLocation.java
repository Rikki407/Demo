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

public class GetAdsLocation extends AppCompatActivity {
    private String m_img_uri_str, o_img_uri_str_1, o_img_uri_str_2, o_img_uri_str_3, o_img_uri_str_4;
    private TextView post_this_ad;
    MultipartBody.Part main_image=null;
    RequestBody filename;
    MultipartBody.Part other_image_1 = null, other_image_2 = null, other_image_3 = null, other_image_4 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_location);
        Log.e("foxy", " "+main_image+" "+other_image_1+" "+other_image_2+" "+other_image_3+" "+other_image_4);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            m_img_uri_str = bundle.getString("main_uri");
            o_img_uri_str_1 = bundle.getString("other_uri_1");
            o_img_uri_str_2 = bundle.getString("other_uri_2");
            o_img_uri_str_3 = bundle.getString("other_uri_3");
            o_img_uri_str_4 = bundle.getString("other_uri_4");
        }

        if (m_img_uri_str != null&&!m_img_uri_str.isEmpty()) {
            createFile(m_img_uri_str);
        }
        if (o_img_uri_str_1 != null&&!o_img_uri_str_1.isEmpty()) {
            createFile(o_img_uri_str_1);
        }
        if (o_img_uri_str_2 != null&&!o_img_uri_str_2.isEmpty()) {
            createFile(o_img_uri_str_2);
        }
        if (o_img_uri_str_3 != null&&!o_img_uri_str_3.isEmpty()) {
            createFile(o_img_uri_str_3);
        }
        if (o_img_uri_str_4 != null&&!o_img_uri_str_4.isEmpty()) {
            createFile(o_img_uri_str_4);
        }


        post_this_ad = (TextView) findViewById(R.id.post_this_ad);
        post_this_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postTheAd();
            }
        });
    }

    private void postTheAd() {

        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), "HAHA6");
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

        Log.e("itenom", "here "+""+main_image+" : "+m_img_uri_str);

        Call<PostContainments> postAdCall ;

        if(!Acquire.CALL_WITH_IMAGES)
        {
            Log.e("itenom", "heretoo"+Acquire.CALL_WITH_IMAGES);

            postAdCall = apiInterface.postAds(title, description, category, availability, condition, quantity,
                    rental_option, locality, city, pincode, state, district, phone);
        }
        else {
            Log.e("itenom", "heretx"+Acquire.CALL_WITH_IMAGES);

            postAdCall =apiInterface.postAdsWithImage(title, description, category, availability, condition, quantity,
                    rental_option, locality, city, pincode, state, district, phone, main_image, filename, other_image_1, other_image_2,
                    other_image_3, other_image_4);
        }
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
        final int new_window_height = screen_height * 72 / 100;
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.width = Math.max(layout.width, new_window_width);
        layout.height = Math.max(layout.height, new_window_height);
        getWindow().setAttributes(layout);
    }

    private void createFile(String uri_string) {
        Uri uri = Uri.parse(uri_string);
        Log.e("kbkbnk", "" + uri);
        String filePath = getRealPathFromURIPath(uri, GetAdsLocation.this);
        File file = new File(filePath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Log.e("foxy", " "+main_image+" "+other_image_1+" "+other_image_2+" "+other_image_3+" "+other_image_4);

        if (uri_string.equals(m_img_uri_str))
            main_image = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        else if (uri_string.equals(o_img_uri_str_1))
            other_image_1 = MultipartBody.Part.createFormData("other_image[]", file.getName(), requestBody);

        else if (uri_string.equals(o_img_uri_str_2))
            other_image_2 = MultipartBody.Part.createFormData("other_image[]", file.getName(), requestBody);

        else if (uri_string.equals(o_img_uri_str_3))
            other_image_3 = MultipartBody.Part.createFormData("other_image[]", file.getName(), requestBody);

        else if (uri_string.equals(o_img_uri_str_4))
            other_image_4 = MultipartBody.Part.createFormData("other_image[]", file.getName(), requestBody);

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
