package com.weblieu.findtrue;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.layer_net.stepindicator.StepIndicator;
import com.marcoscg.dialogsheet.DialogSheet;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.weblieu.findtrue.adapter.SectionsPagerAdapter;
import com.weblieu.findtrue.fragment.ChoosePostFragment;
import com.weblieu.findtrue.fragment.PersonalDetailsFragment;
import com.weblieu.findtrue.utils.AppConstant;

import java.util.List;

public class HiringActivity extends AppCompatActivity {

    private static final String TAG = "HiringActivity";
    private ImageView ivBackHiring;
    private CheckBox mCheckBox;
    private ImageView ivImageAdhar, ivProfileImage, ivSignature;
    public String IMAGE_ONE;
    public String IMAGE_TWO;
    public String IMAGE_THREE;
    private Uri mCropImageUri;
    private RadioGroup radioGroup;
    private RadioButton radioButtonOne, radioButtonTwo, radioButtonThree, radioButtonFour;
    private RelativeLayout relative10th, relative2, relativeGraduate;
    private LinearLayout linearAdhar;
    private View view2;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static ViewPager mViewPager;
    public static StepIndicator stepIndicator;

    boolean i_dont_want_to_leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiring);
        initID();
    }

    private void initID() {
        ivBackHiring = (ImageView) findViewById(R.id.ivBackHiring);
        mCheckBox = (CheckBox) findViewById(R.id.mCheckBox);
        ivImageAdhar = (ImageView) findViewById(R.id.ivImageAdhar);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        ivSignature = (ImageView) findViewById(R.id.ivSignature);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioButtonOne = (RadioButton)findViewById(R.id.radioButtonOne);
        radioButtonTwo = (RadioButton)findViewById(R.id.radioButtonTwo);
        radioButtonThree = (RadioButton)findViewById(R.id.radioButtonThree);
        radioButtonFour = (RadioButton)findViewById(R.id.radioButtonFour);
        relative10th = (RelativeLayout)findViewById(R.id.relative10th);
        relativeGraduate = (RelativeLayout)findViewById(R.id.relativeGraduate);
        linearAdhar = (LinearLayout)findViewById(R.id.linearAdhar);
        view2 = (View)findViewById(R.id.view2);
        relative2 = (RelativeLayout)findViewById(R.id.relative2);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        stepIndicator = (StepIndicator) findViewById(R.id.step_indicator);
        stepIndicator.setupWithViewPager(mViewPager);
        stepIndicator.setClickable(false);

        initEvent();
        ivBackHiring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonOne){
                    linearAdhar.setVisibility(View.GONE);
                    relative10th.setVisibility(View.VISIBLE);
                    relative2.setVisibility(View.VISIBLE);
                    relativeGraduate.setVisibility(View.VISIBLE);
                }else if (checkedId == R.id.radioButtonTwo){
                    linearAdhar.setVisibility(View.GONE);
                    relative10th.setVisibility(View.VISIBLE);
                    relative2.setVisibility(View.VISIBLE);
                    relativeGraduate.setVisibility(View.VISIBLE);
                }else if (checkedId == R.id.radioButtonThree){
                    relativeGraduate.setVisibility(View.GONE);
                    linearAdhar.setVisibility(View.GONE);
                    relative10th.setVisibility(View.VISIBLE);
                    relative2.setVisibility(View.VISIBLE);

                }else if (checkedId == R.id.radioButtonFour){
                    relative10th.setVisibility(View.GONE);
                    relativeGraduate.setVisibility(View.GONE);
                    linearAdhar.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    relative2.setVisibility(View.GONE);
                    linearAdhar.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    private void initEvent() {
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DialogSheet dialogSheet = new DialogSheet(HiringActivity.this, true);
                    View view = View.inflate(HiringActivity.this, R.layout.term_dialog, null);
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

        // start picker to get image for cropping and then use the image in cropping activity
        ivImageAdhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onSelectImageClick(v);
                //IMAGE_ONE = AppConstant.IMAGE_ONE;
                //System.out.println("--------------ONE image-"+IMAGE_ONE);
            }
        });

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onSelectImageClick(v);
                //IMAGE_TWO = AppConstant.IMAGE_TWO;
                //System.out.println("--------------TWO image-"+IMAGE_TWO);
            }
        });

        ivSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onSelectImageClick(v);
                //IMAGE_THREE = AppConstant.IMAGE_THREE;
                //System.out.println("--------------THREE image-"+IMAGE_THREE);
            }
        });
    }

    public void onSelectImageClick(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
       // CropImage.startPickImageActivity(this);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            startCropImageActivity(mCropImageUri);
//        } else {
//            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
//        }
//    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    //Override onActivityResult method in your activity to get crop result
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//                System.out.println("resultUri---------------------"+resultUri);
//                if (AppConstant.IMAGE_ONE.equals(IMAGE_ONE)) {
//                    ivImageAdhar.setImageURI(resultUri);
//                    System.out.println("image11111111111111111111111111");
//                    AppConstant.IMAGE_ONE = "";
//
//
//                }
//                if (AppConstant.IMAGE_TWO.equals(IMAGE_TWO)) {
//                    ivProfileImage.setImageURI(resultUri);
//                    System.out.println("image22222222222222222222222222222");
//                    AppConstant.IMAGE_TWO = "";
//                }
//                if (AppConstant.IMAGE_THREE.equals(IMAGE_THREE)){
//                    ivSignature.setImageURI(resultUri);
//                    System.out.println("image33333333333333333333333333333");
//                    AppConstant.IMAGE_TWO = "";
//                }
//
//        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//            Exception error = result.getError();
//        }
//
//        }
//    }


    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        }
        else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }

}

