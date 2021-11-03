package com.weblieu.findtrue.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.weblieu.findtrue.R;
import com.weblieu.findtrue.adapter.FaqAdapter;
import com.weblieu.findtrue.adapter.PackageAdapter;
import com.weblieu.findtrue.model.FAQData;
import com.weblieu.findtrue.model.GetPackage;
import com.weblieu.findtrue.model.PackageDetails;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageActivity extends AppCompatActivity {

    private Context context = PackageActivity.this;
    private ImageView back;
    private RecyclerView recyclerPackage;
    private PackageAdapter packageAdapter;
    List<PackageDetails> listPackageDetails;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        back = (ImageView)findViewById(R.id.back);
        recyclerPackage = (RecyclerView)findViewById(R.id.recyclerPackage);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        recyclerPackage.setLayoutManager(linearLayoutManager);
        listPackageDetails = new ArrayList<>();

        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        getPackage();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getPackage(){
        apiInterface.getPackage().enqueue(new Callback<GetPackage>() {
            @Override
            public void onResponse(Call<GetPackage> call, Response<GetPackage> response) {
                if (response.isSuccessful()){
                    List<PackageDetails> results = response.body().getData();
                    listPackageDetails.addAll(results);
                    packageAdapter = new PackageAdapter(context, listPackageDetails);
                    recyclerPackage.setAdapter(packageAdapter);
                }
            }

            @Override
            public void onFailure(Call<GetPackage> call, Throwable t) {

            }
        });

    }
}