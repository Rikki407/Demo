package com.kirayepay.KirayePay_Rikki.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rikki on 8/24/17.
 */

public class SocialLoginResponse
{
    @SerializedName("message")
    String message;
    @SerializedName("user")
    User user;

    public String getMessage() {
        return message;
    }

    public User getStatus() {
        return user;
    }
}
