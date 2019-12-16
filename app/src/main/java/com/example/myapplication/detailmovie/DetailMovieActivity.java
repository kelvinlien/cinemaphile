package com.example.myapplication.detailmovie;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.db.MovieHelper;
import com.example.myapplication.model.MovieResponse;
import com.google.android.material.appbar.AppBarLayout;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.request.RequestOptions;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    private MovieHelper movieHelper;
    private Menu menu;
    private MenuItem menuItem;
    private String id;
    private boolean isStar;
    private MovieResponse.Results extras;


    ImageView ivMovie;

    TextView tvTitle;

    TextView tvRate;

    TextView tvOverview;

    TextView tvDate;

    Toolbar toolbar;

    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extras = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (extras != null) {
            tvTitle.setText(extras.getTitle());
            tvRate.setText("" + extras.getVoteAverage());
            tvOverview.setText(extras.getOverview());
            tvDate.setText(extras.getReleaseDate());

            getSupportActionBar().setTitle(extras.getTitle());

            if (extras.getPosterPath() != null){
                Glide.with(this)
                        .load("http://image.tmdb.org/t/p/w185/".concat(extras.getPosterPath()))
                        .thumbnail(Glide.with(this).load(R.drawable.loading))
                        .apply(new RequestOptions().error(R.drawable.reload))
                        .into(ivMovie);

            }else{
                Glide.with(this)
                        .load(R.drawable.reload)
                        .thumbnail(Glide.with(this).load(R.drawable.loading))
                        .apply(new RequestOptions().error(R.drawable.reload))
                        .into(ivMovie);
            }


            id = String.valueOf(extras.getId());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_activity, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                Intent shareIntent = new Intent();
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Check our new movie");
                startActivity(shareIntent);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }
}

