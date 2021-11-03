package com.weblieu.findtrue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.weblieu.findtrue.adapter.TabAdapter;
import com.weblieu.findtrue.fragment.PhotosFragment;
import com.weblieu.findtrue.fragment.ProfileFragment;
import com.weblieu.findtrue.fragment.ReviewFragment;
import com.weblieu.findtrue.fragment.ViedoFragment;
import com.weblieu.findtrue.service.AppConfig;

public class ViewProfileActivity extends AppCompatActivity {

    private ImageView ivBack, ivCoverPhoto;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String VENDOR_NAME;
    private Toolbar toolbar2;
    private ImageView ivBackScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        ivCoverPhoto = (ImageView)findViewById(R.id.ivCoverPhoto);
        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        ivBackScreen = (ImageView)findViewById(R.id.ivBackScreen);

        ivBackScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProfileFragment(), "Profile");
        adapter.addFragment(new PhotosFragment(), "Photos");
        adapter.addFragment(new ViedoFragment(), "Video");
        adapter.addFragment(new ReviewFragment(), "Review");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        if (intent!=null){
            String PROFILE_IMAGE = intent.getStringExtra("PROFILE_IMAGE");
            VENDOR_NAME = intent.getStringExtra("VENDOR_NAME");
            System.out.println("PROFILE_IMAGE----------------------------"+PROFILE_IMAGE);
            System.out.println("VENDOR_NAME----------------------------"+VENDOR_NAME);
            //Birthday Party Organizer In Delhi
            Glide.with(this).load(AppConfig.CATEGORY_DETAILS_IMAGE + PROFILE_IMAGE).placeholder(R.drawable.no_img).into(ivCoverPhoto);
        }

    }
}