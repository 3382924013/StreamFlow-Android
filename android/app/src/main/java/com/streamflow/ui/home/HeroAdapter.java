package com.streamflow.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.streamflow.R;
import com.streamflow.model.Movie;

import java.util.List;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.ViewHolder> {

    private final List<Movie> movies;
    private final OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public HeroAdapter(List<Movie> movies, OnMovieClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(holder.itemView).load(movie.getPoster()).centerCrop().into(holder.image);
        holder.title.setText(movie.getTitle());
        holder.desc.setText(movie.getDescription());
        holder.genre.setText(movie.getGenre().get(0));
        holder.vipBadge.setVisibility(movie.isVip() ? View.VISIBLE : View.GONE);
        holder.playBtn.setOnClickListener(v -> listener.onMovieClick(movie));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, desc, genre, vipBadge, playBtn;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.hero_image);
            title = itemView.findViewById(R.id.hero_title);
            desc = itemView.findViewById(R.id.hero_desc);
            genre = itemView.findViewById(R.id.hero_genre);
            vipBadge = itemView.findViewById(R.id.hero_vip_badge);
            playBtn = itemView.findViewById(R.id.hero_play_btn);
        }
    }
}
