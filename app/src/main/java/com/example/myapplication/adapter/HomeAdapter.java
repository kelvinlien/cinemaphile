package com.example.myapplication.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.detailmovie.DetailMovieActivity;
import com.example.myapplication.model.movie_item;
import com.example.myapplication.network.TMDB;


import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int POSTER_SIZE = 1;
    private ArrayList<movie_item> movieResults;
    private Context context;
    private TMDB tmdb = TMDB.getInstance();
    private boolean isLoadingAdded = false;

    private movie_item result;

    public HomeAdapter(ArrayList<movie_item> results, Context context) {
        this.movieResults = results;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.movie_item, parent, false);
        Button detailButton = (Button) v1.findViewById(R.id.btn_detail);
        detailButton.setOnClickListener(v -> {
            try {
                LinearLayout layout = (LinearLayout) v.getParent().getParent();
                LinearLayout parentLinear = (LinearLayout) v.getParent().getParent().getParent();
                TextView ID = parentLinear.findViewById(R.id.movie_id);
                String id = (String) ID.getText();
                TextView Title = layout.findViewById(R.id.tv_title);
                String title = (String) Title.getText();
                TextView Desc = layout.findViewById(R.id.tv_desc);
                String overview = (String) Desc.getText();
                TextView Date = layout.findViewById(R.id.tv_date);
                String date = (String) Date.getText();
                Intent i = new Intent(context, DetailMovieActivity.class);
                i.putExtra("title", title);
                i.putExtra("id", id);
                i.putExtra("overview", overview);
                i.putExtra("date", date);
                context.startActivity(i);
            } catch (Exception e) {
                System.out.println("something went wrong: " + e);
            }
        });
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        result = movieResults.get(position); // Movie
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;

                movieVH.movieId.setText(result.getId());
                movieVH.tvTitle.setText(result.getTitle());
                movieVH.tvDate.setText(result.getReleaseDate());
                movieVH.tvDesc.setText(result.getOverView());
                tmdb.getImage(result.getPoster(), POSTER_SIZE, movieVH.ivMovie);

                break;

            case LOADING:
//                Do nothing
                break;

        }
    }

    @Override
    public int getItemCount() {
        return movieResults == null ? 0 : movieResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(movie_item r) {
        movieResults.add(r);
        notifyItemInserted(movieResults.size() - 1);
    }

    public void addAll(ArrayList<movie_item> moveResults) {
        for (movie_item result : moveResults) {
            add(result);
        }
    }

    public void remove(movie_item r) {
        int position = movieResults.indexOf(r);
        if (position > -1) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new movie_item());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieResults.size() - 1;
        movie_item result = getItem(position);

        if (result != null) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public movie_item getItem(int position) {
        return movieResults.get(position);
    }

    class MovieVH extends RecyclerView.ViewHolder {

        TextView movieId;

        ImageView ivMovie;

        TextView tvTitle;

        TextView tvDesc;

        TextView tvDate;

        Button btnDetail;

        Button btnShare;

        public MovieVH(@NonNull View itemView) {
            super(itemView);
            movieId = itemView.findViewById(R.id.movie_id);
            ivMovie = itemView.findViewById(R.id.iv_movie);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvDate = itemView.findViewById(R.id.tv_date);
            btnDetail = itemView.findViewById(R.id.btn_detail);
            btnShare = itemView.findViewById(R.id.btn_share);
        }


        ImageView getIvMovie() {
            return ivMovie;
        }

        TextView getMovieId(){
            return movieId;
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

    class LoadingVH extends RecyclerView.ViewHolder {

        LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
