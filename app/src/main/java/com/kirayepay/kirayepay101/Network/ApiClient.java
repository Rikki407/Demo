package com.kirayepay.kirayepay101.Network;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rikki on 8/3/17.
 */

public class ApiClient
{
    static ApiInterface apiInterface;

    public static ApiInterface getApiInterface()
    {
        if(apiInterface == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.kirayepay.com/api/")
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().serializeNulls().create()))
                    .build();
            apiInterface =retrofit.create(ApiInterface.class);
        }
        return apiInterface;
    }
}
