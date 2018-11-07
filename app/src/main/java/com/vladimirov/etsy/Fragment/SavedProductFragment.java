package com.vladimirov.etsy.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vladimirov.etsy.Adapter.RecyclerViewDatabaseAdapter;
import com.vladimirov.etsy.Data.DatabaseHelper;
import com.vladimirov.etsy.Model.Product;
import com.vladimirov.etsy.MyInterface.OnProductLongClickListener;
import com.vladimirov.etsy.MyInterface.OnProductSelectedListener;
import com.vladimirov.etsy.ProductSelectedActivity;
import com.vladimirov.etsy.R;

import java.util.ArrayList;

public class SavedProductFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerViewDatabaseAdapter adapter;
    private ArrayList<Product> productArrayList = new ArrayList<Product>();

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saved_product_fragment, null);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerView);

        dbHelper = new DatabaseHelper(getContext());

        loadProducts();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productArrayList.clear();
                loadProducts();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        adapter = new RecyclerViewDatabaseAdapter(getActivity(), productArrayList);

        adapter.setOnProductSelectedListener(new OnProductSelectedListener() {
            @Override
            public void onProductSelectedListened(String price, String currency_code, String title, String description, String url) {
                Intent intent = new Intent(getActivity(), ProductSelectedActivity.class);
                intent.putExtra("PRICE", price);
                intent.putExtra("CURRENCY", currency_code);
                intent.putExtra("TITLE", title);
                intent.putExtra("DESCRIPTION", description);
                intent.putExtra("URL", url);
                startActivity(intent);
            }
        });

        adapter.setOnLongClickListener(new OnProductLongClickListener() {
            @Override
            public void onProductLongClickListener(String url, final int position) {
                final String key = url;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete item?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db = dbHelper.getWritableDatabase();
                        db.delete("myTable", "image LIKE '%" + key + "%'", null);
                        db.close();
                        productArrayList.remove(position);
                        recyclerView.removeViewAt(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, productArrayList.size());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadProducts() {
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM myTable", null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    Product product = new Product();
                    product.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    product.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                    product.setCurrency_code(cursor.getString(cursor.getColumnIndex("currency")));
                    product.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    product.setUrl(cursor.getString(cursor.getColumnIndex("image")));
                    productArrayList.add(product);
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }

}
