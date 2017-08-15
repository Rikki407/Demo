package com.kirayepay.kirayepay101.Network;


import android.database.Observable;

import com.kirayepay.kirayepay101.Network.Responses.AdsContainments;
import com.kirayepay.kirayepay101.Network.Responses.CategoriesContainments;
import com.kirayepay.kirayepay101.Network.Responses.PostAdsContainments;

import java.util.ArrayList;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rikki on 8/3/17.
 */

public interface ApiInterface
{
    @GET("category")
    Call<ArrayList<CategoriesContainments>> getAllCategories(@Query("api_token") String apiKey);
    @GET("ads_category/{cat_id}")
    Call<ArrayList<AdsContainments>> getAllAdsInCategories(@Path("cat_id") String cat_id,@Query("api_token") String apiKey);
    @GET("ads/{ads_id}")
    Call<ArrayList<AdsContainments>> getAdsDetails(@Path("ads_id") String ads_id, @Query("api_token") String apiKey);
    @GET("most_viewed")
    Call<ArrayList<AdsContainments>> getMostViewedAds(@Query("api_token") String apiKey);

    @Multipart
    @POST("ads?api_token=VZE6G37Kx8S1n7fnQBaSsK8cAu6zyF5h1aV8OznB9gofAt9iJYoeOs3aLfHz")
    Call<PostAdsContainments> postAds(@Part("title") RequestBody title, @Part("description") RequestBody description,
                                     @Part("parent_category") RequestBody parent_category, @Part("availability") RequestBody availability,
                                     @Part("condition") RequestBody condition, @Part("quantity") RequestBody quantity, @Part("rental_option") RequestBody rental_option,
                                     @Part("locality") RequestBody locality, @Part("city") RequestBody city, @Part("pincode") RequestBody pincode, @Part("state") RequestBody state,
                                     @Part("district") RequestBody district, @Part("phone") RequestBody phone, @Part MultipartBody.Part image,@Part ("image") RequestBody name);

}
