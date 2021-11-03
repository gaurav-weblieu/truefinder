package com.weblieu.findtrue.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.weblieu.findtrue.HiringActivity;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.service.AppConfig;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class ChoosePostFragment extends Fragment {

    private AppCompatButton buttonOne, button2, button3, btnNext;

    private RadioGroup radioGroup;
    private RadioButton radioButtonOne, radioButtonTwo, radioButtonThree, radioButtonFour;
    private RelativeLayout relative10th, relative2, relativeGraduate;
    private LinearLayout linearAdhar;
    public String IMAGE_ONE;
    public String IMAGE_TWO;
    public String IMAGE_THREE;
    public String IMAGE_FOUR;
    String choosePost = "";

    private ImageView imageTenPercentage, image12Percentage, imageGraguate, ivImageAdhar;
    private EditText editSchool, editGrade, edit12th, editPercentage, editGraduate, editPercentageOne;

    public ChoosePostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonOne = (AppCompatButton)view.findViewById(R.id.buttonOne);
        button2 = (AppCompatButton)view.findViewById(R.id.button2);
        button3 = (AppCompatButton)view.findViewById(R.id.button3);
        btnNext = (AppCompatButton)view.findViewById(R.id.btnNext);
        radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup);
        radioButtonOne = (RadioButton)view.findViewById(R.id.radioButtonOne);
        radioButtonTwo = (RadioButton)view.findViewById(R.id.radioButtonTwo);
        radioButtonThree = (RadioButton)view.findViewById(R.id.radioButtonThree);
        radioButtonFour = (RadioButton)view.findViewById(R.id.radioButtonFour);
        relative10th = (RelativeLayout)view.findViewById(R.id.relative10th);
        relativeGraduate = (RelativeLayout)view.findViewById(R.id.relativeGraduate);
        linearAdhar = (LinearLayout)view.findViewById(R.id.linearAdhar);
        relative2 = (RelativeLayout)view.findViewById(R.id.relative2);

        imageTenPercentage = (ImageView)view.findViewById(R.id.imageTenPercentage);
        image12Percentage = (ImageView)view.findViewById(R.id.image12Doc);
        imageGraguate = (ImageView)view.findViewById(R.id.imageGraguate);
        ivImageAdhar = (ImageView)view.findViewById(R.id.ivImageAdhar);

        editSchool = (EditText)view.findViewById(R.id.editSchool);
        editGrade = (EditText)view.findViewById(R.id.editGrade);
        edit12th = (EditText)view.findViewById(R.id.edit12th);
        editPercentage = (EditText)view.findViewById(R.id.editPercentage);
        editGraduate = (EditText)view.findViewById(R.id.editGraduate);
        editPercentageOne = (EditText)view.findViewById(R.id.editPercentageOne);

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
                IMAGE_ONE = AppConstant.IMAGE_ONE;
                System.out.println("--------------ONE image-"+IMAGE_ONE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
                IMAGE_TWO = AppConstant.IMAGE_TWO;
                System.out.println("--------------TWO image-"+IMAGE_TWO);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
                IMAGE_THREE = AppConstant.IMAGE_THREE;
                System.out.println("--------------THREE image-"+IMAGE_THREE);
            }
        });

        ivImageAdhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
                IMAGE_FOUR = AppConstant.IMAGE_FOUR;
                System.out.println("--------------fOUR image-"+IMAGE_FOUR);
            }
        });


        //get all submit field
        System.out.println("choose post-------------"+CommonUtils.getPreferencesString(getContext(), AppConstant.CHOOSE_POST));
        System.out.println("adhaar-----------------"+CommonUtils.getPreferencesString(getContext(), AppConstant.ADHAAR_CARD));
        if (CommonUtils.getPreferencesString(getContext(), AppConstant.CHOOSE_POST).equals("Work From Home")){
            radioButtonFour.setChecked(true);
            relative10th.setVisibility(View.GONE);
            relativeGraduate.setVisibility(View.GONE);
            linearAdhar.setVisibility(View.GONE);
            relative2.setVisibility(View.GONE);
            linearAdhar.setVisibility(View.VISIBLE);
            if (CommonUtils.getPreferencesString(getContext(), AppConstant.ADHAAR_CARD).equals("")){

            }else {
                Glide.with(getContext()).load(CommonUtils.getPreferencesString(getContext(), AppConstant.ADHAAR_CARD)).into(ivImageAdhar);
            }
            //

        }else if (CommonUtils.getPreferencesString(getContext(), AppConstant.CHOOSE_POST).equals("Zonal Manager")){
            radioButtonOne.setChecked(true);
            linearAdhar.setVisibility(View.GONE);
            relative10th.setVisibility(View.VISIBLE);
            relative2.setVisibility(View.VISIBLE);
            relativeGraduate.setVisibility(View.VISIBLE);
        }else if (CommonUtils.getPreferencesString(getContext(), AppConstant.CHOOSE_POST).equals("Sales manager")){
            radioButtonTwo.setChecked(true);
            linearAdhar.setVisibility(View.GONE);
            relative10th.setVisibility(View.VISIBLE);
            relative2.setVisibility(View.VISIBLE);
            relativeGraduate.setVisibility(View.VISIBLE);
        }else if (CommonUtils.getPreferencesString(getContext(), AppConstant.CHOOSE_POST).equals("Sales executive")){
            radioButtonThree.setChecked(true);
            relativeGraduate.setVisibility(View.GONE);
            linearAdhar.setVisibility(View.GONE);
            relative10th.setVisibility(View.VISIBLE);
            relative2.setVisibility(View.VISIBLE);
            if (CommonUtils.getPreferencesString(getContext(), AppConstant.ACADEMIA_10_SCHOOL).equals("")){
                editSchool.setText("");
                editGrade.setText("");
            }else {
                editSchool.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.ACADEMIA_10_SCHOOL));
                editGrade.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.ACADEMIA_10_PERCENTAGE));
                Glide.with(getContext()).load(CommonUtils.getPreferencesString(getContext(), AppConstant.ACADEMIA_10_DOCUMENT)).into(imageTenPercentage);
            }
        }else {
            radioButtonOne.setChecked(false);
            radioButtonTwo.setChecked(false);
            radioButtonThree.setChecked(false);
            radioButtonFour.setChecked(false);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonUtils.savePreferenceString(getContext(), AppConstant.CHOOSE_POST, choosePost);
                CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_10_SCHOOL, editSchool.getText().toString());
                CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_10_PERCENTAGE, editGrade.getText().toString());
                //CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_10_DOCUMENT, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_12_SCHOOL, edit12th.getText().toString());
                CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_12_PERCENTAGE, editPercentage.getText().toString());
                //CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_12_DOCUMENT, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.GRADUATE_COLLEGE, editGraduate.getText().toString());
                CommonUtils.savePreferenceString(getContext(), AppConstant.GRADUATE_PERCENTAGE, editPercentageOne.getText().toString());
                //CommonUtils.savePreferenceString(getContext(), AppConstant.GRADUATE_DOCUMENT, "");
                //CommonUtils.savePreferenceString(getContext(), AppConstant.ADHAAR_CARD, "");

                HiringActivity.stepIndicator.setCurrentStepPosition(2);
                HiringActivity.mViewPager.setCurrentItem(2);


            }
        });



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonOne){
                    choosePost = "Zonal Manager";
                    System.out.println("choosePost--------------------------"+choosePost);
                    linearAdhar.setVisibility(View.GONE);
                    relative10th.setVisibility(View.VISIBLE);
                    relative2.setVisibility(View.VISIBLE);
                    relativeGraduate.setVisibility(View.VISIBLE);

                }else if (checkedId == R.id.radioButtonTwo){
                    choosePost = "Sales manager";
                    System.out.println("choosePost--------------------------"+choosePost);
                    linearAdhar.setVisibility(View.GONE);
                    relative10th.setVisibility(View.VISIBLE);
                    relative2.setVisibility(View.VISIBLE);
                    relativeGraduate.setVisibility(View.VISIBLE);

                }else if (checkedId == R.id.radioButtonThree){
                    choosePost = "Sales executive";
                    System.out.println("choosePost--------------------------"+choosePost);
                    relativeGraduate.setVisibility(View.GONE);
                    linearAdhar.setVisibility(View.GONE);
                    relative10th.setVisibility(View.VISIBLE);
                    relative2.setVisibility(View.VISIBLE);

                }else if (checkedId == R.id.radioButtonFour){
                    choosePost = "Work From Home";
                    System.out.println("choosePost--------------------------"+choosePost);
                    relative10th.setVisibility(View.GONE);
                    relativeGraduate.setVisibility(View.GONE);
                    linearAdhar.setVisibility(View.GONE);
                    relative2.setVisibility(View.GONE);
                    linearAdhar.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    public void onSelectImageClick(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(getContext(), this);
        // CropImage.startPickImageActivity(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                System.out.println("resultUri---------------------" + resultUri);
                if (AppConstant.IMAGE_ONE.equals(IMAGE_ONE)) {
                    imageTenPercentage.setVisibility(View.VISIBLE);
                    imageTenPercentage.setImageURI(resultUri);
                    CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_10_DOCUMENT, String.valueOf(resultUri));
                    AppConstant.IMAGE_ONE = "";


                }
                if (AppConstant.IMAGE_TWO.equals(IMAGE_TWO)) {
                    image12Percentage.setVisibility(View.VISIBLE);
                    image12Percentage.setImageURI(resultUri);
                    CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_12_DOCUMENT, String.valueOf(resultUri));
                    System.out.println("image22222222222222222222222222222");
                    //AppConstant.IMAGE_TWO = "";

                }
                if (AppConstant.IMAGE_THREE.equals(IMAGE_THREE)) {
                    imageGraguate.setImageURI(resultUri);
                    imageGraguate.setVisibility(View.VISIBLE);
                    CommonUtils.savePreferenceString(getContext(), AppConstant.GRADUATE_DOCUMENT, String.valueOf(resultUri));
                    System.out.println("image33333333333333333333333333333");
                    AppConstant.IMAGE_THREE = "";
                }
                if (AppConstant.IMAGE_FOUR.equals(IMAGE_FOUR)){
                    ivImageAdhar.setVisibility(View.VISIBLE);
                    imageGraguate.setImageURI(resultUri);
                    CommonUtils.savePreferenceString(getContext(), AppConstant.ADHAAR_CARD, String.valueOf(resultUri));
                    AppConstant.IMAGE_FOUR = "";
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}