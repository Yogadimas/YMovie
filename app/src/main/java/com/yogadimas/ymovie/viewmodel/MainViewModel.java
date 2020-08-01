package com.yogadimas.ymovie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yogadimas.ymovie.model.movie.ResponseMovie;
import com.yogadimas.ymovie.model.tv.ResponseTv;
import com.yogadimas.ymovie.repository.MovieRepository;
import com.yogadimas.ymovie.repository.TvRepository;

public class MainViewModel extends ViewModel {

    private MutableLiveData<ResponseMovie> responseMovieMutableLiveData;
    private MutableLiveData<ResponseTv> responseTvMutableLiveData;

    public void initMovie() {
        if (responseMovieMutableLiveData != null) {
            return;
        }
        MovieRepository movieRepository = MovieRepository.getInstancemovie();
        responseMovieMutableLiveData = movieRepository.getMovie();
    }

    public LiveData<ResponseMovie> getMovieModel() {
        return responseMovieMutableLiveData;
    }

    public void initSearchMovie(String text) {
        if (responseMovieMutableLiveData != null) {
            return;
        }
        MovieRepository movieRepo = MovieRepository.getInstancemovie();
        responseMovieMutableLiveData = movieRepo.searchMovie(text);
    }

    public LiveData<ResponseMovie> searchMovie() {
        return responseMovieMutableLiveData;
    }

    public void initTv() {
        if (responseTvMutableLiveData != null) {
            return;
        }
        TvRepository tvRepository = TvRepository.getInstance();
        responseTvMutableLiveData = tvRepository.getTV();
    }

    public LiveData<ResponseTv> getTvModel() {
        return responseTvMutableLiveData;
    }
}
