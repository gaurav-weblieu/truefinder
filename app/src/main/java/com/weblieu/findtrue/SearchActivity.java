package com.weblieu.findtrue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.weblieu.findtrue.adapter.CategoryAdapter;
import com.weblieu.findtrue.adapter.CategoryDetailsAdapter;
import com.weblieu.findtrue.adapter.SearchingAdapter;
import com.weblieu.findtrue.model.Category;
import com.weblieu.findtrue.model.CategoryData;
import com.weblieu.findtrue.model.CityList;
import com.weblieu.findtrue.model.CityModel;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.search.Data;
import com.weblieu.findtrue.search.SearchCategory;
import com.weblieu.findtrue.search.Vendor;
import com.weblieu.findtrue.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

import de.mrapp.android.dialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    List<CityList> cityLists;
    List<Category> categoryItemList = null;
    private ApiInterface apiInterface;
    private TextView tvFindCity, tvFindCategory;
    private SpinnerCityAdapter spinnerCityAdapter;
    RecyclerView mRecyclerView, recyclerViewCategoryName;
    MaterialDialog.Builder builder;
    ImageView ivBackScreen;
    private ImageView ivSearchBtn;
    private RecyclerView recyclerViewSearching;
    private SearchingAdapter searchingAdapter;
    private List<Vendor> listVendor;
    private ProgressBar progressBar3;
    private TextView tvMessage;

    CategoryNameAdapter categoryNameAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tvFindCity = (TextView) findViewById(R.id.tvFindCity);
        tvFindCategory = (TextView) findViewById(R.id.tvFindCategory);
        ivBackScreen = (ImageView)findViewById(R.id.ivBackScreen);
        ivSearchBtn = (ImageView)findViewById(R.id.ivSearchBtn);
        recyclerViewSearching = (RecyclerView)findViewById(R.id.recyclerViewSearching);
        progressBar3 = (ProgressBar)findViewById(R.id.progressBar3);
        tvMessage = (TextView)findViewById(R.id.tvMessage);

        cityLists = new ArrayList<>();

        listVendor = new ArrayList<>();
        recyclerViewSearching.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSearching.setHasFixedSize(true);

        ivBackScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvFindCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new MaterialDialog.Builder(SearchActivity.this);
                builder.setView(R.layout.change_city_dialog);
                builder.show();
                mRecyclerView = (RecyclerView) builder.create().findViewById(R.id.recyclerViewCityName);
                ProgressBar mProgressBar = (ProgressBar)builder.create().findViewById(R.id.mProgressBar);
                EditText editSearch = (EditText)builder.create().findViewById(R.id.editSearch);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                mRecyclerView.setHasFixedSize(true);
                ImageView imageClose = (ImageView)builder.create().findViewById(R.id.imageViewClose);
                List<CityList> cityLists = new ArrayList<>();
                spinnerCityAdapter = new SpinnerCityAdapter(SearchActivity.this, cityLists);
                apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
                apiInterface.getCity().enqueue(new Callback<CityModel>() {
                    @Override
                    public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                        if (response.isSuccessful()){
                            mProgressBar.setVisibility(View.GONE);
                            System.out.println("type=========------------------------"+response.body().getType());
                            List<CityList> models = response.body().getData();
                            for (CityList list : models){
                                cityLists.add(list);
                                spinnerCityAdapter = new SpinnerCityAdapter(SearchActivity.this, cityLists);
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

        tvFindCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new MaterialDialog.Builder(SearchActivity.this);
                builder.setView(R.layout.category_dialog);
                builder.show();
                ImageView imageViewClose = (ImageView)builder.create().findViewById(R.id.imageViewClose);
                recyclerViewCategoryName = (RecyclerView)builder.create().findViewById(R.id.recyclerViewCategoryName);
                EditText editSearch = (EditText)builder.create().findViewById(R.id.editSearch);
                ProgressBar mProgressBar = (ProgressBar)builder.create().findViewById(R.id.mProgressBar);
                recyclerViewCategoryName.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
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
                                categoryNameAdapter = new CategoryNameAdapter(SearchActivity.this, categoryItemList);
                                recyclerViewCategoryName.setAdapter(categoryNameAdapter);

                                System.out.println("category+++++--------------------------" + categoryItemList);
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
        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        ivSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar3.setVisibility(View.VISIBLE);
                //System.out.println("SEARCH_CATEGORY_NAME----------------"+AppConstant.SEARCH_CATEGORY_NAME);
                apiInterface.searchProduct(AppConstant.SEARCH_CATEGORY_NAME, AppConstant.SEARCH_CITY_NAME).enqueue(new Callback<SearchCategory>() {
                    @Override
                    public void onResponse(Call<SearchCategory> call, Response<SearchCategory> response) {
                        if (response.isSuccessful()){
                            progressBar3.setVisibility(View.GONE);

                            //System.out.println("message============================"+response.body().getMessage());
                            int results = response.body().getData().getOffSet();
                            //System.out.println("resultTest--------------------------"+results);
                            List<Vendor> vendors = response.body().getData().getVendor();
                            System.out.println("vendor===================="+vendors.size());
                            listVendor.clear();
                            if (vendors.size() == 0){
                                listVendor.clear();
                                tvMessage.setVisibility(View.VISIBLE);
                                searchingAdapter = new SearchingAdapter(SearchActivity.this, listVendor);
                                recyclerViewSearching.setAdapter(searchingAdapter);
                                searchingAdapter.notifyDataSetChanged();
                            }else {
                                for (Vendor data : vendors){
                                    tvMessage.setVisibility(View.GONE);
                                    System.out.println("data------"+data.getId());
                                    listVendor.add(data);
                                    System.out.println("listVendor=========================="+listVendor);
                                    searchingAdapter = new SearchingAdapter(SearchActivity.this, listVendor);
                                    recyclerViewSearching.setAdapter(searchingAdapter);
                                    searchingAdapter.notifyDataSetChanged();

                                }
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<SearchCategory> call, Throwable t) {
                        progressBar3.setVisibility(View.GONE);
                        tvMessage.setVisibility(View.VISIBLE);

                    }
                });
            }
        });
    }

    //city name adapter------------------------------------
    public class SpinnerCityAdapter extends RecyclerView.Adapter<SpinnerCityAdapter.ViewHolder> implements Filterable{
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
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.city_list, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CityList cityList = cityLists.get(position);
            System.out.println("city_name-------------------------"+cityList.getCity());
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
                    tvFindCategory.setText(category.getCategory_name());
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