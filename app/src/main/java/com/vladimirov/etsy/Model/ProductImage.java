package com.vladimirov.etsy.Model;

import com.google.gson.annotations.SerializedName;

public class ProductImage {

    @SerializedName("url_fullxfull")
    private String url;

    public ProductImage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
