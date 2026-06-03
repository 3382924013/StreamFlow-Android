package com.streamflow.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.streamflow.R;
import com.streamflow.model.Movie;

import java.util.List;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {

    private final List<Movie> movies;
    private final OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public RecentAdapter(List<Movie> movies, OnMovieClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(holder.itemView).load(movie.getCover()).centerCrop().into(holder.cover);
        holder.title.setText(movie.getTitle());
        if (movie.getProgress() > 0) {
            holder.progress.setVisibility(View.VISIBLE);
            holder.progress.setProgress(movie.getProgress());
        } else {
            holder.progress.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> listener.onMovieClick(movie));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView title;
        ProgressBar progress;

        ViewHolder(View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.recent_cover);
            title = itemView.findViewById(R.id.recent_title);
            progress = itemView.findViewById(R.id.recent_progress);
        }
    }
}
