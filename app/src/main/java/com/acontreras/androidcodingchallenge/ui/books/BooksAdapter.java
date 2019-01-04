package com.acontreras.androidcodingchallenge.ui.books;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acontreras.androidcodingchallenge.R;
import com.acontreras.androidcodingchallenge.data.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {


    private List<Book> mBooks = new ArrayList<>();
    private int mRowLayout;

    public BooksAdapter(int rowLayout) {
        this.mRowLayout = rowLayout;
    }

    public void swapList(List<Book> newList) {
        mBooks.clear();
        if (newList != null) {
            mBooks.addAll(newList);
        }
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mRowLayout, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookViewHolder  holder, final int position) {

        holder.title.setText(mBooks.get(position).getTitle());
        holder.author.setText(mBooks.get(position).getAuthor());
        Picasso.with(holder.thumbNail.getContext())
                .load((mBooks.get(position).getImageURL()))
                .into(holder.thumbNail);

    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView author;
        ImageView thumbNail;

        public BookViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            author = (TextView) itemView.findViewById(R.id.item_author);
            thumbNail = (ImageView) itemView.findViewById(R.id.item_image);

        }

    }
}
