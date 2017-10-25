package com.kirayepay.kirayepay101.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rikki on 8/12/17.
 */

public class PostContainments
{
    @SerializedName("message")
    String message;
    @SerializedName("status")
    String status;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
