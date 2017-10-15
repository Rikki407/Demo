package com.kirayepay.kirayepay101.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rikki on 8/24/17.
 */

public class SocialLoginResponse
{
    @SerializedName("message")
    String message;                 // "User Registered." for successful POST request
    @SerializedName("user")
    User user;                  // "success" for successful POST request

    public String getMessage() {
        return message;
    }

    public User getStatus() {
        return user;
    }
}
