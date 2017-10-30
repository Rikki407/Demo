package com.kirayepay.KirayePay_Rikki.Network.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rikki on 8/8/17.
 */

public class AdsContainments
{
    @SerializedName("ads_id")
    private int ads_id;
    @SerializedName("title")
    private String ads_title;
    @SerializedName("manufacture")
    private String manufacture;
    @SerializedName("make_year")
    private String make_year;
    @SerializedName("availability")
    private String availability;
    @SerializedName("available_from")
    private String available_from;
    @SerializedName("condition")
    private String condition;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("rental_option")
    private String rental_option;
    @SerializedName("rental_amount")
    private int rental_amount;
    @SerializedName("security_deposit")
    private int security_deposit;
    @SerializedName("delivery_charge")
    private String delivery_charge;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("seller_id")
    private String seller_id;
    @SerializedName("image")
    private String image;
    @SerializedName("description")
    private String description;
    @SerializedName("category_id")
    private int category_id;
    @SerializedName("category")
    private String category;
    @SerializedName("parent_category")
    private String parent_category;
    @SerializedName("parent_id")
    private int parent_id;
    @SerializedName("product_id")
    private int product_id;
    @SerializedName("location_phone")
    private long location_phone;
    @SerializedName("location_locality")
    private String location_locality;

    @SerializedName("addr_city")
    private String addr_city;

    @SerializedName("location_district")
    private String location_district;
    @SerializedName("location_state")
    private String location_state;
    @SerializedName("location_pincode")
    private int location_pincode;
    @SerializedName("seller_name")
    private String seller_name;
    @SerializedName("seller_email")
    private String seller_email;
    @SerializedName("seller_phone")
    private long seller_phone;
    @SerializedName("seller_city")
    private String seller_city;
    @SerializedName("seller_locality")
    private String seller_locality;
    @SerializedName("seller_district")
    private String seller_district;
    @SerializedName("seller_state")
    private String seller_state;
    @SerializedName("seller_country")
    private String seller_country;
    @SerializedName("seller_pincode")
    private int seller_pincode;
    public int getAds_id() {
        return ads_id;
    }

    public String getAds_title() {
        return ads_title;
    }

    public String getManufacture() {
        return manufacture;
    }

    public String getMake_year() {
        return make_year;
    }

    public String getAvailability() {
        return availability;
    }

    public String getAvailable_from() {
        return available_from;
    }

    public String getCondition() {
        return condition;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getRental_option() {
        return rental_option;
    }

    public int getRental_amount() {
        return rental_amount;
    }

    public int getSecurity_deposit() {
        return security_deposit;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getCategory() {
        return category;
    }

    public String getParent_category() {
        return parent_category;
    }

    public int getParent_id() {
        return parent_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public long getLocation_phone() {
        return location_phone;
    }

    public String getLocation_locality() {
        return location_locality;
    }

    public String getAddr_city() {
        return addr_city;
    }

    public String getLocation_district() {
        return location_district;
    }

    public String getLocation_state() {
        return location_state;
    }

    public int getLocation_pincode() {
        return location_pincode;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public long getSeller_phone() {
        return seller_phone;
    }

    public String getSeller_city() {
        return seller_city;
    }

    public String getSeller_locality() {
        return seller_locality;
    }

    public String getSeller_district() {
        return seller_district;
    }

    public String getSeller_state() {
        return seller_state;
    }

    public String getSeller_country() {
        return seller_country;
    }

    public int getSeller_pincode() {
        return seller_pincode;
    }
}
