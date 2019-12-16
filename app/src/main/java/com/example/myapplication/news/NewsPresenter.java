package com.example.myapplication.news;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;
import com.example.myapplication.model.HomeMovieResponse;
import com.example.myapplication.network.EndpointClient;
import com.example.myapplication.network.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsPresenter {

    private final String TAG = getClass().getSimpleName();
    private NewsFilm view;
    private ServiceGenerator serviceGenerator;
    private Context context;

    public NewsPresenter(NewsFilm view, ServiceGenerator serviceGenerator, Context context) {
        this.view = view;
        this.serviceGenerator = serviceGenerator;
        this.context = context;
    }

    public void upcoming() {
        view.showLoading();
        Call<HomeMovieResponse> call = serviceGenerator.createService(EndpointClient.class, context)
                .upcomingMovie(BuildConfig.APPLICATION_ID, ServiceGenerator.LANG);
        call.enqueue(new Callback<HomeMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<HomeMovieResponse> call, @NonNull Response<HomeMovieResponse> response) {
                view.hideLoading();
                if (response.isSuccessful()){
                    if (response.body().getResults().size() == 0){
                        view.showMovieResponseEmpty();
                    }else{
                        view.showMovieReponseSuccess(response.body());
                    }
                }else{
                    view.showMovieResponseError(context.getString(R.string.error_message_failed_to_fetch));
                }
            }

            @Override
            public void onFailure(Call<HomeMovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                view.hideLoading();
                view.showMovieResponseError(t.getMessage());
            }
        });
    }

}