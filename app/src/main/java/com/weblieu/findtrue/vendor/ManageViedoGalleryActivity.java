package com.weblieu.findtrue.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageViedoGalleryActivity extends AppCompatActivity {

    private Context context = ManageViedoGalleryActivity.this;
    private ImageView back;
    private EditText editYouTube;
    private RelativeLayout btnSubmit;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    String VENDOR_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_viedo_gallery);

        back = (ImageView)findViewById(R.id.back);
        editYouTube = (EditText)findViewById(R.id.editYouTube);
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
                String youTubeLink = editYouTube.getText().toString();
                apiInterface.uploadYouTubeUrl(VENDOR_ID, youTubeLink).enqueue(new Callback<ResponseRequest>() {
                    @Override
                    public void onResponse(Call<ResponseRequest> call, Response<ResponseRequest> response) {
                        if (response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Uplads successfully", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseRequest> call, Throwable t) {
                        Toast.makeText(context, "Uplads falied", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}