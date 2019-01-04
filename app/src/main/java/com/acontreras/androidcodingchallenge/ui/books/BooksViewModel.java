package com.acontreras.androidcodingchallenge.ui.books;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.acontreras.androidcodingchallenge.data.Book;
import com.acontreras.androidcodingchallenge.repository.BooksRepository;

public class BooksViewModel extends ViewModel {
    private LiveData<Book[]> books;
    private BooksRepository booksRepository;

    public BooksViewModel() {

    }

    public void init(BooksRepository repo) {
        this.booksRepository = repo;

        if (!booksRepository.hasErrored() && books != null) {
            return;
        }

        books = booksRepository.getBooks();
    }

    public LiveData<Book[]> getBooks() {
        return this.books;
    }

}
