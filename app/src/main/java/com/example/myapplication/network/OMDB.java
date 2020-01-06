package com.example.myapplication.network;

import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;


public class OMDB {
    private static final String BASE_URL = "http://www.omdbapi.com/";
    private static final String API_KEY = "61222a7d";
    private static final String MOVIE_NAME_PARA = "t";
    private static final String MOVIE_ID_PARA = "i";
    private static final String API_KEY_PARA = "apikey";
    private static OMDB instance;
    private AsyncHttpClient client;
    private RequestParams rp;

    public OMDB() {
        client = new AsyncHttpClient();
        rp = new RequestParams();
        rp.put(API_KEY_PARA, API_KEY);
    }

    public static synchronized OMDB getInstance() {
        if (instance == null) {
            instance = new OMDB();
        }
        return instance;
    }

    public void getImage(String url, ImageView iv)       //return a jpeg image onSuccess
    {
        Picasso.get()
                .load(url)
//                .resize(50, 50)
//                .centerCrop()
                .into(iv);
    }


    public void getMovieDetailByName(String movie_name, AsyncHttpResponseHandler handler) {
        rp.remove(MOVIE_ID_PARA);
        rp.put(MOVIE_NAME_PARA, movie_name);
        client.get(BASE_URL, rp, handler);
    }

    public void getMovieDetailByID(String imdb_id, AsyncHttpResponseHandler handler) {
        rp.remove(MOVIE_NAME_PARA);
        rp.put(MOVIE_ID_PARA, imdb_id);
        client.get(BASE_URL, rp, handler);
    }


}
