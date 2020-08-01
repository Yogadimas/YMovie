package com.yogadimas.ymovie.adapter.favorit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yogadimas.ymovie.BuildConfig;
import com.yogadimas.ymovie.R;
import com.yogadimas.ymovie.activity.detail.DetailTvActivity;
import com.yogadimas.ymovie.model.tv.ResultsTv;
import com.yogadimas.ymovie.roomdatabase.tv.TvDatabase;

import java.util.List;

public class FavoritTvAdapter extends RecyclerView.Adapter<FavoritTvAdapter.ViewHolder> {

    private Context context;
    private List<ResultsTv> listitem;

    public FavoritTvAdapter(Context context, List<ResultsTv> resultsTv) {
        this.context = context;
        this.listitem = resultsTv;
    }

    @NonNull
    @Override
    public FavoritTvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_favorite, viewGroup, false));
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(@NonNull final FavoritTvAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvTitle.setText(listitem.get(i).getName());
        viewHolder.tvDateRelease.setText(listitem.get(i).getFirstAirDate());
        viewHolder.tvOverview.setText(listitem.get(i).getOverview());
        ;
        viewHolder.tvVote.setText(String.valueOf(listitem.get(i).getVoteAverage()));
        Glide.with(context).load(BuildConfig.BASE_URL_IMAGE + listitem.get(i).getPosterPath()).into(viewHolder.ivPoster);
        long id = listitem.get(i).getId();
        viewHolder.itemView.setTag(id);

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

        viewHolder.itemView.setOnLongClickListener(v -> {
            AlertDialog.Builder dialog;
            dialog = new AlertDialog.Builder(context);
            dialog.setCancelable(true);
            dialog.setIcon(R.drawable.ic_dustbin);
            dialog.setTitle(R.string.confirmfav);
            dialog.setPositiveButton(R.string.yes, (dialog1, which) -> {
                dialog1.dismiss();
                TvDatabase tvDatabase = TvDatabase.getTvDatabase(context);
                tvDatabase.tvDao().deleteTv(listitem.get(viewHolder.getAdapterPosition()).getId());
                Toast.makeText(context, R.string.successDelete, Toast.LENGTH_SHORT).show();
                listitem.remove(listitem.get(viewHolder.getAdapterPosition()));
                notifyItemRemoved(viewHolder.getAdapterPosition());

            });
            dialog.setNegativeButton(R.string.no, (dialog12, which) -> dialog12.dismiss());
            dialog.show();
            return true;
        });

    }


    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvDateRelease, tvVote, tvOverview;
        private ImageView ivPoster;
        private Button btnShare, btnDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDateRelease = itemView.findViewById(R.id.tv_released);
            tvVote = itemView.findViewById(R.id.tv_item_rating);
            ivPoster = itemView.findViewById(R.id.img_item_poster);
            tvOverview = itemView.findViewById(R.id.tv_content_overview);
            btnDetail = itemView.findViewById(R.id.btn_detail);
            btnShare = itemView.findViewById(R.id.btn_share);

        }
    }
}