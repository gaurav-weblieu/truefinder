package com.weblieu.findtrue.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weblieu.findtrue.R;
import com.weblieu.findtrue.model.CategoryData;
import com.weblieu.findtrue.model.CategoryProfile;
import com.weblieu.findtrue.model.Data1;
import com.weblieu.findtrue.model.Datum;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {


    private ApiInterface apiInterface;
    private TextView tvVendorName, tvAddress, tvBusiness, tvVendorAddress, tvEstablishedYear, tvCash, tvFullOfficeAddress, tvOfficeHour, tvCategory_Name;
    private TextView tvOfficeAddress;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvVendorName = (TextView)view.findViewById(R.id.tvVendorName);
        tvAddress = (TextView)view.findViewById(R.id.tvAddress);
        tvBusiness = (TextView)view.findViewById(R.id.tvBusiness);
        tvEstablishedYear = (TextView)view.findViewById(R.id.tvEstablishedYear);
        tvCash = (TextView)view.findViewById(R.id.tvCash);
        tvFullOfficeAddress = (TextView)view.findViewById(R.id.tvFullOfficeAddress);
        tvOfficeHour = (TextView)view.findViewById(R.id.tvOfficeHour);
        tvCategory_Name = (TextView)view.findViewById(R.id.tvCategory_Name);
        tvOfficeAddress = (TextView)view.findViewById(R.id.tvOfficeAddress);
        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);

        getCategoryProfile();


    }

    private void getCategoryProfile(){
        //String vid = "Birthday-Party-Organizer-In-Delhi";
        String vid = CommonUtils.getPreferencesString(getContext(), AppConstant.VENDOR_EMAIL);
        apiInterface.getCategoryProfile(vid).enqueue(new Callback<CategoryProfile>() {
            @Override
            public void onResponse(Call<CategoryProfile> call, Response<CategoryProfile> response) {
                if (response.isSuccessful()){
                    String type = response.body().getType();
                    System.out.println("type---------------------------"+type);
                    List<Datum> results = response.body().getData();
                    for (Datum data : results){
                        tvVendorName.setText("About "+data.getVendorName());
                        tvAddress.setText(data.getAddress());
                        tvBusiness.setText("Business Info "+data.getVendorName()+" :");
                        tvFullOfficeAddress.setText(data.getAddress()+", "+data.getLocalarea()+", "+data.getPincode()+", "+data.getCountry());
                        tvCategory_Name.setText(AppConstant.CATEGORY_NAME);
                        tvOfficeAddress.setText(data.getVendorName() +"Office Address:");
                    }
                    List<Data1> data = response.body().getData1();
                    for (Data1 data1 : data){
                        tvEstablishedYear.setText(data1.getEstablish());
                        tvCash.setText(data1.getAccepts());
                        tvOfficeHour.setText(data1.getOfficeHour());
                    }
                }

            }

            @Override
            public void onFailure(Call<CategoryProfile> call, Throwable t) {

            }
        });

    }
}