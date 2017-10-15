package com.kirayepay.kirayepay101.Network.Responses.SellerProfileResponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rikki on 10/7/17.
 */

public class SellerContainments
{
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private long phone;
    @SerializedName("city")
    private String city;
    @SerializedName("locality")
    private String locality;
    @SerializedName("district")
    private String district;
    @SerializedName("state")
    private String state;
    @SerializedName("country")
    private String country;
    @SerializedName("pincode")
    private int pincode;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public String getLocality() {
        return locality;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public int getPincode() {
        return pincode;
    }
}
