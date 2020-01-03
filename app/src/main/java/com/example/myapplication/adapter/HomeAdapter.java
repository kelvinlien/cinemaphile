package com.example.myapplication.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.detailmovie.DetailMovieActivity;
import  com.example.myapplication.model.movie_item;
import java.util.List;

import com.example.myapplication.network.TMDB;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<movie_item>results;
    private Context context;
    private TMDB tmdb = TMDB.getInstance();

    public HomeAdapter(List<movie_item> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false);
        ImageButton detailButton = view.findViewById(R.id.btn_detail);
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int i) {

        holder.tvDate.setText(results.get(i).getReleaseDate());
        holder.tvDesc.setText(results.get(i).getOverView());
        holder.tvTitle.setText(results.get(i).getTitle());

        tmdb.getImage(results.get(i).getPoster(), 1, holder.ivMovie);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMovie;

        TextView tvTitle;

        TextView tvDesc;

        TextView tvDate;

        ImageButton btnDetail;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMovie= itemView.findViewById(R.id.iv_movie);
            tvTitle=itemView.findViewById(R.id.tv_title);
            tvDesc=itemView.findViewById(R.id.tv_desc);
            tvDate=itemView.findViewById(R.id.tv_date);
            btnDetail=itemView.findViewById(R.id.btn_detail);
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

        public ImageButton getBtnDetail() {
            return btnDetail;
        }

    }
}
