package com.example.myapplication.news;

import com.example.myapplication.model.HomeMovieResponse;

public interface NewsFilm {
    void showLoading();

    void hideLoading();

    void showMovieResponseError(String ms);

    void showMovieResponseEmpty();

    void showMovieReponseSuccess(HomeMovieResponse body);
}
