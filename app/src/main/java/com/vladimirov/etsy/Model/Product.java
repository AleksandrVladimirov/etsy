package com.vladimirov.etsy.Model;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("listing_id")
    private int listing_id;
    @SerializedName("price")
    private String price;
    @SerializedName("currency_code")
    private String currency_code;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("url_fullxfull")
    private String url;

    public Product() {
    }

    public Product(int listing_id, String price, String currency_code, String title, String description, String url) {
        this.listing_id = listing_id;
        this.price = price;
        this.currency_code = currency_code;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public static final DiffUtil.ItemCallback<Product> DIFF_CALLBACK = new DiffUtil.ItemCallback<Product>() {

        @Override
        public boolean areItemsTheSame(@NonNull Product product, @NonNull Product t1) {
            return product.getTitle().equals(t1.getTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product product, @NonNull Product t1) {
            return true;
        }
    };

    public int getListing_id() {
        return listing_id;
    }

    public void setListing_id(int listing_id) {
        this.listing_id = listing_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
