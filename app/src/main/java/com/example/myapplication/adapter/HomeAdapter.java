package com.example.myapplication.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import  com.example.myapplication.model.movie_item;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<movie_item> movieResults;
    private Context context;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";

    private boolean isLoadingAdded = false;

    movie_item result;

    public HomeAdapter(List<movie_item> results, Context context) {
        this.movieResults = results;
        this.context = context;
    }

    public HomeAdapter(Context context) {
        this.movieResults = new ArrayList<>();
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

                /**
                 * Using Glide to handle image loading.
                 */
                Glide
                        .with(context)
                        .load(BASE_URL_IMG + result.getPoster())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                        .centerCrop()
                        .into(movieVH.ivMovie);

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

        public void addAll (List < movie_item > moveResults) {
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
