package com.vladimirov.etsy;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vladimirov.etsy.Adapter.ProductsListAdapter;
import com.vladimirov.etsy.Data.ProductDataSource;
import com.vladimirov.etsy.Executor.MainThreadExecutor;
import com.vladimirov.etsy.Model.Product;
import com.vladimirov.etsy.Model.RequestFailure;
import com.vladimirov.etsy.MyInterface.OnProductSelectedListener;
import com.vladimirov.etsy.MyInterface.ServerApi;

public class ProductsListActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProductsListAdapter adapter;
    private MainThreadExecutor executor;
    private ServerApi serverApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_product_fragment);

        executor = new MainThreadExecutor();

        serverApi = MainActivity.retrofit.create(ServerApi.class);

        String categorySelected = getIntent().getStringExtra("CATEGORY");
        String keywords = getIntent().getStringExtra("KEYWORDS");

        setupRecyclerView();
        setupSwipeRefresh(categorySelected, keywords);
        setupDataSource(categorySelected, keywords);
    }

    private void setupRecyclerView() {
        adapter = new ProductsListAdapter();

        adapter.setOnProductSelectedListener(new OnProductSelectedListener() {
            @Override
            public void onProductSelectedListened(String price, String currency_code, String title, String description, String url) {
                Intent intent = new Intent(ProductsListActivity.this, ProductSelectedActivity.class);
                intent.putExtra("PRICE", price);
                intent.putExtra("CURRENCY", currency_code);
                intent.putExtra("TITLE", title);
                intent.putExtra("DESCRIPTION", description);
                intent.putExtra("URL", url);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void setupSwipeRefresh(final String categorySelected, final String keywords) {
        swipeRefreshLayout= findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setupDataSource(categorySelected, keywords);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setupDataSource(String categorySelected, String keywords) {
        ProductDataSource dataSource = new ProductDataSource(serverApi, categorySelected, keywords);

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(25)
                .setInitialLoadSizeHint(50)
                .setEnablePlaceholders(true)
                .build();

        PagedList<Product> products = new PagedList.Builder<>(dataSource, config)
                .setFetchExecutor(executor)
                .setNotifyExecutor(executor)
                .build();

        adapter.submitList(products);

        dataSource.getRequestFailureLiveData().observe(this, new Observer<RequestFailure>() {
            @Override
            public void onChanged(@Nullable final RequestFailure requestFailure) {
                if (requestFailure == null) return;

                Snackbar.make(findViewById(android.R.id.content), requestFailure.getErrorMessage(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                requestFailure.getRetryable().retry();
                            }
                        }).show();
            }
        });
    }

}
