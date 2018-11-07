package com.vladimirov.etsy.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryList {

    @SerializedName("results")
    private ArrayList<Category> categories = null;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
