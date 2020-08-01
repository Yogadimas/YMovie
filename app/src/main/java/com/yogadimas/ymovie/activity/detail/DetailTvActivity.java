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
import com.yogadimas.ymovie.model.tv.ResultsTv;
import com.yogadimas.ymovie.roomdatabase.tv.TvDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailTvActivity extends AppCompatActivity {

    public static final String EXTRATV = "extra_tvshow";

    @BindView(R.id.img_item_poster)
    ImageView ivPoster;
    @BindView(R.id.tv_item_title)
    TextView tvItemTitle;
    @BindView(R.id.img_item_header)
    ImageView ivHeader;
    @BindView(R.id.tv_item_rating)
    TextView tvRating;
    @BindView(R.id.tv_item_popularity)
    TextView tvPopularity;
    @BindView(R.id.tv_item_voteCount)
    TextView tvVoteCount;
    @BindView(R.id.tv_item_language)
    TextView tvLanguage;
    @BindView(R.id.tv_content_overview)
    TextView tvContentOverview;
    @BindView(R.id.progress_detail)
    ProgressBar progressBarDetailTv;
    @BindView(R.id.tv_released)
    TextView tvReleaseDate;
    ResultsTv resultsTvShow;
    MaterialFavoriteButton materialFavoriteButtonNice;
    private TvDatabase tvDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);
        ButterKnife.bind(this);


        materialFavoriteButtonNice = findViewById(R.id.favorite_nice);
        resultsTvShow = getIntent().getParcelableExtra(EXTRATV);

        if (savedInstanceState != null) {
            showLoading(false);
        } else {
            showLoading(true);

        }

        tvDatabase = TvDatabase.getTvDatabase(this);
        if (tvDatabase.tvDao().selectItem(String.valueOf(resultsTvShow.getId())) != null) {
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
            getSupportActionBar().setTitle(getString(R.string.detailtv) + " " + resultsTvShow.getName());
        Glide.with(DetailTvActivity.this).load(BuildConfig.BASE_URL_IMAGE + resultsTvShow.getBackdropPath()).into(ivHeader);
        Glide.with(DetailTvActivity.this).load(BuildConfig.BASE_URL_IMAGE + resultsTvShow.getPosterPath()).into(ivPoster);
        tvItemTitle.setText(resultsTvShow.getName());
        tvReleaseDate.setText(resultsTvShow.getFirstAirDate());
        tvRating.setText(String.valueOf(resultsTvShow.getVoteAverage()));
        tvPopularity.setText(String.valueOf(resultsTvShow.getPopularity()));
        tvVoteCount.setText(String.valueOf(resultsTvShow.getVoteCount()));
        tvLanguage.setText(resultsTvShow.getOriginalLanguage());
        tvContentOverview.setText(resultsTvShow.getOverview());
        showLoading(false);

    }


    private void initFavorite(final Context context) {
        materialFavoriteButtonNice.setOnFavoriteChangeListener(
                (buttonView, favorite) -> {
                    if (favorite) {
                        tvDatabase = TvDatabase.getTvDatabase(context);
                        tvDatabase.tvDao().InsertTv(resultsTvShow);
                        Toast.makeText(DetailTvActivity.this, getString(R.string.addFavorite), Toast.LENGTH_SHORT).show();
                    } else {
                        tvDatabase = TvDatabase.getTvDatabase(context);
                        tvDatabase.tvDao().deleteTv(resultsTvShow.getId());
                        Toast.makeText(DetailTvActivity.this, getString(R.string.deleteFavorite), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBarDetailTv.setVisibility(View.VISIBLE);
        } else {
            progressBarDetailTv.setVisibility(View.GONE);
        }
    }


}
