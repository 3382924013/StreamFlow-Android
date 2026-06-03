package com.streamflow.model;

import java.util.List;

public class User {
    private String id;
    private String nickname;
    private String avatar;
    private boolean isVip;
    private String vipExpireDate;
    private List<Movie> watchHistory;
    private List<Movie> favorites;
    private List<Movie> downloads;

    public User(String id, String nickname, String avatar, boolean isVip,
                String vipExpireDate, List<Movie> watchHistory,
                List<Movie> favorites, List<Movie> downloads) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
        this.isVip = isVip;
        this.vipExpireDate = vipExpireDate;
        this.watchHistory = watchHistory;
        this.favorites = favorites;
        this.downloads = downloads;
    }

    public String getId() { return id; }
    public String getNickname() { return nickname; }
    public String getAvatar() { return avatar; }
    public boolean isVip() { return isVip; }
    public String getVipExpireDate() { return vipExpireDate; }
    public List<Movie> getWatchHistory() { return watchHistory; }
    public List<Movie> getFavorites() { return favorites; }
    public List<Movie> getDownloads() { return downloads; }
}
