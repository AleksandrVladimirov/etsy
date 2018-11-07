package com.vladimirov.etsy.Model;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("name")
    private String name;
    @SerializedName("long_name")
    private String longName;

    public Category(String name, String longName) {
        this.name = name;
        this.longName = longName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }
}
