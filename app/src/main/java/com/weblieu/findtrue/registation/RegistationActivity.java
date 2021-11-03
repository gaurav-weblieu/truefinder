package com.weblieu.findtrue.registation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.login.LoginActivity;
import com.weblieu.findtrue.model.MessageResponse;
import com.weblieu.findtrue.model.Result;
import com.weblieu.findtrue.model.UserRegistration;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistationActivity extends AppCompatActivity {

    private EditText mFirstName, mLastName, mMobileNumber, mEmailID, mPassword;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private TextView btnSubmit;
    private RelativeLayout btnRelative;
    private ProgressBar progressBar;
    String gender;
    String mobile_no;
    private TextView btnSave;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        initID();
        btnRelative.setEnabled(false);
        btnRelative.setBackgroundResource(R.drawable.disable_btn);
        btnSave.setTextColor(Color.BLACK);

        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
    }

    @SuppressLint("ResourceAsColor")
    private void initID(){
        mFirstName = (EditText)findViewById(R.id.editTextTextPersonName3);
        mLastName = (EditText)findViewById(R.id.editTextTextPersonName4);
        mMobileNumber = (EditText)findViewById(R.id.editTextPhone);
        mEmailID = (EditText)findViewById(R.id.editTextTextEmailAddress);
        mPassword = (EditText)findViewById(R.id.editTextTextPassword);
        rgGender = (RadioGroup)findViewById(R.id.rgGroup);
        rbMale = (RadioButton)findViewById(R.id.rbMale);
        rbFemale = (RadioButton)findViewById(R.id.rbFemale);
        btnSubmit = (TextView) findViewById(R.id.button);
        btnRelative = (RelativeLayout)findViewById(R.id.btnRelative);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        btnSave = (TextView)findViewById(R.id.btnSave);

        mMobileNumber.addTextChangedListener(new TextWatcher() {
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
                    if (mMobileNumber.getText().toString().matches("(0/91)?[6-9][0-9]{9}")){
                        mobile_no = mMobileNumber.getText().toString();
                        System.out.println("mobile_no------------------------"+mobile_no);
                        btnRelative.setEnabled(true);
                        btnRelative.setBackgroundResource(R.drawable.btn_back);
                        btnSave.setTextColor(Color.WHITE);
                    }

                }else {

                }

            }
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbMale) {
                    gender = "Male";
                    System.out.println("gender------------------------"+gender);
                }else if (checkedId == R.id.rbFemale) {
                    gender = "Female";
                    System.out.println("gender------------------------" + gender);
                }
            }
        });

        btnRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isOnline(RegistationActivity.this)){
                    if (checkValidation()){
                    UserRegistration userRegistration = new UserRegistration();
                    userRegistration.setFname(mFirstName.getText().toString());
                    userRegistration.setLname(mLastName.getText().toString());
                    userRegistration.setMobileno(mobile_no);
                    userRegistration.setEmail(mEmailID.getText().toString());
                    userRegistration.setPassword(mPassword.getText().toString());
                    userRegistration.setGender(gender);
                    progressBar.setVisibility(View.VISIBLE);

                    apiInterface.userRegistration(userRegistration).enqueue(new Callback<UserRegistration>() {
                        @Override
                        public void onResponse(Call<UserRegistration> call, Response<UserRegistration> response) {
                            if (response.isSuccessful()){
                                System.out.println("response----------------------");
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegistationActivity.this, "User registration successfully", Toast.LENGTH_LONG).show();
                                finish();

                            }
                        }

                        @Override
                        public void onFailure(Call<UserRegistration> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegistationActivity.this, "User registration failed", Toast.LENGTH_LONG).show();
                        }
                    });

                    }
                }else {
                    Toast.makeText(RegistationActivity.this, "Please check your intenet connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkValidation(){
        boolean result = false;
        if (mFirstName.getText().toString().trim().equalsIgnoreCase("")) {
            Snackbar.make(btnRelative, "Please Enter First Name", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);

        }else if (mLastName.getText().toString().trim().equalsIgnoreCase("")) {
            Snackbar.make(btnRelative, "Please Enter Last Name", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);

        }else if (mMobileNumber.getText().toString().trim().equalsIgnoreCase("")){
            Snackbar.make(btnRelative, "Please Enter mobile no", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);

        } else if (mEmailID.getText().toString().equalsIgnoreCase("")) {
            Snackbar.make(btnRelative, "Please Enter EmailID", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);


        }else if (mPassword.getText().toString().equalsIgnoreCase("")){
            Snackbar.make(btnRelative, "Please Enter Password", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);

        }else if (rgGender.getCheckedRadioButtonId()==-1){
            Snackbar.make(btnRelative, "Please select gender", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);

        } else {

            return true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(RegistationActivity.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
    }
}