package com.weblieu.findtrue.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.weblieu.findtrue.CategoryDetailsActivity;
import com.weblieu.findtrue.MainActivity;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.model.Category;
import com.weblieu.findtrue.model.CityList;
import com.weblieu.findtrue.model.CityModel;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.service.AppConfig;
import com.weblieu.findtrue.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.mrapp.android.dialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    Context mContext;
    List<Category> categoryList;
    private ApiInterface apiInterface;
    CityAdapter cityAdapter;
    MaterialDialog.Builder builder;

    public CategoryAdapter(Context mContext, List<Category> categoryList){
        this.mContext = mContext;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Category category = categoryList.get(position);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.linear_one.setBackgroundColor(currentColor);
        //System.out.println("category_name--------------------------------"+category.getCategory_name());
        holder.tvCategoryName.setText(category.getCategory_name());
        Glide.with(mContext).load(AppConfig.IMAGE_URL + category.getCategory_icon()).into(holder.ivCategoryIcon);

        MainActivity.tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new MaterialDialog.Builder(mContext);
                builder.setView(R.layout.change_city_dialog);
                builder.show();
                RecyclerView mRecyclerView = (RecyclerView) builder.create().findViewById(R.id.recyclerViewCityName);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                mRecyclerView.setHasFixedSize(true);
                ImageView imageClose = (ImageView)builder.create().findViewById(R.id.imageViewClose);
                ProgressBar mProgressBar = (ProgressBar)builder.create().findViewById(R.id.mProgressBar);

                List<CityList> cityLists = new ArrayList<>();
                cityAdapter = new CityAdapter(mContext, cityLists);
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
                                cityAdapter = new CityAdapter(mContext, cityLists);
                                mRecyclerView.setAdapter(cityAdapter);
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
            }
        });

        holder.linear_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CategoryDetailsActivity.class);
                intent.putExtra("CATEGORY_NAME", category.getCategory_name());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivCategoryIcon;
        TextView tvCategoryName;
        LinearLayout linear_one;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCategoryIcon = (ImageView)itemView.findViewById(R.id.ivCategoryIcon);
            tvCategoryName = (TextView)itemView.findViewById(R.id.tvCategoryName);
            linear_one = (LinearLayout)itemView.findViewById(R.id.linear_one);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
        }
    }

    //city adapter class

    public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
        Context context;
        List<CityList> cityLists;

        public CityAdapter(Context context, List<CityList> cityLists) {
            this.context = context;
            this.cityLists = cityLists;
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
                    AppConstant.CITY_NAME = cityList.getCity_alias();
                    System.out.println("AppCityName------------------"+ AppConstant.CITY_NAME);
                    MainActivity.tvLocation.setText(cityList.getCity());
                    builder.create().hide();

                }
            });
        }


        @Override
        public int getItemCount() {
            return cityLists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvCityName;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvCityName = (TextView)itemView.findViewById(R.id.tvCityName);
            }
        }
    }
}