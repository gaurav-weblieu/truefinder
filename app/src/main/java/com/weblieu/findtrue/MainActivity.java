package com.weblieu.findtrue;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.weblieu.findtrue.adapter.CategoryAdapter;
import com.weblieu.findtrue.adapter.HomeViewTypeAdapter;
import com.weblieu.findtrue.login.ForgotPasswordActivity;
import com.weblieu.findtrue.login.LoginActivity;
import com.weblieu.findtrue.model.Category;
import com.weblieu.findtrue.profile.GetProfile;
import com.weblieu.findtrue.profile.LoginData;
import com.weblieu.findtrue.profile.ProfileActivity;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.splash.PaymentActivity;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "";
    private Context context = MainActivity.this;
    private AppCompatButton mAppCompactButton;
    private DrawerLayout drawerLayout;
    private NavigationView navitationView;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private HomeViewTypeAdapter mHomeViewTypeAdapter;
    List<Category> categoryItemList = null;
    private ApiInterface apiInterface;

    //SliderLayout imageSlider;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;

    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    public static TextView tvLocation;
    private ImageView ivArrowDown;
    private TextView editTextTextPersonName;
    private CircleImageView ivProfileImage;
    private TextView tvProfileImage, tvUserEmail;
    String USER_ID;
    Geocoder geocoder;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navitationView = (NavigationView) findViewById(R.id.navitationView);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAppCompactButton = (AppCompatButton) findViewById(R.id.btnLogin);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        tvLocation = (TextView)findViewById(R.id.tvLocation);
        mRecyclerView.setNestedScrollingEnabled(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ivArrowDown = (ImageView)findViewById(R.id.ivArrowDown);
        editTextTextPersonName = (TextView) findViewById(R.id.editTextTextPersonName);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        /*-------------------------Toolbar----------------------------------*/
        setSupportActionBar(toolbar);

        navitationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navitation_drawer_open, R.string.navitation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_format_align_left_24);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        //actionBarDrawerToggle.setDrawerIndicatorEnabled(false);


        navitationView.setNavigationItemSelectedListener(this);

//        imageSlider = (SliderLayout) findViewById(R.id.imageSlider);
//        imageSlider.setPictureIndex(0);
//        List<Object> listJingDong = new ArrayList<>();
//        listJingDong.add(R.drawable.truefind_banner6);
//        listJingDong.add(R.drawable.truefind_banner7);
//        listJingDong.add(R.drawable.truefind_banner8);
//        imageSlider.setList(listJingDong);
        getLocalIpAddress();
        getPublicIPAddress(this);

        categoryItemList = new ArrayList<>();
        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        USER_ID = CommonUtils.getPreferencesString(context, AppConstant.USER_ID);
        System.out.println("USER_ID================================"+USER_ID);

        View headerLayout = navitationView.inflateHeaderView(R.layout.header);
        tvUserEmail = (TextView)headerLayout.findViewById(R.id.tvUserEmail);
        ivProfileImage = (CircleImageView)headerLayout.findViewById(R.id.ivProfileImage);
        tvProfileImage = (TextView)headerLayout.findViewById(R.id.tvProfileImage);

        getProfile();
        editTextTextPersonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        apiInterface.getCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    categoryItemList = response.body();
                    if (categoryItemList != null) {
                        CategoryAdapter categoryAdapter = new CategoryAdapter(MainActivity.this, categoryItemList);
                        mRecyclerView.setAdapter(categoryAdapter);

                        //System.out.println("category+++++--------------------------" + categoryItemList);
                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

        //check condition user login and not
        String isLoggedIn = CommonUtils.getPreferencesString(MainActivity.this, AppConstant.IS_LOGGED_IN);
        if (isLoggedIn.equals("true")) {
            mAppCompactButton.setVisibility(View.INVISIBLE);
        } else {
            mAppCompactButton.setVisibility(View.VISIBLE);
        }

        mAppCompactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getProfile(){
        apiInterface.getUserProfile(USER_ID).enqueue(new Callback<GetProfile>() {
            @Override
            public void onResponse(Call<GetProfile> call, Response<GetProfile> response) {
                if (response.isSuccessful()){
                    //System.out.println("message========================="+response.body().getMessage());

                    List<LoginData> loginData = response.body().getData();
                    for (LoginData login : loginData){
                        System.out.println("fname============================"+login.getFname());
                        tvProfileImage.setText(login.getFname()+" "+login.getLname());
                        tvUserEmail.setText(login.getEmail());
                        Glide.with(context).load(login.getNewPofileImage()).centerCrop().placeholder(R.drawable.unnamed).into(ivProfileImage);

                    }
                }
            }

            @Override
            public void onFailure(Call<GetProfile> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;

            case R.id.add_to_shortlist:
                Intent intentFavorite = new Intent(context, AddToShortListActivity.class);
                startActivity(intentFavorite);
                break;

            case R.id.list_business:
                Intent intent2 = new Intent(MainActivity.this, FreeListeningActivity.class);
                startActivity(intent2);
                break;

            case R.id.nav_hiring:
                Intent intent3 = new Intent(MainActivity.this, PaymentActivity.class);
                //Intent intent3 = new Intent(MainActivity.this, HiringActivity.class);
                startActivity(intent3);
                break;

            case R.id.nav_login:
                break;

            case R.id.nav_profile:
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.forgetPassword:
                Intent intent1 = new Intent(context, ForgotPasswordActivity.class);
                startActivity(intent1);

            case R.id.nav_logout:
                final FlatDialog flatDialog = new FlatDialog(context);
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
                        .show(); // .setFirstTextFieldHint("email")
                        //.setSecondTextFieldHint("password")
//                DialogSheet dialogSheet = new DialogSheet(context, true);
//                View view = View.inflate(context, R.layout.logout_dialog, null);
//                dialogSheet.setView(view);
//                dialogSheet.show();

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    //get location
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            System.out.println("latitude-----------------" + location.getLatitude());
                            System.out.println("longitute-----------------" + location.getLongitude());
                            String latlong = location.getLatitude() + "," + location.getLongitude();
                            System.out.println("latlong 1-------------------" + latlong);


                            geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                String current_address = addresses.get(0).getAddressLine(0); //0 to obtain first possible address
                                String city = addresses.get(0).getLocality();
                                System.out.println("city--------------------------" + city);
                                tvLocation.setText(city);
                                String str = city;
                                String cityName = str.substring(0, 1).toLowerCase() + str.substring(1);
                                System.out.println("city--------------------------" + cityName);
                                AppConstant.CITY_NAME = cityName;

                               // System.out.println("city_name-------------------"+AppConstant.CITY_NAME);

                            } catch (IOException e) {

                            }
                        }
                    }
                });

            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }

        } else {
            requestPermissions();
        }
    }

    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            System.out.println("mLatitude-----------------------"+mLastLocation.getLatitude());
            System.out.println("mLongitude-----------------------"+mLastLocation.getLongitude());
            //latTextView.setText(mLastLocation.getLatitude()+"");
            //lonTextView.setText(mLastLocation.getLongitude()+"");
        }
    };
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        String ip = inetAddress.getHostAddress();
                        System.out.println("ipaddress------------------------------"+ip);
                        Log.i(TAG, "***** IP="+ inetAddress.getHostAddress());
                        return inetAddress.getHostAddress();

                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //get public ip address create function
    public static String getPublicIPAddress(Context context) {
        //final NetworkInfo info = NetworkUtils.getNetworkInfo(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo info = cm.getActiveNetworkInfo();

        RunnableFuture<String> futureRun = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if ((info != null && info.isAvailable()) && (info.isConnected())) {
                    StringBuilder response = new StringBuilder();

                    try {
                        HttpURLConnection urlConnection = (HttpURLConnection) (
                                new URL("http://checkip.amazonaws.com/").openConnection());
                        urlConnection.setRequestProperty("User-Agent", "Android-device");
                        //urlConnection.setRequestProperty("Connection", "close");
                        urlConnection.setReadTimeout(15000);
                        urlConnection.setConnectTimeout(15000);
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setRequestProperty("Content-type", "application/json");
                        urlConnection.connect();

                        int responseCode = urlConnection.getResponseCode();

                        if (responseCode == HttpURLConnection.HTTP_OK) {

                            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }

                        }
                        urlConnection.disconnect();
                        String ipaddress = response.toString();
                        System.out.println("ipaddress-----------------------"+ipaddress);
                        AppConstant.PUBLIC_IP_ADDRESS = ipaddress;
                        return response.toString();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //Log.w(TAG, "No network available INTERNET OFF!");
                    return null;
                }
                return null;
            }
        });

        new Thread(futureRun).start();

        try {
            return futureRun.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}