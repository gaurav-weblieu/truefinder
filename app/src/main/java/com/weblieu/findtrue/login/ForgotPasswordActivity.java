package com.weblieu.findtrue.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.weblieu.findtrue.R;
import com.weblieu.findtrue.profile.ProfileActivity;
import com.weblieu.findtrue.profile.UpdateProfile;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText mEditTextPassword, mEditTextConfirmPassword;
    private RelativeLayout relativeLogin;
    private ApiInterface apiInterface;
    String USER_ID;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mEditTextPassword = (EditText)findViewById(R.id.editTextTextEmailAddress2);
        mEditTextConfirmPassword = (EditText)findViewById(R.id.editTextTextPassword2);
        relativeLogin = (RelativeLayout)findViewById(R.id.relativeLogin);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        USER_ID = CommonUtils.getPreferencesString(ForgotPasswordActivity.this, AppConstant.USER_ID);
        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);

        relativeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    progressBar.setVisibility(View.VISIBLE);
                    System.out.println("==============================validate");
                    apiInterface.updatePassword(USER_ID, mEditTextPassword.getText().toString()).enqueue(new Callback<UpdateProfile>() {
                        @Override
                        public void onResponse(Call<UpdateProfile> call, Response<UpdateProfile> response) {
                            if (response.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                System.out.println("----------------------------"+response.body().getMessage());
                                Toast.makeText(ForgotPasswordActivity.this, "Password Change Successfully", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateProfile> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }else {
                    System.out.println("==============================not validate");
                }
            }
        });

    }

    private boolean validate() {
        boolean temp=true;
        String pass=mEditTextPassword.getText().toString();
        String cpass=mEditTextConfirmPassword.getText().toString();
        if(!pass.equals(cpass)) {
            Toast.makeText(ForgotPasswordActivity.this, "Password Not matching", Toast.LENGTH_SHORT).show();
            temp = false;
        }
        return temp;
    }
}