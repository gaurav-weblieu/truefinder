package com.weblieu.findtrue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.weblieu.findtrue.adapter.CategoryDetailsAdapter;
import com.weblieu.findtrue.model.CategoryData;
import com.weblieu.findtrue.model.CategoryDetails;
import com.weblieu.findtrue.model.Result;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDetailsActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private ImageView ivBack;
    private RecyclerView mRecyclerView;
    private CategoryDetailsAdapter mCategoryDetailsAdapter;
    private List<CategoryData> listCategoryData;

    private ApiInterface apiInterface;
    String CATEGORY_NAME;
    private ProgressBar progressBar3;
    private TextView textView14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

//        mToolBar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(mToolBar);

        ivBack = (ImageView)findViewById(R.id.ivBack);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerDetails);
        progressBar3 = (ProgressBar)findViewById(R.id.progressBar3);
        textView14 = (TextView)findViewById(R.id.textView14);
        listCategoryData = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);


        Intent intent = getIntent();
        if (intent!=null){
            CATEGORY_NAME = intent.getStringExtra("CATEGORY_NAME");
            System.out.println("CATEGORY_NAME----------------------"+CATEGORY_NAME);
            AppConstant.CATEGORY_NAME = CATEGORY_NAME;
        }
        //CATEGORY_NAME =

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        getCategoryDetails();
    }


    //create function get api call in getCategoryDetails

    private void getCategoryDetails(){
        progressBar3.setVisibility(View.VISIBLE);
        String replaceText = CATEGORY_NAME;
        String replaceCategoryName = replaceText.replace(' ', '-');
        System.out.println("rel------------------------------"+replaceCategoryName);
        //String mobile_gallery = "mobile-gallery";
        //String city = "delhi";
        System.out.println("city_name=======-------------"+AppConstant.CITY_NAME);
        //AppConstant.CITY_NAME =
        //textView14.setVisibility(View.VISIBLE);
        String city = AppConstant.CITY_NAME;
        apiInterface.getCategoryDetails(replaceCategoryName, city).enqueue(new Callback<CategoryDetails>() {
            @Override
            public void onResponse(Call<CategoryDetails> call, Response<CategoryDetails> response) {
                System.out.println("---------------------------------------+++++");
                if (response.isSuccessful()){
                    progressBar3.setVisibility(View.GONE);
                    String type = response.body().getType();
                    System.out.println("type----------------------------"+type);
                    List<CategoryData> results = response.body().getData();
                    for (CategoryData data : results){
//                        CategoryData categoryData = new CategoryData();
//                        categoryData.getAddress();
//
                        listCategoryData.add(data);
                        mCategoryDetailsAdapter = new CategoryDetailsAdapter(CategoryDetailsActivity.this, listCategoryData);
                        mRecyclerView.setAdapter(mCategoryDetailsAdapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<CategoryDetails> call, Throwable t) {
                System.out.println("--------------------------------------====-");
                progressBar3.setVisibility(View.GONE);
                textView14.setVisibility(View.VISIBLE);
            }
        });
    }
}