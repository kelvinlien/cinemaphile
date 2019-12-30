package com.example.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.HomeAdapter;
import com.example.myapplication.model.movie_item;
import com.example.myapplication.network.TMDB;
import com.example.myapplication.service.MovieService;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchResultsActivity extends AppCompatActivity {
    public static final String TAG = SearchResultsActivity.class.getSimpleName();
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    public ArrayList<movie_item> mMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_search_results);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String title = intent.getStringExtra(SearchManager.QUERY);
            getMovies(title);
        }
    }

    private void getMovies(String title) {
        TMDB.getInstance().getSearchResults(title, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int a, Header[] hd, Throwable tw, JSONObject response)
            {
                System.out.println(response);
            }
//            public void onFailure(int statusCode, Header[] headers, JSONObject response, IOException e) {
//                e.printStackTrace();
//            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    mMovies = TMDB.getInstance().processResults(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SearchResultsActivity.this.runOnUiThread(() -> {
                    mAdapter = new HomeAdapter(mMovies,getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchResultsActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                });
            }
        });
    }
}
