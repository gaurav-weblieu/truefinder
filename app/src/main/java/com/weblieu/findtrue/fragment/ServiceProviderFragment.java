package com.weblieu.findtrue.fragment;

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
import com.weblieu.findtrue.model.Login;
import com.weblieu.findtrue.model.LoginVendor;
import com.weblieu.findtrue.model.Result;
import com.weblieu.findtrue.model.VendorLogin;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;
import com.weblieu.findtrue.vendor.NewAccountVendorActivity;
import com.weblieu.findtrue.vendor.VendorMainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProviderFragment extends Fragment {

    private TextView mTextViewNewAccount;
    private RelativeLayout btnRelative;
    private EditText mEditTextEmail, mEditTextPassword;
    private ProgressBar progressBar;
    private ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_provider, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextViewNewAccount = (TextView)view.findViewById(R.id.textViewVendorAccount);
        btnRelative = (RelativeLayout)view.findViewById(R.id.relativeLogin);
        mEditTextEmail = (EditText)view.findViewById(R.id.editTextVendorEmail);
        mEditTextPassword = (EditText)view.findViewById(R.id.editTextVendorPassword);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        mTextViewNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewAccountVendorActivity.class);
                startActivity(intent);
            }
        });

        btnRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isOnline(getContext())){
                    if (checkValidation()){
                        if (checkValidation()){
                            String email = mEditTextEmail.getText().toString();
                            String password = mEditTextPassword.getText().toString();
                            progressBar.setVisibility(View.VISIBLE);
                            System.out.println("email------------------------"+email);
                            System.out.println("password------------------------"+password);

                            apiInterface.vendorLogin(email, password).enqueue(new Callback<VendorLogin>() {
                                @Override
                                public void onResponse(Call<VendorLogin> call, Response<VendorLogin> response) {
                                    String message2 = response.body().getMessage();
                                    System.out.println("type----------------------"+response.body().getType());
                                    System.out.println("messsage2----------------------------"+message2);
                                    progressBar.setVisibility(View.GONE);
                                    if (response.isSuccessful()){
                                        String message = response.body().getMessage();
                                        System.out.println("type----------------------"+response.body().getType());
                                        System.out.println("messsage----------------------------"+message);
                                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                        String vendorName = response.body().getData().getVendorName();
                                        System.out.println("vendorName-------------------------"+vendorName);

                                        CommonUtils.savePreferenceString(getContext(), AppConstant.VENDOR_ID, response.body().getData().getId());
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.VENDOR_NAME, response.body().getData().getVendorName());
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.OWNER_NAME, response.body().getData().getName());
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.CATEGORY_ID, response.body().getData().getCategoryId());
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.SUB_CATEGORY_ID, response.body().getData().getSubCategoryId());
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.VENDOR_EMAIL, response.body().getData().getVendorEmail());
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.VENDOR_CONTACT_NO, response.body().getData().getContactno());

                                        CommonUtils.savePreferenceString(getContext(), AppConstant.PROFILE_STATUSE, response.body().getData().getProfileVerifiedStatus());
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.PROFILE_VERIFIED, response.body().getData().getProfileVerifiedbyAdminid());
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.PROFILE_VERIFIED_TIME, response.body().getData().getProfileVerifiedTime());

                                        CommonUtils.savePreferenceString(getContext(), AppConstant.IS_LOGGED_IN, "true");
                                        CommonUtils.savePreferenceString(getContext(), AppConstant.IS_USERS, "vendor");
                                        Intent intent = new Intent(getContext(), VendorMainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();

                                    }
                                }

                                @Override
                                public void onFailure(Call<VendorLogin> call, Throwable t) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                }else {
                    Toast.makeText(getContext(), "Please check your intenet connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkValidation(){
        boolean result = false;
        if (mEditTextEmail.getText().toString().equalsIgnoreCase("")) {
            Snackbar.make(btnRelative, "Please Enter EmailID", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);

        }else if (mEditTextPassword.getText().toString().equalsIgnoreCase("")) {
            Snackbar.make(btnRelative, "Please Enter Password", Snackbar.LENGTH_SHORT).show();
            result = false;
            progressBar.setVisibility(View.INVISIBLE);
        }
        else {
            return true;
        }
        return result;
    }
}