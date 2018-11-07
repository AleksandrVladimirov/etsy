package com.vladimirov.etsy.Adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vladimirov.etsy.Model.Product;
import com.vladimirov.etsy.MyInterface.OnProductSelectedListener;
import com.vladimirov.etsy.R;

public class ProductsListAdapter extends PagedListAdapter<Product, ProductsViewHolder> {

    private OnProductSelectedListener listener;

    public ProductsListAdapter() {
        super(Product.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, int i) {
        productsViewHolder.bind(getItem(i), listener);
    }

    public void setOnProductSelectedListener(OnProductSelectedListener listener){
        this.listener = listener;
    }
}
