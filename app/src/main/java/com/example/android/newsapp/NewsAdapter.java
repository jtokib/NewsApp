package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<News> news) {
        super(context, resource, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        final News newsItem = getItem(position);

        TextView sectionNameTV = listItemView.findViewById(R.id.section_name);
        sectionNameTV.setText(newsItem.getSection());

        TextView articleTitleTV = listItemView.findViewById(R.id.article_title);
        articleTitleTV.setText(newsItem.getTitle());

        TextView dateTV = listItemView.findViewById(R.id.date);
        dateTV.setText(newsItem.getDate());

        return listItemView;
    }
}
