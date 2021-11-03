package com.weblieu.findtrue.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.weblieu.findtrue.R;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.ResponseRequest;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionActivity extends AppCompatActivity {

    private Context context = DescriptionActivity.this;
    private ImageView back;
    private EditText editTextTextPersonName2;
    private ApiInterface apiInterface;
    String VENDOR_ID;
    private RelativeLayout btnSubmit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        back = (ImageView)findViewById(R.id.back);
        editTextTextPersonName2 = (EditText)findViewById(R.id.editTextTextPersonName2);
        btnSubmit = (RelativeLayout)findViewById(R.id.btnSubmit);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        VENDOR_ID = CommonUtils.getPreferencesString(context, AppConstant.VENDOR_ID);
        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
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
                String description = editTextTextPersonName2.getText().toString();
                String str = "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>?«»“”‘’]))";
                Pattern patt = Pattern.compile(str);
                Matcher matcher = patt.matcher(description);
                description = matcher.replaceAll("<a href=\"$1\">$1</a>");
                System.out.println("description---------------------"+description);

                apiInterface.submitDescription(VENDOR_ID, description).enqueue(new Callback<ResponseRequest>() {
                    @Override
                    public void onResponse(Call<ResponseRequest> call, Response<ResponseRequest> response) {
                        if (response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Add Successfully", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseRequest> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "failed", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
}