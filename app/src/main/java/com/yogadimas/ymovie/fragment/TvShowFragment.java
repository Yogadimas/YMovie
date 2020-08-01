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
import com.yogadimas.ymovie.adapter.TvAdapter;
import com.yogadimas.ymovie.model.tv.ResultsTv;
import com.yogadimas.ymovie.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TvShowFragment extends Fragment {


    public static final String TV = "extra_tvshow";
    @BindView(R.id.recyclerViewTv)
    RecyclerView recyclerViewTv;
    Unbinder unbinder;
    List<ResultsTv> resultsTvShowArrayList = new ArrayList<>();

    MainViewModel movieViewModel;
    @BindView(R.id.progressBarTv)
    ProgressBar progressBarTv;
    private TvAdapter tvAdapter;


    public TvShowFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Loading(true);

        getTv();

        setupRecyclerView();


    }


    public void getTv() {
        movieViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        movieViewModel.initTv();
        movieViewModel.getTvModel().observe(this, responseTv -> {
            if ((responseTv != null ? responseTv.getResults() : null) == null) {
                Toast.makeText(getActivity(), R.string.nodatafound, Toast.LENGTH_SHORT).show();
                Loading(false);
            } else {
                List<ResultsTv> resultsTv = responseTv.getResults();
                resultsTvShowArrayList.addAll(resultsTv);
                tvAdapter.notifyDataSetChanged();
                Loading(false);
            }

        });
    }

    private void setupRecyclerView() {
        if (tvAdapter == null) {
            tvAdapter = new TvAdapter(getActivity(), resultsTvShowArrayList);
            recyclerViewTv.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewTv.setAdapter(tvAdapter);
            recyclerViewTv.setItemAnimator(new DefaultItemAnimator());
            recyclerViewTv.setNestedScrollingEnabled(true);
        } else {
            tvAdapter.notifyDataSetChanged();
        }
    }

    private void Loading(Boolean state) {
        if (state) {
            progressBarTv.setVisibility(View.VISIBLE);
        } else {
            progressBarTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getTv();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
