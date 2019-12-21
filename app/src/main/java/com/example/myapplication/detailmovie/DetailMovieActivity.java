package com.example.myapplication.detailmovie;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.network.getImageFromURL;

import com.example.myapplication.R;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import com.example.myapplication.network.HttpUtils;

import org.json.*;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.loopj.android.http.*;

import com.facebook.shimmer.*;
import java.util.concurrent.ExecutionException;

public class DetailMovieActivity extends Activity {


    ImageView Poster;

    TextView MovieName;

    TextView Year;

    TextView Rated;

    TextView Runtime;

    TextView IMDb;

    TextView RottenTomatoes;

    TextView Metacritic;

    HttpUtils client;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        client = new HttpUtils();
        Poster = findViewById(R.id.Poster);
        MovieName = findViewById(R.id.MovieName);
        Year = findViewById(R.id.Year);
        Rated = findViewById(R.id.Rated);
        Runtime = findViewById(R.id.Runtime);
        IMDb = findViewById(R.id.IMDb_score);
        RottenTomatoes = findViewById(R.id.RottenTomatoes_score);
        Metacritic = findViewById(R.id.Metacritic_score);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        ShimmerFrameLayout container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.startShimmer();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.detail_activity, menu);
//        this.menu = menu;
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_share:
//                Intent shareIntent = new Intent();
//                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Check our new movie");
//                startActivity(shareIntent);
//                return true;
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return true;
//        }
//    }

    public void callRequest(View view) {
        Button b = findViewById(R.id.test_button);


        b.setClickable(false);
        b.setVisibility(View.INVISIBLE);
        RequestParams rp = new RequestParams();
//        rp.add("username", "aaa"); rp.add("password", "aaa@123");

        HttpUtils.getByUrl("http://www.omdbapi.com/?apikey=61222a7d&t=Inglourious + +Basterds", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                ShimmerFrameLayout container =
                        (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
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
                    String res = serverResp.getString("Response");
                    checkResponse(res);
                    Bitmap bmp = null;
                    try {
                        bmp = new getImageFromURL().execute(poster).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String[] temp = runtime.split(" ");
                    int hours = Integer.parseInt(temp[0]) / 60;
                    int minutes = Integer.parseInt(temp[0]) % 60;
                    String duration = hours > 0 ? Integer.toString(hours) + "h" + Integer.toString(minutes) + "min" : Integer.toString(minutes) + "min";
                    Poster.setImageBitmap(bmp);
                    MovieName.setText((CharSequence) title);
                    Year.setText((CharSequence)year);
                    Rated.setText(rated);
                    Runtime.setText(duration);
                    IMDb.setText(imdb_score);
                    RottenTomatoes.setText(roto_score);
                    Metacritic.setText(meta_score);
                    container.stopShimmer();
                    container.hideShimmer();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                TextView tv = (TextView)findViewById(R.id.Year);
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
}

