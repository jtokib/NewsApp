package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String _url;

    public NewsLoader(Context context, String url) {
        super(context);
        _url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if(_url == null) {
            return null;
        }
        return QueryUtils.fetchNewsData(_url);
    }
}
