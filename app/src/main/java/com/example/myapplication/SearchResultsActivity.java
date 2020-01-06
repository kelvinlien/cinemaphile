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
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class SearchResultsActivity extends AppCompatActivity {
    public static final String TAG = SearchResultsActivity.class.getSimpleName();
    public ArrayList<movie_item> mMovies = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private ArrayList<movie_item> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_search_results);
        ButterKnife.bind(this);
        movieList = new ArrayList<>();
        mAdapter = new HomeAdapter(mMovies, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchResultsActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    protected void onStart() {

        super.onStart();
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String title = intent.getStringExtra(SearchManager.QUERY);
            getMovies(title);
        }

    }

    private void getMovies(String title) {
        TMDB.getInstance().getSearchResults(title, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int a, Header[] hd, Throwable tw, JSONObject response) {
                System.out.println(response);
            }
//            public void onFailure(int statusCode, Header[] headers, JSONObject response, IOException e) {
//                e.printStackTrace();
//            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    mMovies = TMDB.getInstance().processResults(response);
                    mAdapter = new HomeAdapter(mMovies, getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
