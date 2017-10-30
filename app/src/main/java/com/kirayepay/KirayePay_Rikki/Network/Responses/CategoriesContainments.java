package com.kirayepay.KirayePay_Rikki.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rikki on 8/3/17.
 */

public class CategoriesContainments
{
    @SerializedName("id")
    private int category_id;
    @SerializedName("category")
    private String category_name;
    @SerializedName("image")
    private String category_image_url;
    @SerializedName("parent_category")
    private int parent_category = 0 ;
    @SerializedName("is_service")
    private String is_service;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;

    public CategoriesContainments(int category_id, String category_name, String category_image_url, int parent_category, String is_service, String created_at, String updated_at)
    {
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_image_url = category_image_url;
        this.parent_category = parent_category;
        this.is_service = is_service;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getCategory_image_url() {
        return category_image_url;
    }

    public int getParent_category() {
        return parent_category;
    }

    public String getIs_service() {
        return is_service;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
