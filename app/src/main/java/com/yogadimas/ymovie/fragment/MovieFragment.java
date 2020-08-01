package com.yogadimas.ymovie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yogadimas.ymovie.R;
import com.yogadimas.ymovie.adapter.MovieAdapter;
import com.yogadimas.ymovie.model.movie.ResultsMovie;
import com.yogadimas.ymovie.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieFragment extends Fragment {

    public static final String MOVIE = "extra_movie";
    @BindView(R.id.progressBarMovie)
    ProgressBar progressBarMovie;
    @BindView(R.id.recyclerMovie)
    RecyclerView recyclerViewMovie;
    Unbinder unbinder;
    List<ResultsMovie> resultsMovieArrayList = new ArrayList<>();
    MainViewModel movieViewModel;
    private MovieAdapter movieAdapter;


    public MovieFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Loading(true);

        getMovie();

        setupRecyclerView();

    }


    public void getMovie() {
        movieViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        movieViewModel.initMovie();
        movieViewModel.getMovieModel().observe(this, responseMovie -> {
            if ((responseMovie != null ? responseMovie.getResults() : null) == null) {
                Toast.makeText(getActivity(), getString(R.string.nodatafound), Toast.LENGTH_SHORT).show();
                Loading(false);
            } else {
                List<ResultsMovie> resultsMovie = responseMovie.getResults();
                resultsMovieArrayList.addAll(resultsMovie);
                movieAdapter.notifyDataSetChanged();
                Loading(false);

            }
        });

    }

    private void setupRecyclerView() {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter(getActivity(), resultsMovieArrayList);
            recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewMovie.setAdapter(movieAdapter);
            recyclerViewMovie.setItemAnimator(new DefaultItemAnimator());
            recyclerViewMovie.setNestedScrollingEnabled(true);
        } else {
            movieAdapter.notifyDataSetChanged();
        }
    }

    private void Loading(Boolean state) {
        if (state) {
            progressBarMovie.setVisibility(View.VISIBLE);
        } else {
            progressBarMovie.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getMovie();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
