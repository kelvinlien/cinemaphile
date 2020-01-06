package com.example.myapplication.network;

import android.widget.ImageView;

import com.example.myapplication.model.movie_item;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TMDB {
    private static final String ACESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwZTE0YjkzMjc4NDhhNTk5MWI0N2I4N2MyOTE5MzAzNyIsInN1YiI6IjVkZmE0MmE4MjZkYWMxMDAxMjU4Y2U4NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.G5lmTQ3WvKlX6Syb1P2ACNeXHpJsSE1mQ4x2ut1kfcA";
    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String MOVIE_DISCOVER_PREFIX = "discover/movie/";
    private static final String MOVIE_DETAIL_PREFIX = "movie/";
    private static final String MOVIE_SEARCH_PREFIX = "search/movie";
    private static final String MOVIE_TOP_RATED_PREFIX = "movie/top_rated";
    private static final String LANGUAGE_PARA = "language";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String DEFAULT_SORT = "popularity.desc";
    private static final String SORT_PARA = "sort_by";
    private static final String PAGE_PARA = "page";
    private static final String API_KEY = "1b8bcb3f9c653e93116366378d884876";
    private static final String[] POSTER_SIZES = {
            "w92",
            "w154",
            "w185",
            "w342",
            "w500",
            "w780",
            "original"};
    private static TMDB instance;
    private AsyncHttpClient client;


    private TMDB() {
        client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + ACESS_TOKEN);
    }

    public static synchronized TMDB getInstance() {
        if (instance == null) {
            instance = new TMDB();
        }
        return instance;
    }

    public void getTopRatedMovies(int page_number, AsyncHttpResponseHandler handler) {
        RequestParams rp = new RequestParams();
        rp.put(LANGUAGE_PARA, "en-US");
        rp.put(PAGE_PARA, page_number);
        String url = MOVIE_BASE_URL + MOVIE_TOP_RATED_PREFIX;
        client.get(url, rp, handler);
    }

    public void getPopularMovies(int page_number, AsyncHttpResponseHandler handler) {
        RequestParams rp = new RequestParams();
        rp.put(SORT_PARA, DEFAULT_SORT);
        rp.put(PAGE_PARA, page_number);
        String url = MOVIE_BASE_URL + MOVIE_DISCOVER_PREFIX;
        client.get(url, rp, handler);
    }

    public void getImage(String path, int size, ImageView iv)       //return a jpeg image onSuccess
    {
        if (size >= POSTER_SIZES.length) {
            size = POSTER_SIZES.length - 1;
        } else if (size < 0) {
            size = 0;
        }
        String size_code = POSTER_SIZES[size];
        String url = IMAGE_BASE_URL + size_code + "/" + path;
        Picasso.get()
                .load(url)
//                .resize(50, 50)
//                .centerCrop()
                .into(iv);
    }

    public void getSearchResults(String keyword, AsyncHttpResponseHandler handler) {
        RequestParams rp = new RequestParams();
        rp.add("query", keyword);
        String url = MOVIE_BASE_URL + MOVIE_SEARCH_PREFIX;
        client.get(url, rp, handler);
    }

    public void getMovieDetail(String id, AsyncHttpResponseHandler handler) {
        String url = MOVIE_BASE_URL + MOVIE_DETAIL_PREFIX + id;
        client.get(url, new RequestParams(), handler);
    }

    public ArrayList<movie_item> processResults(JSONObject response) throws JSONException {
        ArrayList<movie_item> movies = new ArrayList<>();
        JSONArray moviesJSON = response.getJSONArray("results");

        for (int i = 0; i < moviesJSON.length(); i++) {
            JSONObject movieJSON = moviesJSON.getJSONObject(i);
            String title = movieJSON.getString("title");
            String posterLink = movieJSON.getString("poster_path");
            String releaseDate = movieJSON.getString("release_date");
            String overView = movieJSON.getString("overview");
            String id = movieJSON.getString("id");
            // create Movie object
            movie_item newMovie = new movie_item(releaseDate, title, posterLink, overView, id);
            movies.add(newMovie);
        }
        return movies;
    }


}
