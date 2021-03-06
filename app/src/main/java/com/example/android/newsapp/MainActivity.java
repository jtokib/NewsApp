package com.example.android.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    private static final int NEWS_LOADER_ID = 1;
    private static final String GUARDIAN_REQUEST_URL = "http://content.guardianapis.com/search";
    private static final String GUARDIAN_SHOW_TAGS = "contributor";
    private static final String GUARDIAN_API_KEY = "f65a7443-ad78-4f02-9516-204694a6adb5";
    private NewsAdapter mAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Replace app name with drawable logo
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setLogo(R.drawable.theguardian_op_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Find reference the listview in the layout
        ListView newsListView = findViewById(R.id.list);

        //find the empty view
        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        //create a new adapter that takes an empty list as an input
        mAdapter = new NewsAdapter(this, 0, new ArrayList<News>());

        newsListView.setAdapter(mAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News newsItem = mAdapter.getItem(position);
                String url = newsItem.getUrl();
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            View loadingBar = findViewById(R.id.loading_spinner);
            loadingBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.NO_CONNECTION);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
//        return new NewsLoader(this, GUARDIAN_REQUEST_URL);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String searchQuery = sharedPreferences.getString(getString(R.string.search_key), getString(R.string.search_default));

        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", searchQuery);
        uriBuilder.appendQueryParameter("show-tags", GUARDIAN_SHOW_TAGS);
        uriBuilder.appendQueryParameter("api-key", GUARDIAN_API_KEY);

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        View mProgress = findViewById(R.id.loading_spinner);
        mProgress.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.NO_ARTICLES);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
