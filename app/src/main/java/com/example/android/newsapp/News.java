package com.example.android.newsapp;

public class News {
    private String _section;
    private String _date;
    private String _title;
    private String _url;

    public News(String section, String date, String title, String url) {
        _section = section;
        _date = date;
        _title = title;
        _url = url;
    }

    public String getSection() { return _section; }
    public String getDate() { return _date; }
    public String getTitle() { return _title; }
    public String getUrl() { return _url; }

}
