package com.streamflow.ui.player;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.streamflow.R;
import com.streamflow.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private final List<Comment> comments;
    private final OnLikeClickListener listener;

    public interface OnLikeClickListener {
        void onLikeClick(Comment comment);
    }

    public CommentAdapter(List<Comment> comments, OnLikeClickListener listener) {
        this.comments = comments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        Glide.with(holder.itemView).load(comment.getAvatar()).circleCrop().into(holder.avatar);
        holder.nickname.setText(comment.getNickname());
        holder.content.setText(comment.getContent());
        holder.likes.setText(String.valueOf(comment.getLikes()));
        holder.date.setText(comment.getCreatedAt());

        holder.likeIcon.setImageResource(comment.isLiked() ? R.drawable.ic_heart_filled : R.drawable.ic_heart);
        holder.likeIcon.setColorFilter(holder.itemView.getContext().getColor(
            comment.isLiked() ? R.color.coral : R.color.gray_400));
        holder.likes.setTextColor(holder.itemView.getContext().getColor(
            comment.isLiked() ? R.color.coral : R.color.gray_400));

        holder.likeIcon.setOnClickListener(v -> listener.onLikeClick(comment));

        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stars.append(i < comment.getRating() ? "★" : "☆");
        }
        holder.rating.setText(stars.toString());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar, likeIcon;
        TextView nickname, rating, content, likes, date;

        ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.comment_avatar);
            nickname = itemView.findViewById(R.id.comment_nickname);
            rating = itemView.findViewById(R.id.comment_rating);
            content = itemView.findViewById(R.id.comment_content);
            likeIcon = itemView.findViewById(R.id.comment_like_icon);
            likes = itemView.findViewById(R.id.comment_likes);
            date = itemView.findViewById(R.id.comment_date);
        }
    }
}
