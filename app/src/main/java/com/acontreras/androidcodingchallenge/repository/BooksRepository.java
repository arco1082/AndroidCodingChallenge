package com.acontreras.androidcodingchallenge.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.acontreras.androidcodingchallenge.api.BooksInterface;
import com.acontreras.androidcodingchallenge.data.Book;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksRepository {
    private BooksInterface webService;


    private boolean mHasErrored = false;

    public BooksRepository(BooksInterface webservice) {
        this.webService = webservice;
    }

    public LiveData<Book[]> getBooks() {

        final MutableLiveData<Book[]> data = new MutableLiveData<>();
        webService.getBooks().enqueue(new Callback<Book[]>() {
            @Override
            public void onResponse(Call<Book[]> call, Response<Book[]> response) {
                mHasErrored = false;
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Book[]> call, Throwable t) {
                mHasErrored = true;
                data.setValue(null);
            }

        });

        return data;
    }

    public boolean hasErrored() {
        return mHasErrored;
    }
}
