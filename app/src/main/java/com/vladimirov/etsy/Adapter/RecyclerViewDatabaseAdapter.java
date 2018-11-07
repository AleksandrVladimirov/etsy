package com.vladimirov.etsy.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.vladimirov.etsy.Model.Product;
import com.vladimirov.etsy.MyInterface.OnProductLongClickListener;
import com.vladimirov.etsy.MyInterface.OnProductSelectedListener;
import com.vladimirov.etsy.R;

import java.util.ArrayList;

public class RecyclerViewDatabaseAdapter extends RecyclerView.Adapter<ProductsViewHolder> {

    private Activity activity;
    private ArrayList<Product> products;
    private OnProductSelectedListener listener;
    private OnProductLongClickListener longClickListener;

    public RecyclerViewDatabaseAdapter(Activity activity, ArrayList<Product> products) {
        this.activity = activity;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(activity, R.layout.recycler_view_item, null);

        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, int i) {
        productsViewHolder.bind(products.get(i), listener, longClickListener, i);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setOnProductSelectedListener(OnProductSelectedListener listener){
        this.listener = listener;
    }

    public void setOnLongClickListener(OnProductLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }

}
