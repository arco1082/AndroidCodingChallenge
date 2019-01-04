package com.acontreras.androidcodingchallenge.api;
import com.acontreras.androidcodingchallenge.data.Book;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface BooksInterface {

    @GET("books.json")
    @Headers("Content-type: application/json")
    Call<Book[]> getBooks();

}