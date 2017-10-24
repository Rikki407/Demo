package com.kirayepay.kirayepay101.Fab.ADs;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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
    private String m_img_uri_str, o_img_uri_str_1, o_img_uri_str_2, o_img_uri_str_3, o_img_uri_str_4,
            sd_string, cond_string, ro_string, ra_string, desc_string, tite_string, man_string, quan_string;
    private TextView post_this_ad;
    MultipartBody.Part main_image=null;
    RequestBody filename;
    MultipartBody.Part other_image_1 = null, other_image_2 = null, other_image_3 = null, other_image_4 = null;
    int main_cat_id,sub_cat_1_id,sub_cat_2_id;
    private EditText Locality,City,State,District,Phone,Pincode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_location);
        Log.e("foxy", " "+main_image+" "+other_image_1+" "+other_image_2+" "+other_image_3+" "+other_image_4);
        Locality = (EditText)findViewById(R.id.post_ads_locality);
        City = (EditText)findViewById(R.id.post_ads_city);
        State = (EditText)findViewById(R.id.post_ads_state);
        District = (EditText)findViewById(R.id.post_ads_destrict);
        Phone = (EditText)findViewById(R.id.post_ads_phone);
        Pincode = (EditText)findViewById(R.id.post_ads_pincode);

        setEditTexts();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            m_img_uri_str = bundle.getString("main_uri");
            o_img_uri_str_1 = bundle.getString("other_uri_1");
            o_img_uri_str_2 = bundle.getString("other_uri_2");
            o_img_uri_str_3 = bundle.getString("other_uri_3");
            o_img_uri_str_4 = bundle.getString("other_uri_4");
            tite_string = bundle.getString("title");
            desc_string = bundle.getString("description");
            man_string = bundle.getString("manufacture");
            quan_string = bundle.getString("quantity");
            ra_string = bundle.getString("rental_amount");
            sd_string = bundle.getString("security_deposit");
            cond_string = bundle.getString("conditions");
            ro_string = bundle.getString("rental_option");
            main_cat_id = bundle.getInt("main_cat_id");
            sub_cat_1_id = bundle.getInt("sub_cat_1_id");
            sub_cat_2_id = bundle.getInt("sub_cat_2_id");
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

    private void setEditTexts() {
        SharedPreferences prefs = getSharedPreferences(Acquire.USER_DETAILS,MODE_PRIVATE);
        Locality.setText(prefs.getString(Acquire.LOCALITY,""));
        City.setText(prefs.getString(Acquire.CITY,""));
        State.setText(prefs.getString(Acquire.STATE,""));
        District.setText(prefs.getString(Acquire.DISTRICT,""));
        Phone.setText(prefs.getString(Acquire.PHONE,""));
        Pincode.setText(prefs.getString(Acquire.PINCODE,""));

    }

    private void postTheAd() {
//        Log.e("All Fields",sd_string+", "+cond_string+", "+ro_string+", "+ ra_string+", "+desc_string+", "+tite_string+", "+man_string+", "+quan_string+", "+main_cat_id+", "+sub_cat_1_id+", "+sub_cat_2_id);
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), ""+tite_string);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), ""+desc_string);
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), ""+main_cat_id);
        RequestBody availability = RequestBody.create(MediaType.parse("text/plain"), "yes");
        RequestBody condition = RequestBody.create(MediaType.parse("text/plain"), ""+cond_string);
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), ""+quan_string);
        RequestBody rental_option = RequestBody.create(MediaType.parse("text/plain"), ""+ro_string);
        RequestBody security_deposit = RequestBody.create(MediaType.parse("text/plain"), ""+sd_string);
        RequestBody manufacture = RequestBody.create(MediaType.parse("text/plain"), ""+man_string);
        RequestBody rental_amount = RequestBody.create(MediaType.parse("text/plain"), ""+ra_string);

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

            postAdCall = apiInterface.postAds(title, description, category,manufacture, availability, condition, quantity,
                    rental_option,rental_amount,security_deposit,locality, city, pincode, state, district, phone);
        }
        else {
            Log.e("itenom", "heretx"+Acquire.CALL_WITH_IMAGES);

            postAdCall =apiInterface.postAdsWithImage(title, description, category,manufacture, availability, condition, quantity,
                    rental_option,rental_amount,security_deposit,locality, city, pincode, state, district, phone, main_image, filename,
                    other_image_1, other_image_2, other_image_3, other_image_4);
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
