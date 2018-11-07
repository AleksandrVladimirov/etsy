package com.vladimirov.etsy.Data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.vladimirov.etsy.Model.Product;
import com.vladimirov.etsy.Model.ProductImageList;
import com.vladimirov.etsy.Model.ProductList;
import com.vladimirov.etsy.Model.RequestFailure;
import com.vladimirov.etsy.MyInterface.Retryable;
import com.vladimirov.etsy.MyInterface.ServerApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ProductDataSource extends PageKeyedDataSource<Integer, Product> {

    private ServerApi serverApi;
    private String categorySelected, keywords;
    private final MutableLiveData<RequestFailure> requestFailureLiveData;

    public ProductDataSource(ServerApi serverApi, String categorySelected, String keywords) {
        this.serverApi = serverApi;
        this.categorySelected = categorySelected;
        this.keywords = keywords;
        this.requestFailureLiveData = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Product> callback) {
        final int page = 1;

        Call<ProductList> call = serverApi.getProduct(categorySelected, keywords, page);

        Callback<ProductList> requestCallback = new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                ProductList productList = response.body();

                if(productList == null){
                    onFailure(call, new HttpException(response));
                    return;
                }

                final ProductList fProductList = productList;
                for(int j = 0; j < productList.getProductArrayList().size(); j++) {
                    final int id = response.body().getProductArrayList().get(j).getListing_id();
                    final int iFinal = j;
                    Call<ProductImageList> productImageListCall = serverApi.getProductImageUrl(id);
                    productImageListCall.enqueue(new Callback<ProductImageList>() {
                        @Override
                        public void onResponse(Call<ProductImageList> call1, Response<ProductImageList> response1) {
                            if (response1.body() != null && response1.isSuccessful()) {
                                fProductList.getProductArrayList().get(iFinal).setUrl(response1.body().getProductImageArrayList().get(0).getUrl());
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductImageList> call, Throwable t) {

                        }
                    });
                }

                callback.onResult(
                        productList.getProductArrayList(),
                        0,
                        productList.getTotalCount(),
                        null,
                        page + 1
                );
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                Retryable retryable = new Retryable() {
                    @Override
                    public void retry() {
                        loadInitial(params, callback);
                    }
                };

                handleError(retryable, t);
            }
        };

        call.enqueue(requestCallback);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Product> callback) {
        final int page = params.key;

        Call<ProductList> call = serverApi.getProduct(categorySelected, keywords, page);

        final Callback<ProductList> requestCallback = new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                ProductList productList = response.body();

                if(productList == null){
                    onFailure(call, new HttpException(response));
                    return;
                }
                final ProductList fProductList = productList;
                for(int j = 0; j < productList.getProductArrayList().size(); j++) {
                    final int id = response.body().getProductArrayList().get(j).getListing_id();
                    final int iFinal = j;
                    Call<ProductImageList> productImageListCall = serverApi.getProductImageUrl(id);
                    productImageListCall.enqueue(new Callback<ProductImageList>() {
                        @Override
                        public void onResponse(Call<ProductImageList> call1, Response<ProductImageList> response1) {
                            if (response1.body() != null && response1.isSuccessful()) {
                                fProductList.getProductArrayList().get(iFinal).setUrl(response1.body().getProductImageArrayList().get(0).getUrl());
                                List<Product> products = fProductList.getProductArrayList();
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductImageList> call, Throwable t) {

                        }
                    });
                }

                callback.onResult(
                        productList.getProductArrayList(),
                        page +1
                );
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                Retryable retryable = new Retryable() {
                    @Override
                    public void retry() {
                        loadAfter(params, callback);
                    }
                };

                handleError(retryable, t);
            }
        };

        call.enqueue(requestCallback);
    }

    public LiveData<RequestFailure> getRequestFailureLiveData() {
        return requestFailureLiveData;
    }

    private void handleError(Retryable retryable, Throwable t) {
        requestFailureLiveData.postValue(new RequestFailure(retryable, t.getMessage()));
    }
}
