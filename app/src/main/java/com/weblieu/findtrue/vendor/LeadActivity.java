package com.weblieu.findtrue.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.weblieu.findtrue.MainActivity;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.adapter.CategoryAdapter;
import com.weblieu.findtrue.adapter.LeadTableViewAdapter;
import com.weblieu.findtrue.model.GetUserLead;
import com.weblieu.findtrue.model.UserLeads;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadActivity extends AppCompatActivity {

    private Context context = LeadActivity.this;
    private RecyclerView mRecyclerViewLead;
    private List<UserLeads> leadList;
    private ApiInterface apiInterface;
    String VENDOR_ID;
    private ProgressBar progressBar;
    private ImageView back, imageViewIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        mRecyclerViewLead = (RecyclerView)findViewById(R.id.recyclerViewLeadList);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        imageViewIcons = (ImageView)findViewById(R.id.imageViewIcons);
        back = (ImageView)findViewById(R.id.back);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewLead.setLayoutManager(linearLayoutManager);

        leadList = new ArrayList<>();
        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        VENDOR_ID = CommonUtils.getPreferencesString(context, AppConstant.VENDOR_ID);
        System.out.println("VENDOR_ID================================"+VENDOR_ID);
        progressBar.setVisibility(View.VISIBLE);

        getAllLeads();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void getAllLeads(){
        apiInterface.getUserLeads(VENDOR_ID).enqueue(new Callback<GetUserLead>() {
            @Override
            public void onResponse(Call<GetUserLead> call, Response<GetUserLead> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    System.out.println("all leads-------------------------------"+response.body().getData());
                    leadList.add(response.body().getData());
                    LeadTableViewAdapter leadTableViewAdapter = new LeadTableViewAdapter(LeadActivity.this, leadList);
                    mRecyclerViewLead.setAdapter(leadTableViewAdapter);

                }
            }

            @Override
            public void onFailure(Call<GetUserLead> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                imageViewIcons.setVisibility(View.VISIBLE);
            }
        });
    }
}