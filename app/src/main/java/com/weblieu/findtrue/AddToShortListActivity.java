package com.weblieu.findtrue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.weblieu.findtrue.adapter.AddShortListAdapter;
import com.weblieu.findtrue.adapter.CategoryDetailsAdapter;
import com.weblieu.findtrue.model.AddShortList;
import com.weblieu.findtrue.model.CategoryData;
import com.weblieu.findtrue.model.WishlistData;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToShortListActivity extends AppCompatActivity {

    private Context context = AddToShortListActivity.this;
    private RecyclerView mRecyclerView;
    private ApiInterface apiInterface;
    String USER_ID;
    private AddShortListAdapter addShortListAdapter;
    private List<WishlistData> listWishList;
    private ImageView ivBackScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_short_list);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerViewAddWishlist);
        ivBackScreen = (ImageView)findViewById(R.id.ivBackScreen);
        listWishList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        USER_ID = CommonUtils.getPreferencesString(context, AppConstant.USER_ID);



        apiInterface.getShortList(USER_ID).enqueue(new Callback<AddShortList>() {
            @Override
            public void onResponse(Call<AddShortList> call, Response<AddShortList> response) {
                System.out.println("id============================="+USER_ID);
                if (response.body().getType().equals("success")){
                    System.out.println("message----------------------"+response.body().getMessage());
                    List<WishlistData> results = response.body().getData();
                    for (WishlistData data : results){
                        listWishList.add(data);
                        addShortListAdapter = new AddShortListAdapter(AddToShortListActivity.this, listWishList);
                        mRecyclerView.setAdapter(addShortListAdapter);
                    }

                }else {
                    System.out.println("message----------------------"+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<AddShortList> call, Throwable t) {

            }
        });

        ivBackScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}