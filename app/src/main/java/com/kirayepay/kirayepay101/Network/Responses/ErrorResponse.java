package com.kirayepay.kirayepay101.Network.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rikki on 10/11/17.
 */

public class ErrorResponse
{
    @SerializedName("email")
    ArrayList<String> email;
    @SerializedName("phone")
    ArrayList<String> phone;
    @SerializedName("password")
    ArrayList<String> password;

    public ArrayList<String> getEmail() {
        return email;
    }

    public ArrayList<String> getPhone() {
        return phone;
    }

    public ArrayList<String> getPassword() {
        return password;
    }
}
