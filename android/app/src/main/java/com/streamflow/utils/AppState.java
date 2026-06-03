package com.streamflow.utils;

import com.streamflow.model.Movie;
import com.streamflow.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppState {
    private static AppState instance;
    private User currentUser;
    private boolean isLoggedIn = true;
    private Movie currentMovie;
    private boolean isPlaying = false;
    private String playbackQuality = "1080P";
    private float playbackSpeed = 1.0f;
    private int currentTime = 0;
    private Set<String> favorites = new HashSet<>();
    private List<String> searchHistory = new ArrayList<>();

    private AppState() {
        currentUser = com.streamflow.data.MockData.getCurrentUser();
        for (Movie m : currentUser.getFavorites()) {
            favorites.add(m.getId());
        }
        searchHistory.add("科幻电影");
        searchHistory.add("宫崎骏");
        searchHistory.add("悬疑");
    }

    public static synchronized AppState getInstance() {
        if (instance == null) instance = new AppState();
        return instance;
    }

    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User user) { this.currentUser = user; }
    public boolean isLoggedIn() { return isLoggedIn; }
    public void setLoggedIn(boolean loggedIn) { isLoggedIn = loggedIn; }
    public Movie getCurrentMovie() { return currentMovie; }
    public void setCurrentMovie(Movie movie) { this.currentMovie = movie; }
    public boolean isPlaying() { return isPlaying; }
    public void setPlaying(boolean playing) { isPlaying = playing; }
    public String getPlaybackQuality() { return playbackQuality; }
    public void setPlaybackQuality(String quality) { this.playbackQuality = quality; }
    public float getPlaybackSpeed() { return playbackSpeed; }
    public void setPlaybackSpeed(float speed) { this.playbackSpeed = speed; }
    public int getCurrentTime() { return currentTime; }
    public void setCurrentTime(int time) { this.currentTime = time; }
    public Set<String> getFavorites() { return favorites; }
    public boolean isFavorite(String movieId) { return favorites.contains(movieId); }
    public void toggleFavorite(String movieId) {
        if (favorites.contains(movieId)) favorites.remove(movieId);
        else favorites.add(movieId);
    }
    public List<String> getSearchHistory() { return searchHistory; }
    public void addSearchHistory(String query) {
        searchHistory.remove(query);
        searchHistory.add(0, query);
        if (searchHistory.size() > 10) searchHistory.remove(searchHistory.size() - 1);
    }
    public void clearSearchHistory() { searchHistory.clear(); }
}
