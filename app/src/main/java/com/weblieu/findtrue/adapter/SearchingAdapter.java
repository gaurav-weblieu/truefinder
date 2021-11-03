package com.weblieu.findtrue.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.marcoscg.dialogsheet.DialogSheet;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.ViewProfileActivity;
import com.weblieu.findtrue.model.CategoryData;
import com.weblieu.findtrue.model.SendEnquiry;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.ResponseRequest;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.search.Data;
import com.weblieu.findtrue.search.SearchCategory;
import com.weblieu.findtrue.search.Vendor;
import com.weblieu.findtrue.service.AppConfig;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchingAdapter extends RecyclerView.Adapter<SearchingAdapter.ViewHolder>{
    Context mContext;
    private List<Vendor> vendorList;

    int mYear, mMonth, mDay;
    String vndrbox, category_id, city_id, areapinid, dateTime;
    String checked;

    TextView editDateTime;
    EditText editName;
    EditText editEmail;
    EditText editMobileNo;
    ApiInterface apiInterface;

    public SearchingAdapter(Context mContext, List<Vendor> vendorList){
        this.mContext = mContext;
        this.vendorList = vendorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.category_details_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Vendor vendorName = vendorList.get(position);
        System.out.println("id-------------------------"+vendorName.getId());
        Glide.with(mContext).load(AppConfig.CATEGORY_DETAILS_IMAGE+vendorName.getProfilepic()).centerCrop().placeholder(R.drawable.no_img).into(holder.ivImage);
        holder.tvName.setText(vendorName.getVendorName()+", "+vendorName.getCity());
        holder.tvAddress.setText(vendorName.getAddress());
        holder.tvPrice.setText("PACKAGE PRICE : " +"\u20B9" +vendorName.getPackagePrice());

        holder.linear_cat_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewProfileActivity.class);
                intent.putExtra("PROFILE_IMAGE", vendorList.get(position).getProfilepic());
                intent.putExtra("VENDOR_NAME", vendorList.get(position).getVendorName());
                CommonUtils.savePreferenceString(mContext, AppConstant.VENDOR_EMAIL, vendorList.get(position).getEmail());
                mContext.startActivity(intent);
            }
        });

        holder.btnSendEnquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSheet dialogSheet = new DialogSheet(mContext, true);
                View view = View.inflate(mContext, R.layout.fragment_enquiry_dialog, null);
                dialogSheet.setView(view);
                dialogSheet.show();
                AppCompatButton btnClose = (AppCompatButton)view.findViewById(R.id.btnClose);
                AppCompatButton btnSubmit = (AppCompatButton)view.findViewById(R.id.btnSubmit);
                ImageView ivClose = (ImageView)view.findViewById(R.id.ivClose);
                editDateTime = (TextView) view.findViewById(R.id.editDateTime);
                editName = (EditText)view.findViewById(R.id.editName);
                editEmail = (EditText)view.findViewById(R.id.editEmail);
                editMobileNo = (EditText)view.findViewById(R.id.editMobileNo);
                CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox);

                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSheet.dismiss();
                    }
                });

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSheet.dismiss();
                    }
                });

                editDateTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get Current Date
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        editDateTime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                        //dateTime = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                        dateTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                        System.out.println("dateTime----------------------"+dateTime);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });


                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            checked = "1";
                            System.out.println("checked---------------------"+checked);
                        }else {
                            //boolean check = false;
                            checked = "0";
                            System.out.println("checked---------------------"+checked);
                        }
                    }
                });


                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validationCheck()){
                            String id = vendorList.get(position).getId();
                            String city_id = vendorList.get(position).getCityId();
                            String areapinid = vendorList.get(position).getPincode();
                            apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
                            SendEnquiry sendEnquiry = new SendEnquiry();
                            sendEnquiry.setVndrbox(id);
                            sendEnquiry.setCategory_id(category_id);
                            sendEnquiry.setCity_id(city_id);
                            sendEnquiry.setAreapinid(areapinid);
                            sendEnquiry.setName(editName.getText().toString());
                            sendEnquiry.setEmail(editEmail.getText().toString());
                            sendEnquiry.setMobile_country_code(91);
                            sendEnquiry.setContactno(editMobileNo.getText().toString());
                            sendEnquiry.setBookingdate(dateTime);
                            sendEnquiry.setWhatsapp_message_status(checked);
                            sendEnquiry.setIpaddress(AppConstant.PUBLIC_IP_ADDRESS);

                            //System.out.println("output------------------"+sendEnquiry);
                            String country_code = "91";

                            apiInterface.sendEnquiry(vndrbox, category_id, city_id, areapinid, editName.getText().toString(), editEmail.getText().toString(), country_code, editMobileNo.getText().toString(), dateTime, checked, AppConstant.PUBLIC_IP_ADDRESS).enqueue(new Callback<ResponseRequest>() {
                                @Override
                                public void onResponse(Call<ResponseRequest> call, Response<ResponseRequest> response) {
                                    if (response.isSuccessful()){
                                        System.out.println("-------------------------------------"+response.body().getMessage());
                                        Toast.makeText(mContext, "Enquiry send successfully", Toast.LENGTH_LONG).show();
                                        dialogSheet.dismiss();

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseRequest> call, Throwable t) {
                                    Toast.makeText(mContext, "Enquiry failed", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                        //dialogSheet.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendorList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView ivImage;
        TextView tvName, tvAddress, tvPrice;
        AppCompatButton btnSendEnquery;
        LinearLayout linear_cat_details;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = (RoundedImageView)itemView.findViewById(R.id.ivImage);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvAddress = (TextView)itemView.findViewById(R.id.tvAddress);
            tvPrice = (TextView)itemView.findViewById(R.id.tvPrice);
            btnSendEnquery = (AppCompatButton) itemView.findViewById(R.id.btnSendEnquery);
            linear_cat_details = (LinearLayout)itemView.findViewById(R.id.linear_cat_details);

        }
    }

    private boolean validationCheck(){
        boolean result = false;
        if (editName.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(mContext, "Please enter your name", Toast.LENGTH_LONG).show();
            result = false;

        } else if (editEmail.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(mContext, "Please enter your email", Toast.LENGTH_LONG).show();
            result = false;

        } else if (editMobileNo.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(mContext, "Please enter your mobile number", Toast.LENGTH_LONG).show();
            result = false;

        } else if (editDateTime.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(mContext, "Please enter your mobile number", Toast.LENGTH_LONG).show();
            result = false;
        } else {

            return true;
        }
        return result;
    }

}
