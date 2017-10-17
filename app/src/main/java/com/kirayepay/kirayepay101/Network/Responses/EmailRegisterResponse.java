package com.kirayepay.kirayepay101.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rikki on 9/7/17.
 */

public class EmailRegisterResponse
{
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private String status;
    @SerializedName("user_id")
    private int id;
    @SerializedName("error")
    private ErrorResponse error;

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public ErrorResponse getError() {
        return error;
    }
}
