package com.weblieu.findtrue.repositry;

import com.weblieu.findtrue.service.AppConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {
    private static Retrofit instance;

    public static Retrofit getInstance() {

        return instance == null ? instance = new Retrofit.Builder()
                .baseUrl("https://test.cashfree.com/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build() : instance;

    }
}
