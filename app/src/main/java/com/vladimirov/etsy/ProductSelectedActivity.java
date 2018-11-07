package com.vladimirov.etsy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vladimirov.etsy.Data.DatabaseHelper;

public class ProductSelectedActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ImageView image;
    private TextView name, price, currency, description;
    private String Price, Currency, Title, Description, Url;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_item);

        dbHelper = new DatabaseHelper(this);

        image = findViewById(R.id.imageProductFull);
        name = findViewById(R.id.nameProductFull);
        price = findViewById(R.id.priceProduct);
        currency = findViewById(R.id.currencyProduct);
        description = findViewById(R.id.descriptionProduct);
        fab = findViewById(R.id.floatingActionButton);

        Price = getIntent().getStringExtra("PRICE");
        Currency = getIntent().getStringExtra("CURRENCY");
        Title = getIntent().getStringExtra("TITLE");
        Description = getIntent().getStringExtra("DESCRIPTION");
        Url = getIntent().getStringExtra("URL");

        if(savedProduct()){
           fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveProduct();
                    fab.setVisibility(View.GONE);
                }
            });
        }

        price.setText(Price);
        name.setText(Title);
        currency.setText(Currency);
        description.setText(Description);

        Picasso.get()
                .load(Url)
                .into(image);
    }

    private void saveProduct() {
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", Title);
        values.put("price", Price);
        values.put("currency", Currency);
        values.put("description", Description);
        values.put("image", Url);

        db.insert("myTable", null, values);
    }

    protected boolean savedProduct(){
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM myTable WHERE image LIKE '%" + Url + "%'", null);
        if(cursor != null && cursor.moveToFirst()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        if(cursor != null){
            cursor.close();
        }
    }
}
