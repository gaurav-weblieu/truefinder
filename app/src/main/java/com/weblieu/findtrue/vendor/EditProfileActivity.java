  package com.weblieu.findtrue.vendor;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.SearchActivity;
import com.weblieu.findtrue.model.Category;
import com.weblieu.findtrue.model.CityList;
import com.weblieu.findtrue.model.CityModel;
import com.weblieu.findtrue.model.EditProfileUpdate;
import com.weblieu.findtrue.model.NewCateIdz;
import com.weblieu.findtrue.model.VendorData;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.ResponseRequest;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;
import com.weblieu.findtrue.utils.TextDrawable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringJoiner;

import de.mrapp.android.dialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements CategorySelectedListener, View.OnClickListener{

    private Context context = EditProfileActivity.this;
    private EditText editBusinessName, editVendorName, editLocalArea, editAddress, editPincode, editCountry, editWebSite, editContactNumber, editAreaCode, editPhoneNo, editTextEstablish, editAccepts;
    private ImageView back;
    private RelativeLayout btnSubmit;
    private TextView tvFindCity, tvSelectCategory;

    List<CityList> cityLists;
    List<Category> categoryItemList = null;
    private ApiInterface apiInterface;
    String VENDOR_ID;
    MaterialDialog.Builder builder;
    RecyclerView mRecyclerView, recyclerViewCategoryName, recyclerChip;
    private SpinnerCityAdapter spinnerCityAdapter;
    CategoryNameAdapter categoryNameAdapter;
    private EditText tvTime;

    private ChipGroup chipGroup;
    List<String> mylist = new ArrayList<String>();

    String chipValues = "";
    private ProgressBar progressBar;

    private List<NewCateIdz> newCateIdzList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editBusinessName = (EditText)findViewById(R.id.editBusinessName);
        editVendorName = (EditText)findViewById(R.id.editVendorName);
        editLocalArea = (EditText)findViewById(R.id.editLocalArea);
        editAddress = (EditText)findViewById(R.id.editAddress);
        editPincode = (EditText)findViewById(R.id.editPincode);
        editCountry = (EditText)findViewById(R.id.editCountry);
        editWebSite = (EditText)findViewById(R.id.editWebSite);
        editAreaCode = (EditText)findViewById(R.id.editAreaCode);
        editPhoneNo = (EditText)findViewById(R.id.editPhoneNo);
        editTextEstablish = (EditText)findViewById(R.id.editTextEstablish);
        editAccepts = (EditText)findViewById(R.id.editAccepts);
        editContactNumber = (EditText)findViewById(R.id.editContactNumber);
        back = (ImageView)findViewById(R.id.back);
        tvTime = (EditText) findViewById(R.id.tvTime);

        tvFindCity = (TextView)findViewById(R.id.tvFindCity);
        tvSelectCategory = (TextView)findViewById(R.id.tvSelectCategory);
        chipGroup = (ChipGroup)findViewById(R.id.chipGroup);
        btnSubmit = (RelativeLayout) findViewById(R.id.btnSubmit);
        recyclerChip = (RecyclerView)findViewById(R.id.recyclerChip);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        editContactNumber.setCompoundDrawables(new TextDrawable(editContactNumber, "+91 "), null, new TextDrawable(editContactNumber, ""), null);
        editWebSite.setCompoundDrawables(new TextDrawable(editWebSite, "+http:// "), null, new TextDrawable(editWebSite, ""), null);
        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        VENDOR_ID = CommonUtils.getPreferencesString(context, AppConstant.VENDOR_ID);
        //System.out.println("VENDOR_ID================================"+VENDOR_ID);
        newCateIdzList = new ArrayList<>();
        getEditProfile();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tvFindCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new MaterialDialog.Builder(context);
                builder.setView(R.layout.change_city_dialog);
                builder.show();
                mRecyclerView = (RecyclerView) builder.create().findViewById(R.id.recyclerViewCityName);
                ProgressBar mProgressBar = (ProgressBar)builder.create().findViewById(R.id.mProgressBar);
                EditText editSearch = (EditText)builder.create().findViewById(R.id.editSearch);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                mRecyclerView.setHasFixedSize(true);
                ImageView imageClose = (ImageView)builder.create().findViewById(R.id.imageViewClose);
                List<CityList> cityLists = new ArrayList<>();
                spinnerCityAdapter = new SpinnerCityAdapter(context, cityLists);
                apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
                apiInterface.getCity().enqueue(new Callback<CityModel>() {
                    @Override
                    public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                        if (response.isSuccessful()){
                            mProgressBar.setVisibility(View.GONE);
                            //System.out.println("type=========------------------------"+response.body().getType());
                            List<CityList> models = response.body().getData();
                            for (CityList list : models){
                                cityLists.add(list);
                                spinnerCityAdapter = new SpinnerCityAdapter(context, cityLists);
                                mRecyclerView.setAdapter(spinnerCityAdapter);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CityModel> call, Throwable t) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });

                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.create().hide();
                    }
                });

                editSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        spinnerCityAdapter.getFilter().filter(s);
                    }
                });
            }
        });

        tvSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new MaterialDialog.Builder(context);
                builder.setView(R.layout.category_dialog);
                builder.show();
                ImageView imageViewClose = (ImageView)builder.create().findViewById(R.id.imageViewClose);
                recyclerViewCategoryName = (RecyclerView)builder.create().findViewById(R.id.recyclerViewCategoryName);
                EditText editSearch = (EditText)builder.create().findViewById(R.id.editSearch);
                ProgressBar mProgressBar = (ProgressBar)builder.create().findViewById(R.id.mProgressBar);
                recyclerViewCategoryName.setLayoutManager(new LinearLayoutManager(context));
                recyclerViewCategoryName.setHasFixedSize(true);
                categoryItemList = new ArrayList<>();
                apiInterface = RetrofitClient.getClient().create(ApiInterface.class);

                apiInterface.getCategory().enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        if (response.isSuccessful()) {
                            mProgressBar.setVisibility(View.GONE);
                            categoryItemList = response.body();
                            if (categoryItemList != null) {
                                categoryNameAdapter = new CategoryNameAdapter(context, categoryItemList);
                                recyclerViewCategoryName.setAdapter(categoryNameAdapter);

                                //System.out.println("category+++++--------------------------" + categoryItemList);
                            } else {

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });

                editSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        categoryNameAdapter.getFilter().filter(s);
                    }
                });


                imageViewClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.create().hide();
                    }
                });
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String listString = "";

                for (String s : mylist)
                {
                    listString += s + "\t";
                }
                System.out.println("-----------------------------"+listString);
                String chipValues = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    chipValues = String.join(", ", mylist);
                }
                System.out.println("-----------------------------"+chipValues);

                apiInterface.updateVandorProfile(VENDOR_ID, editBusinessName.getText().toString(), editVendorName.getText().toString(), tvFindCity.getText().toString(), editLocalArea.getText().toString(), editAddress.getText().toString(), editPincode.getText().toString(), editCountry.getText().toString(), editWebSite.getText().toString(), editContactNumber.getText().toString(), editAreaCode.getText().toString(), editPhoneNo.getText().toString(), editTextEstablish.getText().toString(), editAccepts.getText().toString(), tvTime.getText().toString(), chipValues).enqueue(new Callback<ResponseRequest>() {
                    @Override
                    public void onResponse(Call<ResponseRequest> call, Response<ResponseRequest> response) {
                        if (response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseRequest> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void getEditProfile(){
        apiInterface.getVendorEditProfile(VENDOR_ID).enqueue(new Callback<EditProfileUpdate>() {
            @Override
            public void onResponse(Call<EditProfileUpdate> call, Response<EditProfileUpdate> response) {
                if (response.isSuccessful()) {

                    editBusinessName.setText(response.body().getData().getVendorName());
                    editVendorName.setText(response.body().getData().getName());
                    editCountry.setText(response.body().getData().getCountry());
                    editContactNumber.setText(response.body().getData().getContactno());
                    editLocalArea.setText(response.body().getData().getLocalarea());
                    editAddress.setText(response.body().getData().getAddress());
                    editPincode.setText(response.body().getData().getPincode());
                    editCountry.setText(response.body().getData().getCountry());
                    editWebSite.setText(response.body().getData().getWebsite());
                    editAreaCode.setText(response.body().getData().getLandlineCode());
                    editPhoneNo.setText(response.body().getData().getLandlineNo());
                    editTextEstablish.setText(response.body().getData().getNewBusinessDetailsEstablish());
                    editAccepts.setText(response.body().getData().getNewBusinessDetailsAccepts());
                    tvTime.setText(response.body().getData().getNewBusinessDetailsOfficeHour());

                    newCateIdzList = response.body().getData().getNewCateIdz();
                    for (NewCateIdz newCateIdz : newCateIdzList){
                        Chip chip = new Chip(context);
                        chip.setText(newCateIdz.getCategoryName());
                        chip.setCloseIconVisible(true);
                        chip.setCheckable(false);
                        chip.setClickable(false);
                        chipGroup.addView(chip);

                        Chip chip1 = new Chip(context);
                        chip1.setText(newCateIdz.getId());
                        chip1.setCloseIconVisible(true);
                        chip1.setCheckable(false);
                        chip1.setClickable(false);
                        String value = chip1.getText().toString();
                        mylist.add(value);

                    }

                        System.out.println("newCateIdzList---------------------------"+newCateIdzList);
                }
            }

            @Override
            public void onFailure(Call<EditProfileUpdate> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onItemSelected(Category categoryList) {

    }


    //city name adapter------------------------------------
    public class SpinnerCityAdapter extends RecyclerView.Adapter<SpinnerCityAdapter.ViewHolder> implements Filterable {
        Context context;
        List<CityList> cityLists;
        private List<CityList> cityListFull;

        public SpinnerCityAdapter(Context context, List<CityList> cityLists) {
            this.context = context;
            this.cityLists = cityLists;
            cityListFull = new ArrayList<>(cityLists);
        }

        @NonNull
        @Override
        public SpinnerCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.city_list, parent, false);
            SpinnerCityAdapter.ViewHolder viewHolder = new SpinnerCityAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CityList cityList = cityLists.get(position);
            //System.out.println("city_name-------------------------"+cityList.getCity());
            holder.tvCityName.setText(cityList.getCity());

            //city name in on click event
            holder.tvCityName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, cityList.getCity_alias(), Toast.LENGTH_LONG).show();
                    builder.create().hide();
                    AppConstant.SEARCH_CITY_NAME = cityList.getCity_alias();
                    tvFindCity.setText(cityList.getCity());
                    //System.out.println("AppCityName------------------"+AppConstant.CITY_NAME);


                }
            });
        }


        @Override
        public int getItemCount() {
            return cityLists.size();
        }

        @Override
        public Filter getFilter() {
            return cityNameFilter;
        }

        private Filter cityNameFilter = new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<CityList> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(cityListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (CityList item : cityListFull) {
                        if (item.getCity().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                cityLists.clear();
                cityLists.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvCityName;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvCityName = (TextView)itemView.findViewById(R.id.tvCityName);
            }
        }
    }

    //category name adapter-----------------------------------
    public class CategoryNameAdapter extends RecyclerView.Adapter<CategoryNameAdapter.ViewHolder> implements Filterable {
        Context mContext;
        List<Category> categoryList;
        private List<Category> categoryListFull;

        public CategoryNameAdapter(Context mContext, List<Category> categoryList){
            this.mContext = mContext;
            this.categoryList = categoryList;
            categoryListFull = new ArrayList<>(categoryList);

        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.category_name, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Category category = categoryList.get(position);
            holder.tvCategoryName.setText(category.getCategory_name());
            //System.out.println("category-name--------------------------------"+category.getCategory_name());

            holder.tvCategoryName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.create().hide();
                    String replaceText = category.getCategory_name();
                    String replaceCategoryName = replaceText.replace(' ', '-');
                    AppConstant.SEARCH_CATEGORY_NAME = replaceCategoryName;
                    tvSelectCategory.setText(category.getCategory_name());

                    Chip chip = new Chip(mContext);
                    chip.setText(categoryList.get(position).getCategory_name());
                    chip.setCloseIconVisible(true);
                    chip.setCheckable(false);
                    chip.setClickable(false);
                    chipGroup.addView(chip);

                    Chip chip1 = new Chip(mContext);
                    chip1.setText(categoryList.get(position).getId());
                    chip1.setCloseIconVisible(true);
                    chip1.setCheckable(false);
                    chip1.setClickable(false);
                    //chipGroup.addView(chip1);


                    String value = chip1.getText().toString();
                    mylist.add(value);
                    System.out.println("mylist_________________________"+mylist);


                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //mylist.clear();
                            Chip chip = (Chip)v;
                            chipGroup.removeView(chip);
                            mylist.remove(value);
                            System.out.println("aftermylist_________________________"+mylist);
                        }
                    });


                }
            });
        }

        @Override
        public int getItemCount() {
            return categoryList.size();
        }

        @Override
        public Filter getFilter() {
            return categoryFilter;
        }

        private Filter categoryFilter = new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Category> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(categoryListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Category item : categoryListFull) {
                        if (item.getCategory_name().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                categoryList.clear();
                categoryList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvCategoryName;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvCategoryName = (TextView)itemView.findViewById(R.id.tvCategoryName);
            }
        }
    }
}