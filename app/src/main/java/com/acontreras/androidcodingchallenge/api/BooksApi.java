package com.acontreras.androidcodingchallenge.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BooksApi {
    private static BooksApi sInstance;
    private BooksInterface mInterface;

    private BooksApi(String endpoint) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        OkHttpClient again = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(again)
                .build();
        mInterface =  retrofit.create(BooksInterface.class);
    }

    public static BooksApi getInstance(String endpoint) {
        if (sInstance == null) {
            synchronized (BooksApi.class) {
                if (sInstance == null) {
                    sInstance = new BooksApi(endpoint);
                }
            }
        }
        return sInstance;
    }

    public BooksInterface getBookInterface() {
        return mInterface;
    }
}
