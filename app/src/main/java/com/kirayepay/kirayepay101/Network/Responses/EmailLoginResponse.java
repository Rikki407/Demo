package com.kirayepay.kirayepay101.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rikki on 8/24/17.
 */

public class EmailLoginResponse
{
    @SerializedName("userid")
    private String userid;
    @SerializedName("role_id")
    private String role_id;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("role")
    private String role;
    @SerializedName("permission")
    private String permission;

    public String getUserid() {
        return userid;
    }

    public String getRole_id() {
        return role_id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPermission() {
        return permission;
    }
}
