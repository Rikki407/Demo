package com.kirayepay.kirayepay101.Network.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rikki on 9/29/17.
 */

public class AdsResponse
{
    @SerializedName("ads")
    ArrayList<AdsContainments> ads;
    @SerializedName("images")
    ArrayList<ImagesContainments> images;

    public ArrayList<AdsContainments> getAds() {
        return ads;
    }

    public void setAds(ArrayList<AdsContainments> ads) {
        this.ads = ads;
    }

    public ArrayList<ImagesContainments> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImagesContainments> images) {
        this.images = images;
    }
}
