package com.example.myapplication.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.detailmovie.DetailMovieActivity;
import com.example.myapplication.model.HomeMovieResponse;
import com.example.myapplication.model.MovieResponse;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private ArrayList<HomeMovieResponse.Results> results;
    private Context context;

    public HomeAdapter(ArrayList<HomeMovieResponse.Results> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int i) {
        HomeMovieResponse.Results model = results.get(i);

        final int position = i;

        holder.getTvDate().setText(model.getReleaseDate());
        holder.getTvDesc().setText(model.getOverview());
        holder.getTvTitle().setText(model.getTitle());

        if (model.getPosterPath() != null) {
            Glide.with(context)
                    .load("http://image.tmdb.org/t/p/w185/".concat(model.getPosterPath()))
                    .thumbnail(Glide.with(context).load(R.drawable.loading))
                    .into(holder.getIvMovie());
        }
        holder.getBtnDetail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieResponse.Results movieResponse = new MovieResponse.Results();
                movieResponse.setTitle(results.get(position).getTitle());
                movieResponse.setBackdropPath(results.get(position).getBackdropPath());
                movieResponse.setOverview(results.get(position).getOverview());
                movieResponse.setReleaseDate(results.get(position).getReleaseDate());
                movieResponse.setVoteAverage(results.get(position).getVoteAverage());
                movieResponse.setVoteCount(results.get(position).getVoteCount());
                movieResponse.setReleaseDate(results.get(position).getReleaseDate());
                movieResponse.setPosterPath(results.get(position).getPosterPath());
                movieResponse.setPopularity(results.get(position).getPopularity());
                movieResponse.setId(results.get(position).getId());


                Intent intent = new Intent(v.getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResponse);

                v.getContext().startActivity(intent);
            }
        });

        holder.getBtnShare().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "check our new movie" + " " + results.get(position).getTitle());
                v.getContext().startActivity(shareIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMovie;

        TextView tvTitle;

        TextView tvDesc;

        TextView tvDate;

        Button btnDetail;

        Button btnShare;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            ButterKnife.setDebug(true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MovieResponse.Results movieResponse = new MovieResponse.Results();
                    movieResponse.setTitle(results.get(getAdapterPosition()).getTitle());
                    movieResponse.setBackdropPath(results.get(getAdapterPosition()).getBackdropPath());
                    movieResponse.setOverview(results.get(getAdapterPosition()).getOverview());
                    movieResponse.setReleaseDate(results.get(getAdapterPosition()).getReleaseDate());
                    movieResponse.setVoteAverage(results.get(getAdapterPosition()).getVoteAverage());
                    movieResponse.setVoteCount(results.get(getAdapterPosition()).getVoteCount());
                    movieResponse.setReleaseDate(results.get(getAdapterPosition()).getReleaseDate());
                    movieResponse.setPosterPath(results.get(getAdapterPosition()).getPosterPath());
                    movieResponse.setPopularity(results.get(getAdapterPosition()).getPopularity());
                    movieResponse.setId(results.get(getAdapterPosition()).getId());


                    Intent intent = new Intent(v.getContext(), DetailMovieActivity.class);
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieResponse);

                    v.getContext().startActivity(intent);
                }
            });
        }

        ImageView getIvMovie() {
            return ivMovie;
        }

        TextView getTvTitle() {
            return tvTitle;
        }

        TextView getTvDesc() {
            return tvDesc;
        }

        TextView getTvDate() {
            return tvDate;
        }

        public Button getBtnDetail() {
            return btnDetail;
        }

        public Button getBtnShare() {
            return btnShare;
        }
    }
}
