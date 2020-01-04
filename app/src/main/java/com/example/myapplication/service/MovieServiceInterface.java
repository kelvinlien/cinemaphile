package com.example.myapplication.service;


import com.example.myapplication.model.TopRatedMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieServiceInterface {

    @GET("movie/top_rated")
    Call<TopRatedMovies> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("movie/{id}")
    Call<TopRatedMovies> getDetailsById(
            @Path("id") int id,
            @Query("api_key") String apiKey

    );

}
