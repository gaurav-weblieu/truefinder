package com.weblieu.findtrue.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.weblieu.findtrue.R;
import com.weblieu.findtrue.SearchActivity;
import com.weblieu.findtrue.adapter.FaqAdapter;
import com.weblieu.findtrue.model.CityList;
import com.weblieu.findtrue.model.FAQData;
import com.weblieu.findtrue.model.GetFAQ;
import com.weblieu.findtrue.model.Result;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.ResponseRequest;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFAQActivity extends AppCompatActivity {

    private Context context = AddFAQActivity.this;
    private ImageView back;
    private EditText editQuestion, editAnswer;
    private RelativeLayout btnSubmit;
    String VENDOR_ID;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewFAQ;
    private FaqAdapter faqAdapter;
    private List<FAQData> listFaqList;
    private ProgressBar progressBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_f_a_q);

        back = (ImageView)findViewById(R.id.back);
        editQuestion = (EditText)findViewById(R.id.editQuestion);
        editAnswer = (EditText)findViewById(R.id.editAnswer);
        btnSubmit = (RelativeLayout) findViewById(R.id.btnSubmit);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        recyclerViewFAQ = (RecyclerView)findViewById(R.id.recyclerViewFAQ);
        progressBar3 = (ProgressBar)findViewById(R.id.progressBar3);
        listFaqList = new ArrayList<>();

        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        VENDOR_ID = CommonUtils.getPreferencesString(context, AppConstant.VENDOR_ID);
        //getFAQAnswerQuestion();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                apiInterface.updateFAQ(editQuestion.getText().toString(), editAnswer.getText().toString(), VENDOR_ID).enqueue(new Callback<ResponseRequest>() {
                    @Override
                    public void onResponse(Call<ResponseRequest> call, Response<ResponseRequest> response) {
                        if (response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Add Successfully", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseRequest> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void getFAQAnswerQuestion(){

        apiInterface.getVendorFAQ(VENDOR_ID).enqueue(new Callback<GetFAQ>() {
            @Override
            public void onResponse(Call<GetFAQ> call, Response<GetFAQ> response) {
                if (response.isSuccessful()){
                    progressBar3.setVisibility(View.GONE);
                    System.out.println("----------------------"+response.body().getMessage());
                    listFaqList.clear();
                    List<FAQData> results = response.body().getData();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    recyclerViewFAQ.setLayoutManager(linearLayoutManager);
                    listFaqList.addAll(results);
                    faqAdapter = new FaqAdapter(context, listFaqList);
                    recyclerViewFAQ.setAdapter(faqAdapter);
                }
            }

            @Override
            public void onFailure(Call<GetFAQ> call, Throwable t) {
                progressBar3.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFAQAnswerQuestion();
    }
}