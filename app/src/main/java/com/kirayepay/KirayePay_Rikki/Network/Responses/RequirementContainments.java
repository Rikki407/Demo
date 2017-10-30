package com.kirayepay.KirayePay_Rikki.Network.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rikki on 8/16/17.
 */

public class RequirementContainments
{
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("userid")
    private long user_id;
    @SerializedName("requirement")
    private String requirement;
    @SerializedName("when")
    private String when;
    @SerializedName("till")
    private String till;
    @SerializedName("status")
    private String status;
    @SerializedName("category_id")
    private int category_id;
    @SerializedName("category")
    private String category;
    @SerializedName("user")
    private ArrayList<UserOfRequirement> user;


    public ArrayList<UserOfRequirement> getUser() {
        return user;
    }

    public RequirementContainments(int id, String title, long user_id, String requirement, String when,
                                   String till, String status, int category_id, String category) {
        this.id = id;
        this.title = title;
        this.user_id = user_id;
        this.requirement = requirement;
        this.when = when;
        this.till = till;
        this.status = status;
        this.category_id = category_id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getRequirement() {
        return requirement;
    }

    public String getWhen() {
        return when;
    }

    public String getTill() {
        return till;
    }

    public String getStatus() {
        return status;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getCategory() {
        return category;
    }

    /**
     * Created by rikki on 10/10/17.
     */

    public static class UserOfRequirement {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("email")
        private String email;
        @SerializedName("allowed")
        private int allowed;
        @SerializedName("locality")
        private String locality;
        @SerializedName("city")
        private String city;
        @SerializedName("district")
        private String district;
        @SerializedName("state")
        private String state;
        @SerializedName("country")
        private String country;
        @SerializedName("pincode")
        private int pincode;
        @SerializedName("phone")
        private long phone;
        @SerializedName("userid")
        private int userid;
        @SerializedName("role")
        private String role;
        @SerializedName("permission")
        private String permission;

        public String getState() {
            return state;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public int getAllowed() {
            return allowed;
        }

        public String getLocality() {
            return locality;
        }

        public String getCity() {
            return city;
        }

        public String getDistrict() {
            return district;
        }

        public String getCountry() {
            return country;
        }

        public int getPincode() {
            return pincode;
        }

        public long getPhone() {
            return phone;
        }

        public int getUserid() {
            return userid;
        }

        public String getRole() {
            return role;
        }

        public String getPermission() {
            return permission;
        }
    }
}
