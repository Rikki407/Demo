package com.kirayepay.kirayepay101.Network.Responses.TrendingAdsResponse;

import com.google.gson.annotations.SerializedName;
import com.kirayepay.kirayepay101.Network.Responses.AdsContainments;

import java.util.ArrayList;

/**
 * Created by rikki on 9/29/17.
 */

public class TrendingAds
{
    @SerializedName("title_category")
    Category title_category;
    @SerializedName("ads")
    ArrayList<AdsContainments> ads;

    public Category getTitle_category() {
        return title_category;
    }

    public void setTitle_category(Category title_category) {
        this.title_category = title_category;
    }

    public ArrayList<AdsContainments> getAds() {
        return ads;
    }

    public void setAds(ArrayList<AdsContainments> ads) {
        this.ads = ads;
    }

    /**
     * Created by rikki on 9/29/17.
     */

    public static class Category
    {
        @SerializedName("category")
        String category;
        @SerializedName("id")
        int id;

        public String getCategory() {
            return category;
        }


        public int getId() {
            return id;
        }


    }

    /**
     * Created by rikki on 9/29/17.
     */

    public static class Ads
    {
        @SerializedName("trending_ads")
        TrendingAds trending_ads;

        public TrendingAds getTrending_ads() {
            return trending_ads;
        }

        public void setTrending_ads(TrendingAds trending_ads) {
            this.trending_ads = trending_ads;
        }
    }
}
