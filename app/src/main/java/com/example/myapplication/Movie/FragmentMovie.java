package com.example.myapplication.Movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.HomeAdapter;
import com.example.myapplication.model.movie_item;

import java.util.ArrayList;
import java.util.List;


public class FragmentMovie extends Fragment {

    HomeAdapter adapter;
    public List<movie_item>  movieList;
    RecyclerView recyclerView;
    public FragmentMovie() {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       movieList=new ArrayList<>();

        movieList.add(
                new movie_item("1/1/2018","Maze Runner",R.drawable.maze_runner,"this is movie was released in 2018")
        );
        movieList.add(
                new movie_item("1/1/2018","Maze Runner",R.drawable.maze_runner,"this is movie was released in 2018")
        );
        movieList.add(
                new movie_item("1/1/2018","Maze Runner",R.drawable.maze_runner,"this is movie was released in 2018")
        );
        movieList.add(
                new movie_item("1/1/2018","Maze Runner",R.drawable.maze_runner,"this is movie was released in 2018")
        );
        movieList.add(
                new movie_item("1/1/2018","Maze Runner",R.drawable.maze_runner,"this is movie was released in 2018")
        );
        movieList.add(
                new movie_item("1/1/2018","Maze Runner",R.drawable.maze_runner,"this is movie was released in 2018")
        );
        movieList.add(
                new movie_item("1/1/2018","Maze Runner",R.drawable.maze_runner,"this is movie was released in 2018")
        );
        movieList.add(
                new movie_item("1/1/2018","Maze Runner",R.drawable.maze_runner,"this is movie was released in 2018")
        );
        movieList.add(
                new movie_item("1/1/2018","Maze Runner",R.drawable.maze_runner,"this is movie was released in 2018")
        );

    }
}
