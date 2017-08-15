package com.kirayepay.kirayepay101.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rikki on 8/12/17.
 */

public class PostAdsContainments
{
    @SerializedName("message")
    String message;                 // "successful" for successful POST request
    @SerializedName("status")
    String status;                  // "success" for successful POST request

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
