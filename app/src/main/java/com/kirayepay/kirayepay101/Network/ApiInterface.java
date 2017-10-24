package com.kirayepay.kirayepay101.Network;


import com.kirayepay.kirayepay101.Network.Responses.AdsContainments;
import com.kirayepay.kirayepay101.Network.Responses.AdsResponse;
import com.kirayepay.kirayepay101.Network.Responses.CategoriesContainments;
import com.kirayepay.kirayepay101.Network.Responses.EmailLoginResponse;
import com.kirayepay.kirayepay101.Network.Responses.EmailRegisterResponse;
import com.kirayepay.kirayepay101.Network.Responses.PostContainments;
import com.kirayepay.kirayepay101.Network.Responses.RequirementContainments;
import com.kirayepay.kirayepay101.Network.Responses.SellerProfileResponse.ProfileContainment;
import com.kirayepay.kirayepay101.Network.Responses.SocialLoginResponse;
import com.kirayepay.kirayepay101.Network.Responses.TrendingAdsResponse.TrendingContainments;

import java.util.ArrayList;
import java.util.Map;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
    Call<AdsResponse> getAdsDetails(@Path("ads_id") String ads_id, @Query("api_token") String apiKey);
    @GET("trending_ads")
    Call<TrendingContainments> getTrendingAds(@Query("api_token") String apiKey);
    @GET("most_viewed")
    Call<ArrayList<AdsContainments>> getMostViewedAds(@Query("api_token") String apiKey);
    @GET("requirement")
    Call<ArrayList<RequirementContainments>> getAllRequirements(@Query("api_token") String apiKey);
    @GET("requirement/{id}")
    Call<ArrayList<RequirementContainments>> getRequirementDetails(@Path("id") String ads_id, @Query("api_token") String apiKey);
    @GET("ads/search/list/{keyword}/{city_name}")
    Call<ArrayList<AdsContainments>> getSearchedAds(@Path("keyword") String keyword,@Path("city_name") String city_name,@Query("api_token") String apiKey);
    @GET("user_ads/{id}")
    Call<ArrayList<AdsContainments>> getUserAds(@Path("id") String user_id, @Query("api_token") String apiKey);
    @GET("user_requirement/{id}")
    Call<ArrayList<RequirementContainments>> getUserRequirements(@Path("id") String user_id, @Query("api_token") String apiKey);
    @GET("seller_profile/{id}")
    Call<ProfileContainment> getSellerInfo(@Path("id") String user_id, @Query("api_token") String apiKey);


    @FormUrlEncoded
    @POST("requirement?api_token=VZE6G37Kx8S1n7fnQBaSsK8cAu6zyF5h1aV8OznB9gofAt9iJYoeOs3aLfHz")
    Call<PostContainments> postRequirements(@Field("userid") String user_id, @Field("title") String title, @Field("parent_category") String parent_category,@Field("sub_category") String sub_category,
                                            @Field("child_category") String child_category,@Field("requirement") String description, @Field("when") String when, @Field("till") String till);


    @Multipart
    @POST("ads?api_token=VZE6G37Kx8S1n7fnQBaSsK8cAu6zyF5h1aV8OznB9gofAt9iJYoeOs3aLfHz")
    Call<PostContainments> postAds(@Part("title") RequestBody title, @Part("description") RequestBody description,
                                   @Part("parent_category") RequestBody parent_category, @Part("sub_category") RequestBody sub_category, @Part("category") RequestBody category,@Part("manufacture") RequestBody manufacture, @Part("availability") RequestBody availability,
                                            @Part("condition") RequestBody condition, @Part("quantity") RequestBody quantity, @Part("rental_option") RequestBody rental_option,
                                            @Part("rental_amount") RequestBody rental_amount,@Part("security_deposit") RequestBody security_deposit,@Part("locality") RequestBody locality,
                                            @Part("city") RequestBody city, @Part("pincode") RequestBody pincode, @Part("state") RequestBody state, @Part("district") RequestBody district, @Part("phone") RequestBody phone);
    @Multipart
    @POST("ads?api_token=VZE6G37Kx8S1n7fnQBaSsK8cAu6zyF5h1aV8OznB9gofAt9iJYoeOs3aLfHz")
    Call<PostContainments> postAdsWithImage(@Part("title") RequestBody title, @Part("description") RequestBody description,
                                            @Part("parent_category") RequestBody parent_category, @Part("sub_category") RequestBody sub_category, @Part("category") RequestBody category,@Part("manufacture") RequestBody manufacture, @Part("availability") RequestBody availability,
                                            @Part("condition") RequestBody condition, @Part("quantity") RequestBody quantity, @Part("rental_option") RequestBody rental_option, @Part("rental_amount") RequestBody rental_amount,@Part("security_deposit") RequestBody security_deposit,
                                            @Part("locality") RequestBody locality, @Part("city") RequestBody city, @Part("pincode") RequestBody pincode, @Part("state") RequestBody state,
                                            @Part("district") RequestBody district, @Part("phone") RequestBody phone, @Part MultipartBody.Part image, @Part("image") RequestBody name,
                                            @Part MultipartBody.Part other_img_1, @Part MultipartBody.Part other_img_2, @Part MultipartBody.Part other_img_3, @Part MultipartBody.Part other_img_4);


    @FormUrlEncoded
    @POST("visitors/save_viewer?api_token=VZE6G37Kx8S1n7fnQBaSsK8cAu6zyF5h1aV8OznB9gofAt9iJYoeOs3aLfHz")
    Call<PostContainments> userSendSurvey(@Field("userid") String userid,@Field("ads_id") String ads_id,@Field("ip_address") String ip_address);

    @FormUrlEncoded
    @POST("social_login?api_token=VZE6G37Kx8S1n7fnQBaSsK8cAu6zyF5h1aV8OznB9gofAt9iJYoeOs3aLfHz")
    Call<SocialLoginResponse> userSocialLogin(@Field("name") String name, @Field("email") String email, @Field("userid") String userid, @Field("plateform") String plateform);

    @FormUrlEncoded
    @POST("update_profile?api_token=VZE6G37Kx8S1n7fnQBaSsK8cAu6zyF5h1aV8OznB9gofAt9iJYoeOs3aLfHz")
    Call<PostContainments> userProfileUpdate(@Field("userid") String userid, @Field("name") String name, @Field("phone") String phone, @Field("locality") String locality,
                                             @Field("city") String city, @Field("country") String country, @Field("state") String state, @Field("district") String district, @Field("pincode") int pincode);
    @FormUrlEncoded
    @POST("seller_login?api_token=VZE6G37Kx8S1n7fnQBaSsK8cAu6zyF5h1aV8OznB9gofAt9iJYoeOs3aLfHz")
    Call<ArrayList<EmailLoginResponse>> userEmailLogin(@Field("username") String username,@Field("password") String password);

    @FormUrlEncoded
    @POST("seller_register?api_token=VZE6G37Kx8S1n7fnQBaSsK8cAu6zyF5h1aV8OznB9gofAt9iJYoeOs3aLfHz")
    Call<EmailRegisterResponse> userEmailRegister(@Field("name") String name, @Field("password") String password,
                                                             @Field("phone") String phone, @Field("email") String email,
                                                             @Field("password_confirmation") String password_confirmation);
}
