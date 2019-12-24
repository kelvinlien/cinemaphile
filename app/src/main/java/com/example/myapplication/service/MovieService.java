package com.example.myapplication.service;



import com.example.myapplication.Constants;
import com.example.myapplication.model.movie_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MovieService {

    public ArrayList<movie_item> processResults(Response response) {
        ArrayList<movie_item> movies = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject tmdbJSON = new JSONObject(jsonData);
                JSONArray moviesJSON = tmdbJSON.getJSONArray("results");

                for (int i = 0; i < moviesJSON.length(); i++) {
                    JSONObject movieJSON = moviesJSON.getJSONObject(i);
                    String title = movieJSON.getString("title");
                    String posterLink = movieJSON.getString("poster_path");
                    String releaseDate = movieJSON.getString("release_date");
                    String overView=movieJSON.getString("overview");
                    // create Movie object
                    movie_item newMovie = new movie_item(releaseDate, title, posterLink,overView);
                    movies.add(newMovie);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static void findMovies(String query, Callback callback){
        //OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.MOVIE_API_KEY, Constants.MOVIE_BASE_URL);
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.MOVIE_BASE_URL + Constants
                .MOVIE_API_KEY).newBuilder();
        urlBuilder.addQueryParameter(Constants.MOVIE_TITLE_QUERY, query);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
