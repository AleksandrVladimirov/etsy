package com.vladimirov.etsy.MyInterface;

import com.vladimirov.etsy.Model.CategoryList;
import com.vladimirov.etsy.Model.ProductImageList;
import com.vladimirov.etsy.Model.ProductList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerApi {

//    String key = "bgqr2fdkobe8n1n1er3quenh";
    String key = "vg0khjeoyvec7x41hn1dsq66";

    @GET("taxonomy/categories?api_key=" + key)
    Call<CategoryList> getCategory();

    @GET("listings/active?api_key=" + key)
    Call<ProductList> getProduct(@Query("category") String category,
                                 @Query("keywords") String keywords,
                                 @Query("page") int page);

    @GET("listings/{listing_id}/images?api_key=" + key)
    Call<ProductImageList> getProductImageUrl(@Path("listing_id") int listing_id);
}
