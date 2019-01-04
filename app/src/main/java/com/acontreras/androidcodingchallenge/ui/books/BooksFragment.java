package com.acontreras.androidcodingchallenge.ui.books;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.acontreras.androidcodingchallenge.R;
import com.acontreras.androidcodingchallenge.api.BooksApi;
import com.acontreras.androidcodingchallenge.data.Book;
import com.acontreras.androidcodingchallenge.repository.BooksRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BooksFragment extends Fragment {

    private BooksViewModel mViewModel;

    private BooksAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgress;
    private TextView mMessage;
    private Button mRetry;
    private List<Book> mBooks = new ArrayList<>();
    private boolean mIsObserving = false;

    public static BooksFragment newInstance() {
        return new BooksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.books_fragment, container, false);
        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view);
        mProgress = (ProgressBar) fragmentView.findViewById(R.id.progress);
        mMessage = (TextView) fragmentView.findViewById(R.id.empty_view);
        mRetry = (Button) fragmentView.findViewById(R.id.bt_retry);

        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFromNetwork();
            }
        });

        if (isNetworkAvailable()) {
            showLoading();
            setupRecyclerView();
        } else {
            showNetworkError(getString(R.string.network_error));
        }


        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isNetworkAvailable()) {
            loadFromNetwork();
        }

    }

    private void setupRecyclerView() {
        LinearLayoutManager lLayout = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(lLayout);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new BooksAdapter( R.layout.book_list_item);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void showLoading() {
        mProgress.setVisibility(View.VISIBLE);
        mMessage.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mRetry.setVisibility(View.GONE);
    }

    private void showNetworkError(String error) {

        mRecyclerView.setVisibility(View.GONE);
        mProgress.setVisibility(View.GONE);
        mMessage.setVisibility(View.VISIBLE);
        mRetry.setVisibility(View.VISIBLE);
        mMessage.setText(error);
    }

    private void loadFromNetwork() {
        showLoading();
        mViewModel = ViewModelProviders.of(this).get(BooksViewModel.class);

        mViewModel.init(new BooksRepository(BooksApi.getInstance(getString(R.string.endpoint)).getBookInterface()));
        mViewModel.getBooks().observe(this, new Observer<Book[]>() {
            @Override
            public void onChanged(@Nullable Book[] books) {

                if (books == null || books.length == 0) {
                    showNetworkError(getString(R.string.api_error));
                } else {
                    mBooks.clear();
                    mBooks = new ArrayList<>(Arrays.asList(books));
                    reloadData();
                }

            }
        });
    }

    private void reloadData() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.GONE);
                mMessage.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mAdapter.swapList(mBooks);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
