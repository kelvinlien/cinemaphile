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
    private static final String API_KEY_PARA = "apikey";
    private AsyncHttpClient client;
    private RequestParams rp;
    private static OMDB instance;

    public OMDB()
    {
        client = new AsyncHttpClient();
        rp = new RequestParams();
        rp.put(API_KEY_PARA, API_KEY);
    }

    public static synchronized OMDB getInstance()
    {
        if (instance == null)
        {
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


    public void getMovieDetail(String movie_name, AsyncHttpResponseHandler handler)
    {
        rp.put(MOVIE_NAME_PARA,movie_name);
        client.get(BASE_URL, rp, handler);
    }



}
