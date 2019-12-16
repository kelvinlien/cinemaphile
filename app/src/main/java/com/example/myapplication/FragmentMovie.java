package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentMovie extends Fragment {
    View view;
    public ArrayAdapter<String> adapter; //search adapter
    GridView film;
    public FragmentMovie() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_fragment, container, false);
        /*film = view.findViewById(R.id.search_film);
        ArrayList<String> arrayFilm = new ArrayList<>();
        arrayFilm.addAll(Arrays.asList(getResources().getStringArray(R.array.my_films)));
        adapter = new ArrayAdapter<String>(

                android.R.layout.simple_list_item_1,
                arrayFilm
        );*/
        return view;
    }
}
