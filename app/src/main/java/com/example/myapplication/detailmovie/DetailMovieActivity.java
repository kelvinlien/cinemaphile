package com.example.myapplication.detailmovie;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.myapplication.R;
import com.example.myapplication.network.OMDB;
import com.example.myapplication.network.TMDB;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.robertlevonyan.views.expandable.Expandable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class DetailMovieActivity extends Activity {


    ImageView Poster;

    TextView MovieName;

    TextView Year;

    TextView Rated;

    TextView Runtime;

    CardView IMDbCard;

    TextView IMDb;

    TextView RottenTomatoes;

    CardView MetacriticCard;

    TextView Metacritic;

    ChipGroup Genres;

    TextView Plot;

    Expandable PlotExpand;

    Expandable DCExpand;

    Expandable BIExpand;

    TextView Director;

    TextView Cast;

    TextView BoxOffice;

    TextView Budget;

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
        IMDbCard = findViewById(R.id.IMDb_card);
        IMDb = findViewById(R.id.IMDb_score);
        MetacriticCard = findViewById(R.id.Metacritic_card);
        RottenTomatoes = findViewById(R.id.RottenTomatoes_score);
        Metacritic = findViewById(R.id.Metacritic_score);
        Genres = findViewById(R.id.Genres);
        Plot = findViewById(R.id.PlotContent);
        Director = findViewById(R.id.DirectorName);
        Cast = findViewById(R.id.CastNames);
        BoxOffice = findViewById(R.id.BoxOfficeNumber);
        Budget = findViewById(R.id.BudgetNumber);
        Awards = findViewById(R.id.AwardNames);
        Intent i = getIntent();
        String movie_name = i.getStringExtra("title");
        String tmdb_id = i.getStringExtra("id");
        String tmdb_overview = i.getStringExtra("overview");
        String tmdb_year = Objects.requireNonNull(i.getStringExtra("date")).substring(0,3);
        getTMDBData(tmdb_id, movie_name, tmdb_overview, tmdb_year);
        System.out.println("--------tmdb id is: " + tmdb_id);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ShimmerFrameLayout container =
                findViewById(R.id.shimmer_view_container);
        container.startShimmer();
    }


    public void getOMDBData(String id, String tmdb_poster_path, String tmdb_budget, String tmdb_average_vote, ArrayList<String> tmdb_genre_array, String movie_name, String tmdb_overview, String tmdb_year) {

        OMDB.getInstance().getMovieDetailByID(id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("asd", "---------------- this is response : " + response);
                ShimmerFrameLayout container =
                        findViewById(R.id.shimmer_view_container);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    String res = serverResp.getString("Response");
                    if (res.equals("True")) {
                        String title = serverResp.getString("Title");
                        String year = serverResp.getString("Year");
//                        String poster = serverResp.getString("Poster");
                        String rated = serverResp.getString("Rated");
                        String runtime = serverResp.getString("Runtime");
                        JSONArray ratings = serverResp.getJSONArray("Ratings");
                        for (int i = 0 ; i < ratings.length() ; i++)
                        {
                            JSONObject rating = ratings.getJSONObject(i);
                            switch (rating.getString("Source"))
                            {
                                case "Internet Movie Database":
                                    IMDb.setText(rating.getString("Value"));
                                    break;
                                case "Rotten Tomatoes":
                                    RottenTomatoes.setText(rating.getString("Value"));
                                    break;
                                default:
                                    Metacritic.setText(rating.getString("Value"));
                                    break;
                            }
                        }
                        String[] genre_array = serverResp.getString("Genre").split(", ");
                        String plot = serverResp.getString("Plot");
                        String director = serverResp.getString("Director");
                        String cast = serverResp.getString("Actors");
                        String boxoffice = serverResp.getString("BoxOffice");
                        String awards = serverResp.getString("Awards");
//                        OMDB.getInstance().getImage(poster, Poster);
                        TMDB.getInstance().getImage(tmdb_poster_path, 3, Poster);
                        String[] temp = runtime.split(" ");
                        int hours = Integer.parseInt(temp[0]) / 60;
                        int minutes = Integer.parseInt(temp[0]) % 60;
                        String duration = hours > 0 ? hours + "h" + minutes + "min" : minutes + "min";
                        MovieName.setText(title);
                        Year.setText(year);
                        Rated.setText(rated);
                        Runtime.setText(duration);
                        setCategoryChips(new ArrayList<>(Arrays.asList(genre_array)));
                        Plot.setText(plot);
                        Director.setText(director);
                        Cast.setText(cast);
                        BoxOffice.setText(boxoffice);
                        Budget.setText(tmdb_budget);
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
                super.onSuccess(statusCode, headers, timeline);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                TMDB.getInstance().getImage(tmdb_poster_path, 2, Poster);
                MovieName.setText(movie_name);
                Year.setText(tmdb_year);
                IMDbCard.setVisibility(View.GONE);
                RottenTomatoes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tmdb, 0, 0 ,0 );
                RottenTomatoes.setText(tmdb_average_vote);
                MetacriticCard.setVisibility(View.GONE);
                setCategoryChips(tmdb_genre_array);
                Plot.setText(tmdb_overview);
                Budget.setText(tmdb_budget);
            }
        });
    }

    public void getTMDBData(String tmdb_id, String movie_name, String tmdb_overview, String tmdb_year)
    {
        TMDB.getInstance().getMovieDetail(tmdb_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("asd", "---------------- this is response : " + response);
                String tmdb_poster_path = "";
                String tmdb_budget = "";
                String tmdb_average_vote = "";
                String imdb_id = "";
                ArrayList<String> tmdb_genre_array = new ArrayList<>();
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    System.out.println("----serverResp: " + serverResp);
                    tmdb_poster_path = serverResp.getString("poster_path");
                    tmdb_budget = serverResp.getString("budget");
                    tmdb_average_vote = serverResp.getString("vote_average");
                    imdb_id = serverResp.getString("imdb_id");
                    JSONArray tmdb_genres = serverResp.getJSONArray("genres");
                    for (int i = 0 ; i < tmdb_genres.length() ; i++)
                    {
                        tmdb_genre_array.add(tmdb_genres.getJSONObject(i).getString("name"));
                    }
                } catch (JSONException e) {
                    System.out.println("--------------TMDB getDetail went wrong");
                    e.printStackTrace();
                }
                tmdb_budget = readableBudget(tmdb_budget);
                getOMDBData(imdb_id, tmdb_poster_path, tmdb_budget, tmdb_average_vote, tmdb_genre_array, movie_name, tmdb_overview, tmdb_year);


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    protected String readableBudget(String budget)
    {
        String temp = budget;
        if (!budget.equals(""))
        {
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
            temp = format.format(Integer.parseInt(budget));
        }
        return temp;
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

