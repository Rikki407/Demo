package com.kirayepay.kirayepay101.Network.Responses.SellerProfileResponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rikki on 10/7/17.
 */

public class ProfileContainment
{
    @SerializedName("seller")
    ArrayList<SellerContainments> seller;

    public ArrayList<SellerContainments> getSeller() {
        return seller;
    }
}
