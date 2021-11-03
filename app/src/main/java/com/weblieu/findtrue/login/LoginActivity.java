package com.weblieu.findtrue.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.weblieu.findtrue.MainActivity;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.adapter.LoginAdapter;
import com.weblieu.findtrue.adapter.TabAdapter;
import com.weblieu.findtrue.fragment.PhotosFragment;
import com.weblieu.findtrue.fragment.ProfileFragment;
import com.weblieu.findtrue.fragment.ServiceProviderFragment;
import com.weblieu.findtrue.fragment.UserLoginFragment;
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

public class LoginActivity extends AppCompatActivity {

    //private TextView tvUserLogin, tvSeviceProvider;
    private TabLayout tab_layout;
    private ViewPager view_pager;
    LoginAdapter loginAdapter;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initID();

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.mFrameLayout, new UserLoginFragment(), null);
//        fragmentTransaction.commit();

    }


    private void initID(){
        tab_layout = (TabLayout)findViewById(R.id.tab_layout);
        view_pager = (ViewPager)findViewById(R.id.view_pager);

        loginAdapter = new LoginAdapter(getSupportFragmentManager());
        loginAdapter.addFragment(new UserLoginFragment(), "USER LOGIN");
        loginAdapter.addFragment(new ServiceProviderFragment(), "SERVICE PROVIDER");

        view_pager.setAdapter(loginAdapter);
        tab_layout.setupWithViewPager(view_pager);


        //tab_layout.setTranslationY(300);
        //tab_layout.setAlpha(v);
//        tvUserLogin = (TextView)findViewById(R.id.tvUserLogin);
//        tvSeviceProvider = (TextView)findViewById(R.id.tvSeviceProvider);
        //initAction();
    }

//    private void initAction(){
//        tvUserLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new UserLoginFragment();
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.mFrameLayout, fragment, null);
//                fragmentTransaction.commit();
//                tvUserLogin.setTextColor(Color.WHITE);
//                tvSeviceProvider.setTextColor(Color.BLACK);
//            }
//        });
//
//        tvSeviceProvider.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment fragment = new ServiceProviderFragment();
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.mFrameLayout, fragment, null);
//                fragmentTransaction.commit();
//                tvSeviceProvider.setTextColor(Color.WHITE);
//                tvUserLogin.setTextColor(Color.BLACK);
//            }
//        });
//    }
}