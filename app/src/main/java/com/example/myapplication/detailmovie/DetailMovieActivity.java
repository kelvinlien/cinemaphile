package com.example.myapplication.detailmovie;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.network.OMDB;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.robertlevonyan.views.expandable.Expandable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class DetailMovieActivity extends Activity {


    ImageView Poster;

    TextView MovieName;

    TextView Year;

    TextView Rated;

    TextView Runtime;

    TextView IMDb;

    TextView RottenTomatoes;

    TextView Metacritic;

    ChipGroup Genres;

    TextView Plot;

    Expandable PlotExpand;

    Expandable DCExpand;

    Expandable BIExpand;

    TextView Director;

    TextView Cast;

    TextView BoxOffice;

    TextView Awards;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        Poster = findViewById(R.id.Poster);
        MovieName = findViewById(R.id.MovieName);
        Year = findViewById(R.id.Year);
        Rated = findViewById(R.id.Rated);
        Runtime = findViewById(R.id.Runtime);
        IMDb = findViewById(R.id.IMDb_score);
        RottenTomatoes = findViewById(R.id.RottenTomatoes_score);
        Metacritic = findViewById(R.id.Metacritic_score);
        Genres = findViewById(R.id.Genres);
        Plot = findViewById(R.id.PlotContent);
        Director = findViewById(R.id.DirectorName);
        Cast = findViewById(R.id.CastNames);
        BoxOffice = findViewById(R.id.BoxOfficeNumber);
        Awards = findViewById(R.id.AwardNames);
        Intent i = getIntent();
        String movie_name = i.getStringExtra("title");
        String tmdb_id = i.getStringExtra("id");
        callRequest(movie_name);

    }

    @Override
    protected void onStart() {
        super.onStart();
        ShimmerFrameLayout container =
                findViewById(R.id.shimmer_view_container);
        container.startShimmer();
    }


    public void callRequest(String name) {

        OMDB.getInstance().getMovieDetail(name, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                ShimmerFrameLayout container =
                        findViewById(R.id.shimmer_view_container);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    String res = serverResp.getString("Response");
                    if (res.equals("True")) {
                        String title = serverResp.getString("Title");
                        String year = serverResp.getString("Year");
                        String poster = serverResp.getString("Poster");
                        String rated = serverResp.getString("Rated");
                        String runtime = serverResp.getString("Runtime");
                        JSONArray ratings = serverResp.getJSONArray("Ratings");
                        JSONObject imdb = ratings.getJSONObject(0);
                        String imdb_score = imdb.getString("Value");
                        JSONObject roto = ratings.getJSONObject(1);
                        String roto_score = roto.getString("Value");
                        JSONObject meta = ratings.getJSONObject(2);
                        String meta_score = meta.getString("Value");
                        String[] genreArray = serverResp.getString("Genre").split(", ");
                        String plot = serverResp.getString("Plot");
                        String director = serverResp.getString("Director");
                        String cast = serverResp.getString("Actors");
                        String boxoffice = serverResp.getString("BoxOffice");
                        String awards = serverResp.getString("Awards");
                        OMDB.getInstance().getImage(poster, Poster);
                        String[] temp = runtime.split(" ");
                        int hours = Integer.parseInt(temp[0]) / 60;
                        int minutes = Integer.parseInt(temp[0]) % 60;
                        String duration = hours > 0 ? hours + "h" + minutes + "min" : minutes + "min";
                        MovieName.setText(title);
                        Year.setText(year);
                        Rated.setText(rated);
                        Runtime.setText(duration);
                        IMDb.setText(imdb_score);
                        RottenTomatoes.setText(roto_score);
                        Metacritic.setText(meta_score);
                        setCategoryChips(new ArrayList<>(Arrays.asList(genreArray)));
                        Plot.setText(plot);
                        Director.setText(director);
                        Cast.setText(cast);
                        BoxOffice.setText(boxoffice);
                        Awards.setText(awards);
                    } else {
                        checkResponse(res);
                    }
                    container.stopShimmer();
                    container.hideShimmer();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                TextView tv = findViewById(R.id.Year);
                tv.setText((CharSequence) timeline);

            }
        });
    }

    protected void checkResponse(String res) {
        if (res.equals("False")) {
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Not found");
            alertDialog.setMessage("Movie name not found!");
            // Set the Icon for the Dialog
            alertDialog.setButton("Back", (dialog, which) -> {
                // TODO Add your code for the button here.   }
            });
            alertDialog.show();
        }
    }

    public void setCategoryChips(ArrayList<String> categorys) {
        for (String category :
                categorys) {
            Chip mChip = (Chip) this.getLayoutInflater().inflate(R.layout.item_chip_category, null, false);
            mChip.setText(category);
            mChip.setPadding(0, 0, 0, 0);
            mChip.setOnCheckedChangeListener((compoundButton, b) -> {

            });
            Genres.addView(mChip);
        }
    }
}

