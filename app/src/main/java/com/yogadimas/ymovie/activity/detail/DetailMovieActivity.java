package com.yogadimas.ymovie.activity.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.yogadimas.ymovie.BuildConfig;
import com.yogadimas.ymovie.R;
import com.yogadimas.ymovie.model.movie.ResultsMovie;
import com.yogadimas.ymovie.roomdatabase.movie.MovieDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRAMOVIE = "extra_movie";

    @BindView(R.id.img_item_poster)
    ImageView ivPoster;
    @BindView(R.id.img_item_header)
    ImageView ivHeader;
    @BindView(R.id.tv_item_title)
    TextView tvItemTitle;
    @BindView(R.id.tv_released)
    TextView tvReleaseDate;
    @BindView(R.id.tv_item_popularity)
    TextView tvPopularity;
    @BindView(R.id.tv_item_voteCount)
    TextView tvVoteCount;
    @BindView(R.id.tv_item_language)
    TextView tvLanguage;
    @BindView(R.id.tv_item_rating)
    TextView tvDetailRating;
    @BindView(R.id.tv_content_overview)
    TextView tvContentOverview;
    @BindView(R.id.progress_detail)
    ProgressBar progressBarDetailMovie;
    ResultsMovie resultsMovie;
    MaterialFavoriteButton materialFavoriteButtonNice;
    private MovieDatabase movieDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);
        ButterKnife.bind(this);

        materialFavoriteButtonNice = findViewById(R.id.favorite_nice);
        resultsMovie = getIntent().getParcelableExtra(EXTRAMOVIE);

        if (savedInstanceState != null) {
            showLoading(false);
        } else {
            showLoading(true);

        }


        movieDatabase = MovieDatabase.getMovieDatabase(this);
        if (movieDatabase.movieDao().selectItemNoCursor(String.valueOf(resultsMovie.getId())) != null) {
            materialFavoriteButtonNice.setFavorite(true);
        }

        materialFavoriteButtonNice.setVisibility(View.GONE);
        setUpDelay();

        initFavorite(this);

        Toolbar mDetailToolbar = findViewById(R.id.detail_toolbar);
        mDetailToolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        mDetailToolbar.setNavigationOnClickListener(v -> finish());


    }

    private void setUpDelay() {

        materialFavoriteButtonNice.setVisibility(View.VISIBLE);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.detailMovie) + " " + resultsMovie.getOriginalTitle());
        Glide.with(DetailMovieActivity.this).load(BuildConfig.BASE_URL_IMAGE + resultsMovie.getPosterPath()).into(ivPoster);
        Glide.with(DetailMovieActivity.this).load(BuildConfig.BASE_URL_IMAGE + resultsMovie.getBackdropPath()).into(ivHeader);
        tvItemTitle.setText(resultsMovie.getOriginalTitle());
        tvReleaseDate.setText(resultsMovie.getReleaseDate());
        tvDetailRating.setText(String.valueOf(resultsMovie.getVoteAverage()));
        tvPopularity.setText(String.valueOf(resultsMovie.getPopularity()));
        tvVoteCount.setText(String.valueOf(resultsMovie.getVoteCount()));
        tvLanguage.setText(resultsMovie.getOriginalLanguage());
        tvContentOverview.setText(resultsMovie.getOverview());
        showLoading(false);
    }

    private void initFavorite(final Context context) {
        materialFavoriteButtonNice.setOnFavoriteChangeListener(
                (buttonView, favorite) -> {
                    if (favorite) {
                        movieDatabase = MovieDatabase.getMovieDatabase(context);
                        movieDatabase.movieDao().insert(resultsMovie);
                        Toast.makeText(DetailMovieActivity.this, getString(R.string.addFavorite), Toast.LENGTH_SHORT).show();

                    } else {
                        movieDatabase = MovieDatabase.getMovieDatabase(context);
                        movieDatabase.movieDao().deleteById(resultsMovie.getId());
                        Toast.makeText(DetailMovieActivity.this, getString(R.string.deleteFavorite), Toast.LENGTH_SHORT).show();
                    }

                });

    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBarDetailMovie.setVisibility(View.VISIBLE);
        } else {
            progressBarDetailMovie.setVisibility(View.GONE);
        }
    }
}
