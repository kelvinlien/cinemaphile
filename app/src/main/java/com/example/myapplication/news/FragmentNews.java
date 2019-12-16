package com.example.myapplication.news;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.R;
import com.example.myapplication.adapter.HomeAdapter;
import com.example.myapplication.model.HomeMovieResponse;
import com.example.myapplication.network.ServiceGenerator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class FragmentNews extends Fragment implements NewsFilm{

    private ArrayList<HomeMovieResponse.Results> results = new ArrayList<>();
    private HomeAdapter adapter;
    private NewsPresenter presenter;
    private AlertDialog alertDialog;

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;

    public FragmentNews() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);

        // Inflate the layout for this fragment
        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMovie.setLayoutManager(linearLayoutManager);
        rvMovie.setHasFixedSize(true);
        adapter = new HomeAdapter(results, getActivity());
        rvMovie.setAdapter(adapter);

        alertDialog = new SpotsDialog.Builder().setContext(getActivity()).build();
        ServiceGenerator serviceGenerator = new ServiceGenerator();
        presenter = new NewsPresenter(this, serviceGenerator, getActivity());


        presenter.upcoming();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.upcoming();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        alertDialog.show();
    }

    @Override
    public void hideLoading() {
        alertDialog.dismiss();
    }

    @Override
    public void showMovieResponseError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMovieResponseEmpty() {
        Toast.makeText(getActivity(), R.string.empty_response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMovieReponseSuccess(HomeMovieResponse body) {
        results.clear();
        results.addAll(body.getResults());
        adapter.notifyDataSetChanged();

    }

}