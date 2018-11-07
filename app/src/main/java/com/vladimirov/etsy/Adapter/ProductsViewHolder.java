package com.vladimirov.etsy.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vladimirov.etsy.Model.Product;
import com.vladimirov.etsy.MyInterface.OnProductLongClickListener;
import com.vladimirov.etsy.MyInterface.OnProductSelectedListener;
import com.vladimirov.etsy.R;

class ProductsViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageProduct;
    public TextView nameProduct;

    ProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        imageProduct = itemView.findViewById(R.id.imageProduct);
        nameProduct = itemView.findViewById(R.id.nameProduct);
    }

    void bind(Product product, final OnProductSelectedListener listener){
        if(product != null){
            nameProduct.setText(product.getTitle());

            Picasso.get()
                .load(product.getUrl())
                .resize(150, 150)
                .into(imageProduct);

        } else {
            nameProduct.setText("Loading...");
        }

        final String price = product != null ? product.getPrice() : null;
        final String currency = product != null ? product.getCurrency_code() : null;
        final String title = product != null ? product.getTitle() : null;
        final String description = product != null ? product.getDescription() : null;
        final String url = product != null ? product.getUrl() : null;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onProductSelectedListened(price, currency, title, description, url);
                }
            }
        });
    }

    void bind(Product product, final OnProductSelectedListener listener, final OnProductLongClickListener longClickListener, final int position){
        if(product != null){
            nameProduct.setText(product.getTitle());

            Picasso.get()
                    .load(product.getUrl())
                    .resize(150, 150)
                    .into(imageProduct);

        } else {
            nameProduct.setText("Loading...");
        }

        final String price = product != null ? product.getPrice() : null;
        final String currency = product != null ? product.getCurrency_code() : null;
        final String title = product != null ? product.getTitle() : null;
        final String description = product != null ? product.getDescription() : null;
        final String url = product != null ? product.getUrl() : null;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onProductSelectedListened(price, currency, title, description, url);
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(longClickListener != null){
                    longClickListener.onProductLongClickListener(url, position);
                }
                return false;
            }
        });
    }
}
