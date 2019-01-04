package com.acontreras.androidcodingchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.acontreras.androidcodingchallenge.ui.books.BooksFragment;

public class BooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_activity);
        setTitle(getString(R.string.main_title));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BooksFragment.newInstance())
                    .commitNow();
        }
    }
}
