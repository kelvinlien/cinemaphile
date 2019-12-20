package com.example.myapplication.detailmovie;

import android.app.Activity;
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

import com.loopj.android.http.*;

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

        HttpUtils.getByUrl("http://www.omdbapi.com/?apikey=61222a7d&t=Avengers+ +Endgame", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
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
                    Bitmap bmp = null;
                    try {
                        bmp = new getImageFromURL().execute(poster).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Poster.setImageBitmap(bmp);
                    MovieName.setText((CharSequence) title);
                    Year.setText((CharSequence)year);
                    Rated.setText(rated);
                    Runtime.setText(runtime);
                    IMDb.setText(imdb_score);
                    RottenTomatoes.setText(roto_score);
                    Metacritic.setText(meta_score);
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
}

