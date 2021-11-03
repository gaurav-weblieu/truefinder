package com.weblieu.findtrue.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liuting.sliderlayout.SliderLayout;
import com.weblieu.findtrue.CategoryDetailsActivity;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.model.Category;
import com.weblieu.findtrue.model.DataType;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewTypeAdapter extends RecyclerView.Adapter {

    private ArrayList<DataType> dataSet;
    Context mContext;
    List<Category> categoryItemList;
    int total_types;
    private ApiInterface apiInterface;


    public HomeViewTypeAdapter(ArrayList<DataType> dataViewType, Context context) {
        this.dataSet = dataViewType;
        this.mContext = context;
        total_types = dataViewType.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case DataType.SLIDER_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_image, parent, false);
                return new ImageSliderViewHolder(view);

            case DataType.CATEGORIES_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_type, parent, false);
                return new CategoriesViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        switch (dataSet.get(position).type) {
            case 0:
                return DataType.SLIDER_IMAGE_TYPE;
            case 1:
                return DataType.CATEGORIES_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataType serviceType = dataSet.get(position);
        if (serviceType!=null){
            switch (serviceType.type){
                case DataType.SLIDER_IMAGE_TYPE:
                    ((ImageSliderViewHolder)holder).imageSlider.setPictureIndex(0);
                    List<Object> listJingDong = new ArrayList<>();
                    listJingDong.add(R.drawable.business_services);
                    listJingDong.add(R.drawable.home_service_2);
                    ((ImageSliderViewHolder)holder).imageSlider.setList(listJingDong);

                    break;

                    case DataType.CATEGORIES_TYPE:
                        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
                        categoryItemList = new ArrayList<>();
                        LinearLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 2);
                        ((CategoriesViewHolder)holder).recyclerCategory.setLayoutManager(linearLayoutManager);
                        ((CategoriesViewHolder)holder).recyclerCategory.hasFixedSize();
                        apiInterface.getCategory().enqueue(new Callback<List<Category>>() {
                            @Override
                            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                                if (response.isSuccessful()){
                                    categoryItemList = response.body();
                                    CategoryAdapter categoryAdapter = new CategoryAdapter(mContext, categoryItemList);
                                    ((CategoriesViewHolder)holder).recyclerCategory.setAdapter(categoryAdapter);

                                    System.out.println("category+++++--------------------------" + categoryItemList);

                                }

                            }

                            @Override
                            public void onFailure(Call<List<Category>> call, Throwable t) {

                            }
                        });
                        break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    /*-------------------------ImageSliderViewHolder-------------------------------*/
    public static class ImageSliderViewHolder extends RecyclerView.ViewHolder {
        SliderLayout imageSlider;

        public ImageSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSlider = (SliderLayout) itemView.findViewById(R.id.imageSlider);
        }
    }


    /*-------------------------CategoriesViewHolder-------------------------------*/
    public static class CategoriesViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerCategory;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerCategory = (RecyclerView)itemView.findViewById(R.id.recyclerCategory);

        }
    }
}