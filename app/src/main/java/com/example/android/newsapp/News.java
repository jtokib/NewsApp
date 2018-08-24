package com.example.android.newsapp;

public class News {
    private String mSection;
    private String mDate;
    private String mTitle;
    private String mUrl;
    private String mAuthor;

    public News(String section, String date, String title, String url) {
        mSection = section;
        mDate = date;
        mTitle = title;
        mUrl = url;
    }

    public News(String section, String date, String title, String author, String url) {
        mSection = section;
        mDate = date;
        mTitle = title;
        mAuthor = author;
        mUrl = url;
    }

    public String getSection() {
        return mSection;
    }

    public String getDate() {
        return mDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getUrl() {
        return mUrl;
    }

}
