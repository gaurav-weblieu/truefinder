package com.weblieu.findtrue.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

public class VendorMainActivity extends AppCompatActivity {

    private Context context = VendorMainActivity.this;
    private RelativeLayout mRelativeOne, mRelativeTwo, mRelativeThree, mRelativeFour, mRelativeFive, mRelativeSix, relativeLogOut, relativeLid, relativePackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vendor_main);

        mRelativeOne = (RelativeLayout)findViewById(R.id.relativeProfile);
        mRelativeTwo = (RelativeLayout)findViewById(R.id.relativeEditProfile);
        mRelativeThree = (RelativeLayout)findViewById(R.id.relativeDescription);
        mRelativeFour = (RelativeLayout)findViewById(R.id.relativeFaq);
        mRelativeFive = (RelativeLayout)findViewById(R.id.relativeGallery);
        mRelativeSix = (RelativeLayout)findViewById(R.id.relativeVideo);
        relativeLogOut = (RelativeLayout)findViewById(R.id.relativeLogOut);
        relativeLid = (RelativeLayout)findViewById(R.id.relativeLid);
        relativePackage = (RelativeLayout)findViewById(R.id.relativePackage);

        String VENDOR_ID = CommonUtils.getPreferencesString(context, AppConstant.VENDOR_ID);
        System.out.println("VENDOR_ID================================"+VENDOR_ID);

        relativePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PackageActivity.class);
                startActivity(intent);
            }
        });
        mRelativeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileImageActivity.class);
                startActivity(intent);
            }
        });

        mRelativeTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        mRelativeThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LeadActivity.class);
                startActivity(intent);
            }
        });

        mRelativeFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddFAQActivity.class);
                startActivity(intent);

            }
        });

        mRelativeFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageGalleryActivity.class);
                startActivity(intent);
            }
        });

        mRelativeSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageViedoGalleryActivity.class);
                startActivity(intent);
            }
        });

        relativeLid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DescriptionActivity.class);
                startActivity(intent);
            }
        });

        relativeLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FlatDialog flatDialog = new FlatDialog(context);
                flatDialog.setBackgroundColor(R.color.app_color2);
                flatDialog.setTitle("Logout")
                        .setSubtitle("Are you sure you want to end the session")
                        .setFirstButtonText("YES")
                        .setSecondButtonText("NO")
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CommonUtils.savePreferenceString(context, AppConstant.IS_LOGGED_IN, "false");
                                finish();
                                flatDialog.dismiss();
                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
}