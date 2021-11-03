package com.weblieu.findtrue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.weblieu.findtrue.adapter.CategoryAdapter;
import com.weblieu.findtrue.adapter.HomeViewTypeAdapter;
import com.weblieu.findtrue.model.Category;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DomoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<Category> categoryItemList;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domo);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        categoryItemList = new ArrayList<>();
        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(linearLayoutManager);

        apiInterface.getCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()){
                    categoryItemList = response.body();
                    CategoryAdapter categoryAdapter = new CategoryAdapter(DomoActivity.this, categoryItemList);
                    recyclerView.setAdapter(categoryAdapter);

                    System.out.println("category+++++--------------------------" + categoryItemList);

                }

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });

    }
}