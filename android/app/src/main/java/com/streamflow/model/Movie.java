package com.streamflow.model;

import java.util.List;

public class Movie {
    private String id;
    private String title;
    private String description;
    private String cover;
    private String poster;
    private List<String> genre;
    private int year;
    private String region;
    private double rating;
    private boolean isVip;
    private String duration;
    private int episodes;
    private int progress;

    public Movie(String id, String title, String description, String cover, String poster,
                 List<String> genre, int year, String region, double rating,
                 boolean isVip, String duration, int episodes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cover = cover;
        this.poster = poster;
        this.genre = genre;
        this.year = year;
        this.region = region;
        this.rating = rating;
        this.isVip = isVip;
        this.duration = duration;
        this.episodes = episodes;
        this.progress = 0;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCover() { return cover; }
    public String getPoster() { return poster; }
    public List<String> getGenre() { return genre; }
    public int getYear() { return year; }
    public String getRegion() { return region; }
    public double getRating() { return rating; }
    public boolean isVip() { return isVip; }
    public String getDuration() { return duration; }
    public int getEpisodes() { return episodes; }
    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }
}
