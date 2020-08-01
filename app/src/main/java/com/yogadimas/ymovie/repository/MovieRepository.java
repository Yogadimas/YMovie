package com.yogadimas.ymovie.repository;

import androidx.lifecycle.MutableLiveData;

import com.yogadimas.ymovie.model.movie.ResponseMovie;
import com.yogadimas.ymovie.network.ApiService;
import com.yogadimas.ymovie.network.config.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static MovieRepository movieRepository;
    private ApiService apiService;

    private MovieRepository() {
        apiService = Client.getInitRetrofit();
    }

    public static MovieRepository getInstancemovie() {
        if (movieRepository == null) {
            movieRepository = new MovieRepository();

        }
        return movieRepository;
    }

    public MutableLiveData<ResponseMovie> getMovie() {
        final MutableLiveData<ResponseMovie> movieData = new MutableLiveData<>();
        apiService.getDataMovie().enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                if ((response.body() != null ? response.body().getPage() : 0) > 0) {
                    movieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                movieData.setValue(null);
            }
        });

        return movieData;
    }


    public MutableLiveData<ResponseMovie> searchMovie(String text) {
        final MutableLiveData<ResponseMovie> movieMutableLiveData = new MutableLiveData<>();
        apiService.searchMovie(text).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        movieMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                movieMutableLiveData.setValue(null);
            }
        });

        return movieMutableLiveData;
    }


}
