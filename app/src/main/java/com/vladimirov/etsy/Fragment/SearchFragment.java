package com.vladimirov.etsy.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.vladimirov.etsy.MainActivity;
import com.vladimirov.etsy.Model.CategoryList;
import com.vladimirov.etsy.ProductsListActivity;
import com.vladimirov.etsy.R;
import com.vladimirov.etsy.MyInterface.ServerApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private int positionSpinnerSelected;

    private EditText searchForItem;
    private Spinner spinnerCategory;
    private Button buttonSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, null);

        searchForItem = view.findViewById(R.id.searchForItem);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        ServerApi serverApi = MainActivity.retrofit.create(ServerApi.class);
        final Call<CategoryList> call = serverApi.getCategory();

        call.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                final CategoryList categoryList = response.body();

                String[] categories = new String[categoryList.getCategories().size()];

                for (int i = 0; i < categoryList.getCategories().size(); i++){
                    categories[i] = categoryList.getCategories().get(i).getLongName();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, categories);

                spinnerCategory.setAdapter(adapter);
                spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        positionSpinnerSelected = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String entryText = searchForItem.getText().toString();
                        String categorySelected = categoryList.getCategories().get(positionSpinnerSelected).getName();

                        Intent intent = new Intent(getActivity(), ProductsListActivity.class);
                        if(!entryText.isEmpty() && categorySelected != null) {
                            intent.putExtra("CATEGORY", categorySelected);
                            intent.putExtra("KEYWORDS", entryText);
                            startActivity(intent);
                        }

                    }
                });
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {

            }
        });
        return view;
    }
}