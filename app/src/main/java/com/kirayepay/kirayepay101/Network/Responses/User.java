package com.kirayepay.kirayepay101.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rikki on 8/24/17.
 */

public class User
{
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("userid")
    String userid;
    @SerializedName("plateform")
    String plateform;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserid() {
        return userid;
    }

    public String getPlateform() {
        return plateform;
    }
}
