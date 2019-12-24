package com.example.myapplication.news;

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

import java.util.ArrayList;
import java.util.List;


public class FragmentNews extends Fragment {

    private List<movie_item> movieList;
    private HomeAdapter adapter;
    private RecyclerView recyclerView;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieList=new ArrayList<>();

        movieList.add(
                new movie_item("1/1/2012","Avenger","R.drawable.avenger","this is a movie from Marvel Studio")
        );
        movieList.add(
                new movie_item("1/1/2012","Avenger","R.drawable.avenger","this is a movie from Marvel Studio")
        );
        movieList.add(
                new movie_item("1/1/2012","Avenger","R.drawable.avenger","this is a movie from Marvel Studio")
        );
        movieList.add(
                new movie_item("1/1/2012","Avenger","R.drawable.avenger","this is a movie from Marvel Studio")
        );
        movieList.add(
                new movie_item("1/1/2012","Avenger","R.drawable.avenger","this is a movie from Marvel Studio")
        );
        movieList.add(
                new movie_item("1/1/2012","Avenger","R.drawable.avenger","this is a movie from Marvel Studio")
        );


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