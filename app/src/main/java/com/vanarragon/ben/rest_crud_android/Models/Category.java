package com.vanarragon.ben.rest_crud_android.Models;

/**
 * Created by Benjamin van Arragon on 2017-02-20.
 */

import com.google.gson.annotations.SerializedName;


public class Category {
    @SerializedName("category_id")
    private Integer categoryID;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("category_desc")
    private String categoryDescription;


    public Category(String categoryDescription, Integer categoryID, String categoryName) {
        this.categoryDescription = categoryDescription;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
