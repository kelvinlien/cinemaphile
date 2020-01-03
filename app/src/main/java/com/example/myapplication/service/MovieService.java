package com.example.myapplication.service;



import com.example.myapplication.Constants;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;



public class MovieService {

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
