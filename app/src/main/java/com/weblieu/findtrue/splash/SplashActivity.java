package com.weblieu.findtrue.splash;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.weblieu.findtrue.MainActivity;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.login.LoginActivity;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;
import com.weblieu.findtrue.vendor.VendorMainActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private Context context = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String isLoggedIn = CommonUtils.getPreferencesString(context, AppConstant.IS_LOGGED_IN);
        String isUser = CommonUtils.getPreferencesString(context, AppConstant.IS_USERS);
        System.out.println("isLoggedIn---------------------------------"+isLoggedIn);
        System.out.println("isUser---------------------------------"+isUser);

        if (isLoggedIn.equals("true") && isUser.equals("user")){
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }else if (isLoggedIn.equals("true") && isUser.equals("vendor")){
            Intent intent = new Intent(context, VendorMainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        //checking user login and not login

//        if (isLoggedIn.equals("true")){
//            Intent intent = new Intent(context, VendorMainActivity.class);
//            startActivity(intent);
//            finish();
//        }else {
//            Intent intent = new Intent(context, LoginActivity.class);
//            startActivity(intent);
//        }
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
    }
}