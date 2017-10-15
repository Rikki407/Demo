package com.kirayepay.kirayepay101.Network.Responses.TrendingAdsResponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rikki on 10/1/17.
 */

public class TrendingContainments {
    @SerializedName("trending_ads")
    ArrayList<TrendingAds> trending_ads;

    public ArrayList<TrendingAds> getTrending_ads() {
        return trending_ads;
    }

    public void setTrending_ads(ArrayList<TrendingAds> trending_ads) {
        this.trending_ads = trending_ads;
    }
}
