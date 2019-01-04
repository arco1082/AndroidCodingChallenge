package com.acontreras.androidcodingchallenge.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Book implements Serializable {

    protected String title;
    protected String imageURL;
    protected String author;

    public Book(String titleVal, String imageVal, String authorVal) {
        this.title = titleVal;
        this.imageURL = imageVal;
        this.author = authorVal;
    }

    //Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getImageURL() { return imageURL; }

    //Setters
    public void setTitle(String val) { this.title = val; }
    public void setAuthor(String val) { this.author = val; }
    public void setImageUrl(String val) { this.imageURL = val; }

}