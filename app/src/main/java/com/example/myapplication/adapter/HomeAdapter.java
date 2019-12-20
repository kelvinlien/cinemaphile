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

import com.example.myapplication.R;
import  com.example.myapplication.model.movie_item;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<movie_item>results;
    private Context context;

    public HomeAdapter(List<movie_item> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int i) {

        holder.tvDate.setText(results.get(i).getReleaseDate());
        holder.tvDesc.setText(results.get(i).getOverView());
        holder.tvTitle.setText(results.get(i).getTitle());

        holder.ivMovie.setImageDrawable(context.getResources().getDrawable(results.get(i).getPoster()));

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

        Button btnDetail;

        Button btnShare;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMovie= itemView.findViewById(R.id.iv_movie);
            tvTitle=itemView.findViewById(R.id.tv_title);
            tvDesc=itemView.findViewById(R.id.tv_desc);
            tvDate=itemView.findViewById(R.id.tv_date);
            btnDetail=itemView.findViewById(R.id.btn_detail);
            btnShare=itemView.findViewById(R.id.btn_share);
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
