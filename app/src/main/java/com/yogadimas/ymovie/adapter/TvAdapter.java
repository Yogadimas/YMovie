package com.yogadimas.ymovie.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yogadimas.ymovie.BuildConfig;
import com.yogadimas.ymovie.R;
import com.yogadimas.ymovie.activity.detail.DetailTvActivity;
import com.yogadimas.ymovie.model.tv.ResultsTv;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {

    private Context context;
    private List<ResultsTv> listitem;

    public TvAdapter(Context context, List<ResultsTv> resultsItemMovie) {
        this.context = context;
        this.listitem = resultsItemMovie;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
        return new ViewHolder(itemRow);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        viewHolder.tvJudul.setText(listitem.get(i).getName());
        viewHolder.tvDateRelease.setText(listitem.get(i).getFirstAirDate());
        viewHolder.tvOverview.setText(listitem.get(i).getOverview());
        viewHolder.tvVote.setText(String.valueOf(listitem.get(i).getVoteAverage()));
        Glide.with(context).load(BuildConfig.BASE_URL_IMAGE + listitem.get(i).getPosterPath()).into(viewHolder.ivGambar);

        viewHolder.btnDetail.setOnClickListener(v -> {
            Intent btn_detailtv = new Intent(context, DetailTvActivity.class);
            btn_detailtv.putExtra(DetailTvActivity.EXTRATV, listitem.get(i));
            context.startActivity(btn_detailtv);
        });

        viewHolder.btnShare.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
            sharingIntent.setAction(Intent.ACTION_SEND);
            sharingIntent.putExtra(Intent.EXTRA_TEXT,
                    context.getString(R.string.share, listitem.get(i).getName()));
            sharingIntent.setType("text/plain");
            context.startActivity(sharingIntent);
        });
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvJudul, tvDateRelease, tvVote, tvOverview;
        private ImageView ivGambar;
        private Button btnShare, btnDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.tv_item_title);
            tvDateRelease = itemView.findViewById(R.id.tv_released);
            tvVote = itemView.findViewById(R.id.tv_item_rating);
            ivGambar = itemView.findViewById(R.id.img_item_poster);
            tvOverview = itemView.findViewById(R.id.tv_content_overview);
            btnDetail = itemView.findViewById(R.id.btn_detail);
            btnShare = itemView.findViewById(R.id.btn_share);
        }
    }
}
