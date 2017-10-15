package com.kirayepay.kirayepay101.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rikki on 9/29/17.
 */

public class ImagesContainments
{
    @SerializedName("other_image")
    private String other_image;

    public String getOther_image() {
        return other_image;
    }
}
