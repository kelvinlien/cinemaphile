package com.example.myapplication.TrendingFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.HomeAdapter;
import com.example.myapplication.model.movie_item;
import com.example.myapplication.network.TMDB;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class FragmentTrendingMovie extends Fragment {

    HomeAdapter adapter;
    public ArrayList<movie_item>  movieList;
    RecyclerView recyclerView;

    public FragmentTrendingMovie() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_fragment, container, false);
        recyclerView=view.findViewById(R.id.rv_movie2);
        adapter=new HomeAdapter(movieList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieList=new ArrayList<>();
        TMDB.getInstance().getPopularMovie(1, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int a, Header[] hd, Throwable tw, JSONObject response)
            {
                System.out.println("popular movie call went wrong: " + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    movieList = TMDB.getInstance().processResults(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter=new HomeAdapter(movieList,getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
