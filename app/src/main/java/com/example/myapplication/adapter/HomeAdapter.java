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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import com.example.myapplication.detailmovie.DetailMovieActivity;
import  com.example.myapplication.model.movie_item;

import java.util.ArrayList;

import com.example.myapplication.network.TMDB;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<movie_item> movieResults;
    private Context context;
    private TMDB tmdb = TMDB.getInstance();

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int POSTER_SIZE = 1;

    private boolean isLoadingAdded = false;

    private movie_item result;

    public HomeAdapter(ArrayList<movie_item> results, Context context) {
        this.movieResults = results;
        this.context = context;
    }

//    public HomeAdapter(Context context) {
//        this.movieResults = new ArrayList<>();
//        this.context = context;
//    }

    @Override
//    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {         //set onClick event for detail button
//        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false);
//        Button detailButton = (Button)view.findViewById(R.id.btn_detail);
//        detailButton.setOnClickListener(v -> {
//            try {
//            LinearLayout layout = (LinearLayout) v.getParent().getParent();
//            TextView Title = layout.findViewById(R.id.tv_title);
//            String title = (String) Title.getText();
//            Intent i = new Intent(context, DetailMovieActivity.class);
//            i.putExtra("title",title);
//            context.startActivity(i);
//            }
//            catch (Exception e)
//            {
//                System.out.println("something went wrong: " + e);
//            }
//        });
//        return new ViewHolder(view);
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
        Button detailButton = (Button)v1.findViewById(R.id.btn_detail);
        detailButton.setOnClickListener(v -> {
            try {
            LinearLayout layout = (LinearLayout) v.getParent().getParent();
            TextView Title = layout.findViewById(R.id.tv_title);
            String title = (String) Title.getText();
            Intent i = new Intent(context, DetailMovieActivity.class);
            i.putExtra("title",title);
            context.startActivity(i);
            }
            catch (Exception e)
            {
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

                movieVH.movieId.setText("" + result.getId());
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
        public int getItemCount () {
            return movieResults == null ? 0 : movieResults.size();
        }

        @Override
        public int getItemViewType ( int position){
            return (position == movieResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
        }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

        public void add (movie_item r){
            movieResults.add(r);
            notifyItemInserted(movieResults.size() - 1);
        }

        public void addAll (ArrayList < movie_item > moveResults) {
            for (movie_item result : moveResults) {
                add(result);
            }
        }

        public void remove (movie_item r){
            int position = movieResults.indexOf(r);
            if (position > -1) {
                movieResults.remove(position);
                notifyItemRemoved(position);
            }
        }

        public void clear () {
            isLoadingAdded = false;
            while (getItemCount() > 0) {
                remove(getItem(0));
            }
        }

        public boolean isEmpty () {
            return getItemCount() == 0;
        }


        public void addLoadingFooter () {
            isLoadingAdded = true;
            add(new movie_item());
        }

        public void removeLoadingFooter () {
            isLoadingAdded = false;

            int position = movieResults.size() - 1;
            movie_item result = getItem(position);

            if (result != null) {
                movieResults.remove(position);
                notifyItemRemoved(position);
            }
        }

        public movie_item getItem ( int position){
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
                movieId=itemView.findViewById(R.id.movie_id);
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

            public LoadingVH(View itemView) {
                super(itemView);
            }
        }
    }
