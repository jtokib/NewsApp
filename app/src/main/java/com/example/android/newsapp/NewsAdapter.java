package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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

        TextView authorTV = listItemView.findViewById(R.id.author);
        String author = newsItem.getAuthor();
        String byAuthor = byAuthor(author);
        authorTV.setText(byAuthor);

        TextView dateTV = listItemView.findViewById(R.id.date);
        String dateFormatted = dateFormat(newsItem.getDate());
        dateTV.setText(dateFormatted);

        return listItemView;
    }

    private String byAuthor(String author) {
        if (author != "") {
            return "By " + author;
        } else {
            return "";
        }
    }

    private String dateFormat(String date) {
        String dateChop = date.substring(0,date.indexOf("T"));
        return dateChop;
    }
}
