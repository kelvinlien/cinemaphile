package com.example.myapplication.TopRatedFragment;

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


public class FragmentTopRatedMovie extends Fragment {

    private List<movie_item> movieList;
    private HomeAdapter adapter;
    private RecyclerView recyclerView;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieList=new ArrayList<>();
        TMDB.getInstance().getTopRatedMovies(1, new JsonHttpResponseHandler() {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.news_fragment,container,false);

        recyclerView=  view.findViewById(R.id.rv_movie);
        adapter=new HomeAdapter(movieList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        return view;
    }
}