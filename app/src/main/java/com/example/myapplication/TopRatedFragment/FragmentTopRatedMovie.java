package com.example.myapplication.TopRatedFragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.HomeAdapter;
import com.example.myapplication.model.movie_item;
import com.example.myapplication.network.TMDB;
import com.example.myapplication.utils.PaginationScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FragmentTopRatedMovie extends Fragment {

    private static final int PAGE_START = 1;
    private ArrayList<movie_item> movieList;
    private HomeAdapter adapter;
    private RecyclerView recyclerView;
    private CoordinatorLayout coordinatorLayout;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private View view;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieList = new ArrayList<>();
        adapter = new HomeAdapter(movieList, getContext());
        loadFirstPage();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressBar = view.findViewById(R.id.progressBar);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        recyclerView = view.findViewById(R.id.rv_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(() -> loadNextPage(), 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_fragment, container, false);
        return view;
    }

    private void loadNextPage() {

        TMDB.getInstance().getTopRatedMovies(currentPage, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int a, Header[] hd, Throwable tw, JSONObject response) {
                System.out.println("popular movie call went wrong: " + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                ArrayList<movie_item> results = null;
                try {
                    results = TMDB.getInstance().processResults(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.addAll(results);

                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }
        });
    }

    private void loadFirstPage() {

        TMDB.getInstance().getTopRatedMovies(1, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int a, Header[] hd, Throwable tw, JSONObject response) {
                System.out.println("popular movie call went wrong: " + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                ArrayList<movie_item> results = null;

                try {
                    results = TMDB.getInstance().processResults(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
                adapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }
        });

    }
}