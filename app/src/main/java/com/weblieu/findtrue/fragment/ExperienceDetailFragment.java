package com.weblieu.findtrue.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.marcoscg.dialogsheet.DialogSheet;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.weblieu.findtrue.HiringActivity;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class ExperienceDetailFragment extends Fragment {

    private CheckBox mCheckBox;
    private TextInputEditText editCompanyName, editJobProfile, editExperience, editFax, editApply, editLastCompany, editSummary;
    private RadioGroup groupRelocate;
    private RadioButton radioButtonYes, radioButtonNo, radioButtonNotSure;
    String radioMessage = "";
    private ImageView ivProfileImage, ivSignature;
    public String IMAGE_ONE;
    public String IMAGE_TWO;
    private RelativeLayout btnRelativePay;
    private ProgressBar progressBar;
    Uri resultUri;
    private ApiInterface apiInterface;
    InputStream isTenImage, is12Image, isGraduateImage, isAdhaarImage, isphotoImage, isSignatureImage;
    MultipartBody.Part body1, body22, body33, body44, body55, body66;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_experience_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCheckBox = (CheckBox)view.findViewById(R.id.mCheckBox);
        editCompanyName = (TextInputEditText)view.findViewById(R.id.editCompanyName);
        editJobProfile = (TextInputEditText)view.findViewById(R.id.editJobProfile);
        editExperience = (TextInputEditText)view.findViewById(R.id.editExperience);
        editFax = (TextInputEditText)view.findViewById(R.id.editFax);
        editApply = (TextInputEditText)view.findViewById(R.id.editApply);
        editLastCompany = (TextInputEditText)view.findViewById(R.id.editLastCompany);
        editSummary = (TextInputEditText)view.findViewById(R.id.editSummary);

        groupRelocate = (RadioGroup)view.findViewById(R.id.groupRelocate);
        radioButtonYes = (RadioButton)view.findViewById(R.id.radioButtonYes);
        radioButtonNo = (RadioButton)view.findViewById(R.id.radioButtonNo);
        radioButtonNotSure = (RadioButton)view.findViewById(R.id.radioButtonNotSure);

        ivProfileImage = (ImageView)view.findViewById(R.id.ivProfileImage);
        ivSignature = (ImageView)view.findViewById(R.id.ivSignature);
        btnRelativePay = (RelativeLayout)view.findViewById(R.id.btnRelativePay);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        groupRelocate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonYes){
                    radioMessage = "Yes";
                    System.out.println("---------------------------"+radioMessage);
                }else if (checkedId == R.id.radioButtonNo){
                    radioMessage = "No";
                    System.out.println("---------------------------"+radioMessage);
                }else if (checkedId == R.id.radioButtonNotSure){
                    radioMessage = "Not/Sure";
                    System.out.println("---------------------------"+radioMessage);
                }

            }
        });
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    DialogSheet dialogSheet = new DialogSheet(getContext(), true);
                    View view = View.inflate(getContext(), R.layout.term_dialog, null);
                    dialogSheet.setView(view);
                    dialogSheet.show();

                    ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
                    AppCompatButton btnExit = (AppCompatButton) view.findViewById(R.id.btnExit);

                    ivClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogSheet.dismiss();
                        }
                    });

                    btnExit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogSheet.dismiss();
                        }
                    });
                }
            }
        });

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
                IMAGE_ONE = AppConstant.IMAGE_ONE;
                System.out.println("--------------ONE image-"+IMAGE_ONE);
            }
        });

        ivSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
                IMAGE_TWO = AppConstant.IMAGE_TWO;
                System.out.println("--------------TWO image-"+IMAGE_TWO);
            }
        });

        //GET ALL FIELD IN FILL UP FORM
        editCompanyName.setText(CommonUtils.getPreferencesString(getContext(),  AppConstant.COMPANY_NAME));
        editJobProfile.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.JOB_PROFILE));
        editExperience.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.EXPERIENCE));
        editFax.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.FAX));
        editApply.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.POSITION_APPLY));
        if (CommonUtils.getPreferencesString(getContext(), AppConstant.POSITION_APPLY).equals("Yes")){
            radioButtonYes.setChecked(true);
        }else if (CommonUtils.getPreferencesString(getContext(), AppConstant.POSITION_APPLY).equals("No")){
            radioButtonNo.setChecked(true);
        }else if (CommonUtils.getPreferencesString(getContext(), AppConstant.POSITION_APPLY).equals("Not/Sure")){
            radioButtonNotSure.setChecked(true);
        }else {
            radioButtonYes.setChecked(false);
            radioButtonNo.setChecked(false);
            radioButtonNotSure.setChecked(false);
        }
        editLastCompany.setText(CommonUtils.getPreferencesString(getContext(), AppConstant.LAST_COMPANY));
        if (CommonUtils.getPreferencesString(getContext(), AppConstant.PHOTO_GRAPH).equals("")){

        }else {
            Glide.with(getContext()).load(CommonUtils.getPreferencesString(getContext(), AppConstant.PHOTO_GRAPH)).into(ivProfileImage);
        }

        if (CommonUtils.getPreferencesString(getContext(), AppConstant.SIGNATURE).equals("")){
            Glide.with(getContext()).load(CommonUtils.getPreferencesString(getContext(), AppConstant.SIGNATURE)).into(ivSignature);
        }
        btnRelativePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.savePreferenceString(getContext(), AppConstant.COMPANY_NAME, editCompanyName.getText().toString());
                CommonUtils.savePreferenceString(getContext(), AppConstant.JOB_PROFILE, editJobProfile.getText().toString());
                CommonUtils.savePreferenceString(getContext(), AppConstant.EXPERIENCE, editExperience.getText().toString());
                CommonUtils.savePreferenceString(getContext(), AppConstant.FAX, editFax.getText().toString());
                CommonUtils.savePreferenceString(getContext(), AppConstant.POSITION_APPLY, editApply.getText().toString());
                CommonUtils.savePreferenceString(getContext(), AppConstant.RELOCATE, radioMessage);
                CommonUtils.savePreferenceString(getContext(), AppConstant.LAST_COMPANY, editLastCompany.getText().toString());

                String fName = CommonUtils.getPreferencesString(getContext(), AppConstant.FIRST_NAME);
                String lName = CommonUtils.getPreferencesString(getContext(), AppConstant.LAST_NAME);
                String motherName = CommonUtils.getPreferencesString(getContext(), AppConstant.MOTHER_NAME);
                String fatherName = CommonUtils.getPreferencesString(getContext(), AppConstant.FATHER_NAME);
                String date = CommonUtils.getPreferencesString(getContext(), AppConstant.DATE);
                String address = CommonUtils.getPreferencesString(getContext(), AppConstant.ADDRESS);
                String adhaarNo = CommonUtils.getPreferencesString(getContext(), AppConstant.ADHAR_NUMBER);
                String email = CommonUtils.getPreferencesString(getContext(), AppConstant.EMAIL_ID);
                String contactNo = CommonUtils.getPreferencesString(getContext(), AppConstant.CONTACT_NUMBER);
                String regFee = CommonUtils.getPreferencesString(getContext(), AppConstant.REGISTRATION_FEE);
                String choosePost = CommonUtils.getPreferencesString(getContext(), AppConstant.CHOOSE_POST);
                String ACADEMIA_10_SCHOOL = CommonUtils.getPreferencesString(getContext(), AppConstant.ACADEMIA_10_SCHOOL);
                String ACADEMIA_10_PERCENTAGE = CommonUtils.getPreferencesString(getContext(), AppConstant.ACADEMIA_10_PERCENTAGE);
                String ten_image = CommonUtils.getPreferencesString(getContext(), AppConstant.ACADEMIA_10_DOCUMENT);

                if (ten_image.equals("")){

                }else {
                    try {
                        Uri myUri = Uri.parse(ten_image);
                        isTenImage = getContext().getContentResolver().openInputStream(myUri);
                        System.out.println("isTenImage-----------------------------------"+isTenImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                String ACADEMIA_12_SCHOOL = CommonUtils.getPreferencesString(getContext(), AppConstant.ACADEMIA_12_SCHOOL);
                String ACADEMIA_12_PERCENTAGE = CommonUtils.getPreferencesString(getContext(), AppConstant.ACADEMIA_12_PERCENTAGE);
                String ACADEMIA_12_DOCUMENT = CommonUtils.getPreferencesString(getContext(), AppConstant.ACADEMIA_12_DOCUMENT);

                if (ACADEMIA_12_DOCUMENT.equals("")){

                }else {
                    try {
                        Uri myUri2 = Uri.parse(ACADEMIA_12_DOCUMENT);
                        is12Image = getContext().getContentResolver().openInputStream(myUri2);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                String GRADUATE_COLLEGE = CommonUtils.getPreferencesString(getContext(), AppConstant.GRADUATE_COLLEGE);
                String GRADUATE_PERCENTAGE = CommonUtils.getPreferencesString(getContext(), AppConstant.GRADUATE_PERCENTAGE);
                String GRADUATE_DOCUMENT = CommonUtils.getPreferencesString(getContext(), AppConstant.GRADUATE_DOCUMENT);
                if (GRADUATE_DOCUMENT.equals("")){

                }else {
                    try {
                        Uri myUri3 = Uri.parse(GRADUATE_DOCUMENT);
                        isGraduateImage = getContext().getContentResolver().openInputStream(myUri3);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                String ADHAAR_CARD = CommonUtils.getPreferencesString(getContext(), AppConstant.ADHAAR_CARD);
                if (ADHAAR_CARD.equals("")){

                }else {
                    try {
                        Uri myUri4 = Uri.parse(ADHAAR_CARD);
                        isAdhaarImage = getContext().getContentResolver().openInputStream(myUri4);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



                System.out.println("FISTNAME-------------------------------"+fName);
                System.out.println("LASTNAME-------------------------------"+lName);
                System.out.println("MOTHER_NAME-------------------------------"+motherName);
                System.out.println("FATHER_NAME-------------------------------"+fatherName);
                System.out.println("date--------------------------------------"+date);
                System.out.println("address--------------------------------------"+address);
                System.out.println("ADHAR_NUMBER-------------------------------"+adhaarNo);
                System.out.println("EMAIL_ID-------------------------------"+email);
                System.out.println("contactNo-------------------------------"+contactNo);
                System.out.println("REGISTRATION_FEE-------------------------------"+regFee);
                System.out.println("CHOOSE_POST-------------------------------"+choosePost);
                System.out.println("ACADEMIA_10_SCHOOL-------------------------------"+ACADEMIA_10_SCHOOL);
                System.out.println("ACADEMIA_10_PERCENTAGE-------------------------------"+ACADEMIA_10_PERCENTAGE);
                System.out.println("ACADEMIA_10_DOCUMENT-------------------------------"+ten_image);
                System.out.println("ACADEMIA_12_SCHOOL-------------------------------"+ACADEMIA_12_SCHOOL);
                System.out.println("ACADEMIA_12_PERCENTAGE-------------------------------"+ACADEMIA_12_PERCENTAGE);
                System.out.println("ACADEMIA_12_DOCUMENT-------------------------------"+ACADEMIA_12_DOCUMENT);
                System.out.println("GRADUATE_COLLEGE-------------------------------"+GRADUATE_COLLEGE);
                System.out.println("GRADUATE_PERCENTAGE-------------------------------"+GRADUATE_PERCENTAGE);
                System.out.println("GRADUATE_DOCUMENT-------------------------------"+GRADUATE_DOCUMENT);
                System.out.println("ADHAAR_CARD-------------------------------"+ADHAAR_CARD);

                System.out.println("companyname------------------------------"+editCompanyName.getText().toString());
                System.out.println("jobprofile------------------------------"+editJobProfile.getText().toString());
                System.out.println("experience------------------------------"+editExperience.getText().toString());
                System.out.println("fax------------------------------"+editFax.getText().toString());
                System.out.println("apply------------------------------"+editApply.getText().toString());
                System.out.println("relocate------------------------------"+radioMessage);
                System.out.println("lastcompany------------------------------"+editLastCompany.getText().toString());
                String photo = CommonUtils.getPreferencesString(getContext(), AppConstant.PHOTO_GRAPH);

                if (photo.equals("")){

                }else {
                    try {
                        Uri uriPhoto = Uri.parse(photo);
                        isphotoImage = getContext().getContentResolver().openInputStream(uriPhoto);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                String signature = CommonUtils.getPreferencesString(getContext(), AppConstant.SIGNATURE);
                if (signature.equals("")){

                }else {
                    try {
                        Uri uriSignature = Uri.parse(signature);
                        isSignatureImage = getContext().getContentResolver().openInputStream(uriSignature);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("photoImage------------------------------"+photo);
                System.out.println("signature------------------------------"+signature);
                System.out.println("comment------------------------------"+editSummary.getText().toString());

                Random r = new Random();
                int randomNumber = r.nextInt(999999);
                System.out.println("---------------------------------------------"+randomNumber);

                progressBar.setVisibility(View.VISIBLE);
                try {
                    uploadFile(fName, lName, motherName, fatherName, date, address, adhaarNo, email, contactNo, regFee, ACADEMIA_10_SCHOOL, ACADEMIA_10_PERCENTAGE, getBytes(isTenImage), ACADEMIA_12_SCHOOL, ACADEMIA_12_PERCENTAGE, getBytes(is12Image), GRADUATE_COLLEGE, GRADUATE_PERCENTAGE, getBytes(isGraduateImage), getBytes(isAdhaarImage), editCompanyName.getText().toString(), editJobProfile.getText().toString(), editExperience.getText().toString(), editApply.getText().toString(), editFax.getText().toString(), radioMessage, editLastCompany.getText().toString(), getBytes(isphotoImage), getBytes(isSignatureImage), editSummary.getText().toString(), String.valueOf(randomNumber), choosePost);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void uploadFile(String first_name, String last_name, String mother_name, String father_name, String date, String address, String adar_number, String email, String contact_number, String fees, String ten_school, String ten_percentage, byte[] ten_imageBytes, String twelve_school, String twelve_percentage, byte[] twelve_imageBytes, String ug_school, String ug_percentage, byte[] ug_imageBytes, byte[] adar_cardBytes, String company_name, String job_profile, String experince, String position, String fax, String relocate, String last_company, byte[] photographBytes, byte[] signatureBytes, String comment, String orderId, String choosepost){

        RequestBody idBodyfname = RequestBody.create(okhttp3.MultipartBody.FORM, first_name);
        RequestBody idBodylname = RequestBody.create(okhttp3.MultipartBody.FORM, last_name);
        RequestBody idBodymname = RequestBody.create(okhttp3.MultipartBody.FORM, mother_name);
        RequestBody idBodyfathername = RequestBody.create(okhttp3.MultipartBody.FORM, father_name);
        RequestBody idBodydate = RequestBody.create(okhttp3.MultipartBody.FORM, date);
        RequestBody idBodyaddress = RequestBody.create(okhttp3.MultipartBody.FORM, address);
        RequestBody idBodyadno = RequestBody.create(okhttp3.MultipartBody.FORM, adar_number);
        RequestBody idBodyemail = RequestBody.create(okhttp3.MultipartBody.FORM, email);
        RequestBody idBodycontactno = RequestBody.create(okhttp3.MultipartBody.FORM, contact_number);
        RequestBody idBodyfees = RequestBody.create(okhttp3.MultipartBody.FORM, fees);
        RequestBody idBodytenschool = RequestBody.create(okhttp3.MultipartBody.FORM, ten_school);
        RequestBody idBodytenpercentage = RequestBody.create(okhttp3.MultipartBody.FORM, ten_percentage);
        RequestBody idBodytwelve = RequestBody.create(okhttp3.MultipartBody.FORM, twelve_school);
        RequestBody idBodytwelveercentage = RequestBody.create(okhttp3.MultipartBody.FORM, twelve_percentage);
        RequestBody idBodyugschool = RequestBody.create(okhttp3.MultipartBody.FORM, ug_school);
        RequestBody idBodyugpercentage = RequestBody.create(okhttp3.MultipartBody.FORM, ug_percentage);
        RequestBody idBodycopname = RequestBody.create(okhttp3.MultipartBody.FORM, company_name);
        RequestBody idBodyjobprofile = RequestBody.create(okhttp3.MultipartBody.FORM, job_profile);
        RequestBody idBodyexp = RequestBody.create(okhttp3.MultipartBody.FORM, experince);
        RequestBody idBodyposition = RequestBody.create(okhttp3.MultipartBody.FORM, position);
        RequestBody idBodyfax = RequestBody.create(okhttp3.MultipartBody.FORM, fax);
        RequestBody idBodyrelocate = RequestBody.create(okhttp3.MultipartBody.FORM, relocate);
        RequestBody idBodylastCompany = RequestBody.create(okhttp3.MultipartBody.FORM, last_company);
        RequestBody idBodycomment = RequestBody.create(okhttp3.MultipartBody.FORM, comment);
        RequestBody idBodyorderid = RequestBody.create(okhttp3.MultipartBody.FORM, orderId);
        RequestBody idBodychoosepost = RequestBody.create(okhttp3.MultipartBody.FORM, choosepost);


        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), ten_imageBytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("ten_image", "image.jpg", requestFile);
        //System.out.println("body++++++++++++++++++++++++++++++++++"+body);
        if (body.equals("")){

        }else {
            body1 = MultipartBody.Part.createFormData("ten_image", "image.jpg", requestFile);
        }
        RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/jpeg"), twelve_imageBytes);
        MultipartBody.Part body2 = MultipartBody.Part.createFormData("twelve_image", "image.jpg", requestFile2);
        if (body2.equals("")) {

        }else {
            body22 = MultipartBody.Part.createFormData("twelve_image", "image.jpg", requestFile2);
        }

        RequestBody requestFile3 = RequestBody.create(MediaType.parse("image/jpeg"), ug_imageBytes);
        MultipartBody.Part body3 = MultipartBody.Part.createFormData("ug_image", "image.jpg", requestFile3);
        if (body3.equals("")){

        }else {
            body33 = MultipartBody.Part.createFormData("ug_image", "image.jpg", requestFile3);
        }

        RequestBody requestFile4 = RequestBody.create(MediaType.parse("image/jpeg"), adar_cardBytes);
        MultipartBody.Part body4 = MultipartBody.Part.createFormData("adar_card", "image.jpg", requestFile4);
        if (body4.equals("")){

        }else {
            body44 = MultipartBody.Part.createFormData("adar_card", "image.jpg", requestFile4);
        }

        RequestBody requestFile5 = RequestBody.create(MediaType.parse("image/jpeg"), photographBytes);
        MultipartBody.Part body5 = MultipartBody.Part.createFormData("photograph", "image.jpg", requestFile5);
        //System.out.println("body5-------------------------------------"+body5);
        if (body5.equals("")){

        }else {
            body55 = MultipartBody.Part.createFormData("photograph", "image.jpg", requestFile5);
        }

        RequestBody requestFile6 = RequestBody.create(MediaType.parse("image/jpeg"), signatureBytes);
        MultipartBody.Part body6 = MultipartBody.Part.createFormData("signature", "image.jpg", requestFile6);
        if (body6.equals("")){

        }else {
            body66 = MultipartBody.Part.createFormData("signature", "image.jpg", requestFile6);
        }

        apiInterface.submitHiring(idBodyfname, idBodylname, idBodymname, idBodyfathername, idBodydate, idBodyaddress, idBodyadno, idBodyemail, idBodycontactno, idBodyfees, idBodytenschool, idBodytenpercentage, body1, idBodytwelve, idBodytwelveercentage, body22, idBodyugschool, idBodyugpercentage, body33, body44, idBodycopname, idBodyjobprofile, idBodyexp, idBodyposition, idBodyfax, idBodyrelocate, idBodylastCompany, body55, body66, idBodycomment, idBodyorderid, idBodychoosepost).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("----------------------------------------");
                Toast.makeText(getContext(), "Upload Successfully", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                CommonUtils.savePreferenceString(getContext(), AppConstant.FIRST_NAME, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.LAST_NAME, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.MOTHER_NAME, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.FATHER_NAME, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.DATE, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.ADDRESS, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.ADHAR_NUMBER, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.EMAIL_ID, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.CONTACT_NUMBER, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.REGISTRATION_FEE, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.CHOOSE_POST, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_10_SCHOOL, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_10_PERCENTAGE, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_10_DOCUMENT, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_12_SCHOOL, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_12_PERCENTAGE, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.ACADEMIA_12_DOCUMENT, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.GRADUATE_COLLEGE, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.GRADUATE_PERCENTAGE, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.GRADUATE_DOCUMENT, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.ADHAAR_CARD, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.JOB_PROFILE, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.EXPERIENCE, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.FAX, "");

                CommonUtils.savePreferenceString(getContext(), AppConstant.POSITION_APPLY, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.RELOCATE, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.LAST_COMPANY, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.PHOTO_GRAPH, "");
                CommonUtils.savePreferenceString(getContext(), AppConstant.SIGNATURE, "");



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Image Updated failed", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];
        System.out.println("======================================"+buff);
        int len = 0;
        try {
            while ((len = is.read(buff)) != -1) {
                byteBuff.write(buff, 0, len);
            }

        }catch (Exception e){

        }
        return byteBuff.toByteArray();
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
                resultUri = result.getUri();
                System.out.println("resultUri---------------------" + resultUri);
                if (AppConstant.IMAGE_ONE.equals(IMAGE_ONE)) {
                    ivProfileImage.setImageURI(resultUri);
                    CommonUtils.savePreferenceString(getContext(), AppConstant.PHOTO_GRAPH, String.valueOf(resultUri));
                    AppConstant.IMAGE_ONE = "";


                }
                if (AppConstant.IMAGE_TWO.equals(IMAGE_TWO)) {
                    ivSignature.setImageURI(resultUri);
                    CommonUtils.savePreferenceString(getContext(), AppConstant.SIGNATURE, String.valueOf(resultUri));
                    System.out.println("image22222222222222222222222222222");
                    //AppConstant.IMAGE_TWO = "";

                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}