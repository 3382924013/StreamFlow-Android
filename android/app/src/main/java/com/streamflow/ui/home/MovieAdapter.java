package com.streamflow.ui.home;

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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final List<Movie> movies;
    private final boolean showProgress;
    private final OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movies, boolean showProgress, OnMovieClickListener listener) {
        this.movies = movies;
        this.showProgress = showProgress;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(holder.itemView).load(movie.getCover()).centerCrop().into(holder.cover);
        holder.title.setText(movie.getTitle());
        holder.rating.setText(String.valueOf(movie.getRating()));
        holder.year.setText(String.valueOf(movie.getYear()));
        holder.duration.setText(movie.getDuration());
        holder.vipBadge.setVisibility(movie.isVip() ? View.VISIBLE : View.GONE);

        if (showProgress && movie.getProgress() > 0) {
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.progressBar.setProgress(movie.getProgress());
        } else {
            holder.progressBar.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> listener.onMovieClick(movie));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView title, rating, year, duration, vipBadge;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.movie_cover);
            title = itemView.findViewById(R.id.movie_title);
            rating = itemView.findViewById(R.id.movie_rating);
            year = itemView.findViewById(R.id.movie_year);
            duration = itemView.findViewById(R.id.movie_duration);
            vipBadge = itemView.findViewById(R.id.movie_vip_badge);
            progressBar = itemView.findViewById(R.id.movie_progress);
        }
    }
}
