package com.streamflow.model;

public class Comment {
    private String id;
    private String userId;
    private String nickname;
    private String avatar;
    private int rating;
    private String content;
    private int likes;
    private boolean isLiked;
    private String createdAt;

    public Comment(String id, String userId, String nickname, String avatar,
                   int rating, String content, int likes, boolean isLiked, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.avatar = avatar;
        this.rating = rating;
        this.content = content;
        this.likes = likes;
        this.isLiked = isLiked;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getNickname() { return nickname; }
    public String getAvatar() { return avatar; }
    public int getRating() { return rating; }
    public String getContent() { return content; }
    public int getLikes() { return likes; }
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }
    public void setLikes(int likes) { this.likes = likes; }
    public String getCreatedAt() { return createdAt; }
}
