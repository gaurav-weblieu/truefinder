package com.weblieu.findtrue.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.weblieu.findtrue.MainActivity;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.login.LoginActivity;
import com.weblieu.findtrue.model.Login;
import com.weblieu.findtrue.model.Result;
import com.weblieu.findtrue.registation.RegistationActivity;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserLoginFragment extends Fragment {
    private TextView mTextCreateAccount, mTextViewMaybeLater;
    private RelativeLayout relativeLogin;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    private EditText editTextEmail, editTextPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextCreateAccount = (TextView)view.findViewById(R.id.tvCreateAccount);
        mTextViewMaybeLater = (TextView)view.findViewById(R.id.tvMayBeLater);
        relativeLogin = (RelativeLayout)view.findViewById(R.id.relativeLogin);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        editTextEmail = (EditText)view.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)view.findViewById(R.id.editTextPassword);

        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        LoginActivity loginActivity = new LoginActivity();

        mTextCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegistationActivity.class);
                startActivity(intent);
            }
        });


        mTextViewMaybeLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        relativeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isOnline(getContext())){
                    if (checkValidation()){
                        String email = editTextEmail.getText().toString();
                        String password = editTextPassword.getText().toString();
                        progressBar.setVisibility(View.VISIBLE);
                        System.out.println("email------------------------"+email);
                        System.out.println("password------------------------"+password);

                        apiInterface.userLogin(email, password).enqueue(new Callback<Login>() {
                            @Override
                            public void onResponse(Call<Login> call, Response<Login> response) {
                                String message2 = response.body().getMessage();
                                System.out.println("type----------------------"+response.body().getType());
                                System.out.println("messsage2----------------------------"+message2);
                                progressBar.setVisibility(View.GONE);
                                if (response.isSuccessful()){
                                    String message = response.body().getMessage();
                                    System.out.println("type----------------------"+response.body().getType());
                                    System.out.println("messsage----------------------------"+message);
                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    List<Result> results = response.body().getData();
                                    for (Result result:results){
                                        System.out.println("id-------------------------"+result.getId());
                                        System.out.println("fname-------------------------"+result.getFname());
                                        System.out.println("lname-------------------------"+result.getLname());
                                        System.out.println("email-------------------------"+result.getEmail());
                                        System.out.println("mobileNo-------------------------"+result.getMobileno());

                                        CommonUtils.savePreferenceString(getContext(), AppConstant.USER_ID, result.getId());
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.F_NAME, result.getFname());
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.L_NAME, result.getLname());
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.EMAIL, result.getEmail());
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.MOBILE_NO, result.getMobileno());

                                        CommonUtils.savePreferenceString(getContext(), AppConstant.IS_LOGGED_IN, "true");
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.IS_USERS, "user");
                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Login> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }else {
                    Toast.makeText(getContext(), "Please check your intenet connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkValidation(){
        boolean result = false;
        if (editTextEmail.getText().toString().equalsIgnoreCase("")) {
            Snackbar.make(relativeLogin, "Please Enter EmailID", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);

        }else if (editTextPassword.getText().toString().equalsIgnoreCase("")) {
            Snackbar.make(relativeLogin, "Please Enter Password", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);
        }
        else {
            return true;
        }
        return result;
    }
}