package com.weblieu.findtrue.fragment;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.layer_net.stepindicator.StepIndicator;
import com.weblieu.findtrue.HiringActivity;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.util.Calendar;

public class PersonalDetailsFragment extends Fragment {

    private AppCompatButton btnNext;
    private ImageView imageDate;
    StringBuffer strBuf;
    String date1;

    private TextInputEditText editFirstName, editLastName, editMotherName, editFatherName, editAddress, editAdharNumber, editEmailid, editContactNumber, editTextPrice, editTextDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNext = (AppCompatButton)view.findViewById(R.id.btnNext);
        imageDate = (ImageView) view.findViewById(R.id.imageDate);

        editFirstName = (TextInputEditText)view.findViewById(R.id.editFirstName);
        editLastName = (TextInputEditText)view.findViewById(R.id.editLastName);
        editMotherName = (TextInputEditText)view.findViewById(R.id.editMotherName);
        editFatherName = (TextInputEditText)view.findViewById(R.id.editFatherName);
        editAddress = (TextInputEditText)view.findViewById(R.id.editAddress);
        editAdharNumber = (TextInputEditText)view.findViewById(R.id.editAdharNumber);
        editEmailid = (TextInputEditText)view.findViewById(R.id.editEmailid);
        editContactNumber = (TextInputEditText)view.findViewById(R.id.editContactNumber);
        editTextPrice = (TextInputEditText)view.findViewById(R.id.editPrice);
        editTextDate = (TextInputEditText)view.findViewById(R.id.editTextDate);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()){
                    CommonUtils.savePreferenceString(getContext(), AppConstant.FIRST_NAME, editFirstName.getText().toString());
                    CommonUtils.savePreferenceString(getContext(), AppConstant.LAST_NAME, editLastName.getText().toString());
                    CommonUtils.savePreferenceString(getContext(), AppConstant.MOTHER_NAME, editMotherName.getText().toString());
                    CommonUtils.savePreferenceString(getContext(), AppConstant.FATHER_NAME, editFatherName.getText().toString());
                    CommonUtils.savePreferenceString(getContext(), AppConstant.DATE, editTextDate.getText().toString());
                    CommonUtils.savePreferenceString(getContext(), AppConstant.ADDRESS, editAddress.getText().toString());
                    CommonUtils.savePreferenceString(getContext(), AppConstant.ADHAR_NUMBER, editAdharNumber.getText().toString());
                    CommonUtils.savePreferenceString(getContext(), AppConstant.EMAIL_ID, editEmailid.getText().toString());
                    CommonUtils.savePreferenceString(getContext(), AppConstant.CONTACT_NUMBER, editContactNumber.getText().toString());
                    CommonUtils.savePreferenceString(getContext(), AppConstant.REGISTRATION_FEE, editTextPrice.getText().toString());
                    HiringActivity.stepIndicator.setCurrentStepPosition(1);
                    HiringActivity.mViewPager.setCurrentItem(1);

                }
            }
        });

        //previes data get on fragment
        editFirstName.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.FIRST_NAME));
        editLastName.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.LAST_NAME));
        editMotherName.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.MOTHER_NAME));
        editFatherName.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.FATHER_NAME));
        editTextDate.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.DATE));
        editAddress.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.ADDRESS));
        editAdharNumber.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.ADHAR_NUMBER));
        editEmailid.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.EMAIL_ID));
        editContactNumber.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.CONTACT_NUMBER));
        //editTextPrice.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.REGISTRATION_FEE));


        imageDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int dd = cldr.get(Calendar.DAY_OF_MONTH);
                int mm = cldr.get(Calendar.MONTH);
                int yy = cldr.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        strBuf = new StringBuffer();
                        strBuf.append(year);//day
                        strBuf.append("-");
                        strBuf.append(month + 1);
                        strBuf.append("-");
                        strBuf.append(dayOfMonth);
                        date1 = strBuf.toString();
                        //System.out.println("date2------------------" + date1);
                        editTextDate.setText(strBuf.toString());
                    }
                }, yy, mm, dd);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    private boolean checkValidation(){
        boolean result = false;
        if (editFirstName.getText().toString().trim().equalsIgnoreCase("")) {
            Snackbar.make(btnNext, "Please Enter First Name", Snackbar.LENGTH_SHORT).show();
            result = false;

        }else if (editLastName.getText().toString().trim().equalsIgnoreCase("")) {
            Snackbar.make(btnNext, "Please Enter Last Name", Snackbar.LENGTH_SHORT).show();
            result = false;

        }else if (editMotherName.getText().toString().trim().equalsIgnoreCase("")) {
            Snackbar.make(btnNext, "Please Enter mother name", Snackbar.LENGTH_SHORT).show();
            result = false;

        }else if (editTextDate.getText().toString().trim().equalsIgnoreCase("")){
            Snackbar.make(btnNext, "Please Select Date", Snackbar.LENGTH_SHORT).show();
            result = false;

        } else if (editFatherName.getText().toString().trim().equalsIgnoreCase("")) {
            Snackbar.make(btnNext, "Please Enter Father Name", Snackbar.LENGTH_SHORT).show();
            result = false;


        }else if (editAddress.getText().toString().trim().equalsIgnoreCase("")){
            Snackbar.make(btnNext, "Please Enter Address", Snackbar.LENGTH_SHORT).show();
            result = false;

        }else if (editAdharNumber.getText().toString().trim().equalsIgnoreCase("")) {
            Snackbar.make(btnNext, "Please Enter Adhar Number", Snackbar.LENGTH_SHORT).show();
            result = false;

        }else if (editEmailid.getText().toString().trim().equalsIgnoreCase("")) {
            Snackbar.make(btnNext, "Please Enter Email ID", Snackbar.LENGTH_SHORT).show();
            result = false;

        }else if (editContactNumber.getText().toString().trim().equalsIgnoreCase("")) {
            Snackbar.make(btnNext, "Please Enter Contact Number", Snackbar.LENGTH_SHORT).show();
            result = false;

        }else if (editTextPrice.getText().toString().trim().equalsIgnoreCase("")){
            Snackbar.make(btnNext, "Please Enter Price", Snackbar.LENGTH_SHORT).show();
            result = false;

        } else {

            return true;
        }
        return result;
    }
}