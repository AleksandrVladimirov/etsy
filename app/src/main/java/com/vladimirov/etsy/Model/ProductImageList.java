package com.vladimirov.etsy.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductImageList {

    @SerializedName("results")
    private ArrayList<ProductImage> productImageArrayList = null;

    public ProductImageList(ArrayList<ProductImage> productImageArrayList) {
        this.productImageArrayList = productImageArrayList;
    }

    public ArrayList<ProductImage> getProductImageArrayList() {
        return productImageArrayList;
    }

    public void setProductImageArrayList(ArrayList<ProductImage> productImageArrayList) {
        this.productImageArrayList = productImageArrayList;
    }
}
