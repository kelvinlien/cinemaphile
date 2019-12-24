package com.example.myapplication.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TMDB {
    private static final String ACESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwZTE0YjkzMjc4NDhhNTk5MWI0N2I4N2MyOTE5MzAzNyIsInN1YiI6IjVkZmE0MmE4MjZkYWMxMDAxMjU4Y2U4NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.G5lmTQ3WvKlX6Syb1P2ACNeXHpJsSE1mQ4x2ut1kfcA";
    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String MOVIE_DISCOVER_PREFIX = "discover/movie";
    private static final String MOVIE_DETAIL_PREFIX = "movie/";
    private static final String MOVIE_SEARCH_PREFIX = "search/movie";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String DEFAULT_SORT = "popularity.desc";
    private static final String SORT_PARA = "sort_by";
    private static final String PAGE_PARA = "page";
    private static final String[] POSTER_SIZES = {
            "w92",
            "w154",
            "w185",
            "w342",
            "w500",
            "w780",
            "original"};

    private AsyncHttpClient client;

    private static TMDB instance;


    private TMDB()
    {
        client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer " + ACESS_TOKEN);
    }

    public static synchronized TMDB getInstance()
    {
        if (instance == null)
        {
            instance = new TMDB();
        }
        return instance;
    }

    public void getPopularMovie(int page_number, AsyncHttpResponseHandler handler)
    {
        RequestParams rp = new RequestParams();
        rp.put(SORT_PARA, DEFAULT_SORT);
        rp.put(PAGE_PARA, page_number);
        String url = MOVIE_BASE_URL + MOVIE_DISCOVER_PREFIX;
        client.get(url, rp, handler);
    }

    public void getImage(String path, int size, AsyncHttpResponseHandler handler)       //return a jpeg image onSuccess
    {
        if (size >= POSTER_SIZES.length)
        {
            size = POSTER_SIZES.length-1;
        }
        else if (size < 0)
        {
            size = 0;
        }
        String size_code = POSTER_SIZES[size];
        String url = IMAGE_BASE_URL + size_code + "/" + path;
        client.get(url, new RequestParams(), handler);
    }

    public void getSearchResults(String keyword, AsyncHttpResponseHandler handler)
    {
        RequestParams rp = new RequestParams();
        rp.add("query", keyword);
        String url = MOVIE_BASE_URL + MOVIE_SEARCH_PREFIX + keyword;
        client.get(url, rp, handler);
    }

    public void getMovieDetail(int id, AsyncHttpResponseHandler handler)
    {
        String url = MOVIE_BASE_URL + MOVIE_DETAIL_PREFIX + id;
        client.get(url, new RequestParams(), handler);
    }


}
