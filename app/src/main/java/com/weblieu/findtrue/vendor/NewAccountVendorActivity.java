package com.weblieu.findtrue.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.registation.RegistationActivity;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.ResponseRequest;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewAccountVendorActivity extends AppCompatActivity {
    private Context context = NewAccountVendorActivity.this;
    private EditText mEditTextBusinessName, mEditTextOwnerName, mEditTextMobileNo, mEditTextEmailID, mEditTextPassword;
    private RelativeLayout btnRelative;
    private ProgressBar progressBar;
    private ApiInterface apiInterface;
    private TextView btnSave;
    String mobile_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_vendor);

        mEditTextBusinessName = (EditText)findViewById(R.id.editTextTextPersonName3);
        mEditTextOwnerName = (EditText)findViewById(R.id.editTextTextPersonName4);
        mEditTextMobileNo = (EditText)findViewById(R.id.editTextPhone);
        mEditTextEmailID = (EditText)findViewById(R.id.editTextTextEmailAddress);
        mEditTextPassword = (EditText)findViewById(R.id.editTextTextPassword);
        btnRelative = (RelativeLayout)findViewById(R.id.btnRelative);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        btnSave = (TextView)findViewById(R.id.btnSave);

        btnRelative.setEnabled(false);
        btnRelative.setBackgroundResource(R.drawable.disable_btn);
        btnSave.setTextColor(Color.BLACK);

        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);

        mEditTextMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable mobile) {
                if (mobile.length() == 10){

                    //btnSend.setTextColor(Color.WHITE);
                    if (mEditTextMobileNo.getText().toString().matches("(0/91)?[6-9][0-9]{9}")){
                        mobile_no = mEditTextMobileNo.getText().toString();
                        System.out.println("mobile_no------------------------"+mobile_no);
                        btnRelative.setEnabled(true);
                        btnRelative.setBackgroundResource(R.drawable.btn_back);
                        btnSave.setTextColor(Color.WHITE);
                    }

                }else {
                }

            }
        });

        btnRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isOnline(context)){
                    if (checkValidation()){
                        progressBar.setVisibility(View.VISIBLE);

                        apiInterface.vendorRegister(mEditTextOwnerName.getText().toString(), mEditTextEmailID.getText().toString(), mEditTextBusinessName.getText().toString(), mobile_no, mEditTextPassword.getText().toString()).enqueue(new Callback<ResponseRequest>() {
                            @Override
                            public void onResponse(Call<ResponseRequest> call, Response<ResponseRequest> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseRequest> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(context, "User registration failed", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }else {
                    Toast.makeText(context, "Please check your intenet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private boolean checkValidation(){
        boolean result = false;
        if (mEditTextBusinessName.getText().toString().trim().equalsIgnoreCase("")) {
            Snackbar.make(btnRelative, "Please Enter Business Name", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);

        }else if (mEditTextOwnerName.getText().toString().trim().equalsIgnoreCase("")) {
            Snackbar.make(btnRelative, "Please Enter Owner Name", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);

        }else if (mEditTextMobileNo.getText().toString().trim().equalsIgnoreCase("")){
            Snackbar.make(btnRelative, "Please Enter mobile no", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);

        } else if (mEditTextEmailID.getText().toString().equalsIgnoreCase("")) {
            Snackbar.make(btnRelative, "Please Enter EmailID", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);


        }else if (mEditTextPassword.getText().toString().equalsIgnoreCase("")){
            Snackbar.make(btnRelative, "Please Enter Password", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);

        } else {

            return true;
        }
        return result;
    }
}